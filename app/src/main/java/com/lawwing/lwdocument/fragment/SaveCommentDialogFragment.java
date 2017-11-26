package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;
import java.util.List;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.SaveCommentClickEvent;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.lawwing.lwdocument.utils.KeyboardUtils;
import com.lawwing.lwdocument.utils.TimeUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lawwing on 2017/11/23.
 */

public class SaveCommentDialogFragment extends DialogFragment
{
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    
    @BindView(R.id.nameEdittext)
    EditText nameEdittext;
    
    @BindView(R.id.typeEdittext)
    EditText typeEdittext;
    
    @BindView(R.id.addTypeBtn)
    ImageView addTypeBtn;
    
    @BindView(R.id.saveBtn)
    TextView saveBtn;
    
    @BindView(R.id.tagFlowlayout)
    TagFlowLayout tagFlowlayout;
    
    @BindView(R.id.typeInputLayout)
    RelativeLayout typeInputLayout;
    
    @BindView(R.id.addTypeCancleBtn)
    ImageView addTypeCancleBtn;
    
    @BindView(R.id.addTypeContentBtn)
    ImageView addTypeContentBtn;
    
    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;
    
    private ArrayList<CommentTypeInfoModel> datas;
    
    private TagAdapter<CommentTypeInfoModel> adapter;
    
    private CommentTypeInfoModel selectBean;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_save_comment_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.drawable.bg_menu_more_list);
        ButterKnife.bind(this, dialog); // Dialog即View
        mCommentTypeInfoDbDao = LWDApp.get()
                .getDaoSession()
                .getCommentTypeInfoDbDao();
        
        initData();
        initTypeRecycler();
        contentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                KeyboardUtils.hideSoftInput(getActivity(), nameEdittext);
                if (!TextUtils.isEmpty(nameEdittext.getText().toString()))
                {
                    if (selectBean != null)
                    {
                        
                        LWDApp.getEventBus().post(new SaveCommentClickEvent(
                                selectBean,
                                nameEdittext.getText().toString().trim()));
                        
                    }
                    else
                    {
                        Toast.makeText(getActivity(),
                                "请先选择类型",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),
                            "请先填写批阅名称",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        addTypeBtn.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                if (datas.size() < 10)
                {
                    typeInputLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    
                    Toast.makeText(getActivity(),
                            "您最多能添加十个分类，如需要管理分类，回到首页进入相关管理页面",
                            Toast.LENGTH_SHORT).show();
                    
                }
            }
        });
        addTypeContentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!TextUtils.isEmpty(typeEdittext.getText().toString()))
                {
                    long count = mCommentTypeInfoDbDao.queryBuilder()
                            .where(CommentTypeInfoDbDao.Properties.TypeName.eq(
                                    typeEdittext.getText().toString().trim()))
                            .where(CommentTypeInfoDbDao.Properties.IsShow
                                    .eq(true))
                            .count();
                    if (count != 0)
                    {
                        Toast.makeText(getActivity(),
                                "类型名不能与已有的重复",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        insertCommentType(typeEdittext.getText().toString());
                        // 刷新列表
                        initData();
                        typeInputLayout.setVisibility(View.GONE);
                        typeEdittext.setText("");
                        KeyboardUtils.hideSoftInput(getActivity(),
                                typeEdittext);
                        initTypeRecycler();
                        adapter.setSelected(datas.size() - 1,
                                datas.get(datas.size() - 1));
                        adapter.notifyDataChanged();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "请输入类型名称", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        addTypeCancleBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                typeInputLayout.setVisibility(View.GONE);
                typeEdittext.setText("");
                KeyboardUtils.hideSoftInput(getActivity(), typeEdittext);
            }
        });
        return dialog;
    }
    
    private void insertCommentType(String name)
    {
        CommentTypeInfoDb commentTypeDb = new CommentTypeInfoDb();
        commentTypeDb.setCreateTime(TimeUtils.getCurTimeMills());
        commentTypeDb.setTypeName(name);
        commentTypeDb.setIsEdit(true);
        commentTypeDb.setIsShow(true);
        mCommentTypeInfoDbDao.insertOrReplace(commentTypeDb);
    }
    
    private void initTypeRecycler()
    {
        adapter = new TagAdapter<CommentTypeInfoModel>(datas)
        {
            @Override
            public View getView(FlowLayout parent, int position,
                    CommentTypeInfoModel commentTypeInfoModel)
            {
                TextView textView = (TextView) LayoutInflater
                        .from(getActivity())
                        .inflate(R.layout.lable_item, tagFlowlayout, false);
                textView.setText(commentTypeInfoModel.getTypeName());
                return textView;
            }
            
        };
        tagFlowlayout.setAdapter(adapter);
        tagFlowlayout
                .setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
                {
                    @Override
                    public boolean onTagClick(View view, int position,
                            FlowLayout parent)
                    {
                        if (selectBean == null)
                        {
                            selectBean = datas.get(position);
                        }
                        else
                        {
                            if (selectBean.getId() == datas.get(position)
                                    .getId())
                            {
                                selectBean = null;
                            }
                            else
                            {
                                selectBean = datas.get(position);
                            }
                        }
                        return true;
                    }
                });
    }
    
    private void initData()
    {
        datas = new ArrayList<>();
        List<CommentTypeInfoDb> dbs = mCommentTypeInfoDbDao.loadAll();
        
        for (CommentTypeInfoDb temp : dbs)
        {
            CommentTypeInfoModel model = new CommentTypeInfoModel();
            model.setId(temp.getId());
            model.setCreateTime(temp.getCreateTime());
            model.setEdit(temp.getIsEdit());
            model.setTypeName(temp.getTypeName());
            model.setShow(temp.getIsShow());
            model.setCount(temp.getCommentInfoDbs().size());
            if (temp.getIsShow())
            {
                datas.add(model);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater,
                container,
                savedInstanceState);
        return rootView;
    }
    
}
