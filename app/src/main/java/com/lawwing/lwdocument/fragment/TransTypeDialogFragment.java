package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;
import java.util.List;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.TransTypeEvent;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lawwing on 2017/11/25.
 */

public class TransTypeDialogFragment extends DialogFragment
{
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    
    @BindView(R.id.tagFlowlayout)
    TagFlowLayout tagFlowlayout;
    
    @BindView(R.id.saveBtn)
    TextView saveBtn;
    
    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;
    
    private ArrayList<CommentTypeInfoModel> datas;
    
    private TagAdapter<CommentTypeInfoModel> adapter;
    
    private CommentTypeInfoModel selectTypeBean;
    
    private CommentInfoModel selectCommentInfo;
    
    private int selectPoision;
    
    public TransTypeDialogFragment(CommentInfoModel selectCommentInfo,
            int selectPoision)
    {
        this.selectCommentInfo = selectCommentInfo;
        this.selectPoision = selectPoision;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_trans_type_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
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
                
                if (selectTypeBean != null)
                {
                    dismiss();
                    LWDApp.getEventBus().post(new TransTypeEvent(selectTypeBean,
                            selectCommentInfo, selectPoision));
                }
                else
                {
                    Toast.makeText(getActivity(), "请先选择分类", Toast.LENGTH_SHORT)
                            .show();
                }
                
            }
        });
        // 下面写各种事件
        return dialog;
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
            datas.add(model);
        }
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
                        if (selectTypeBean == null)
                        {
                            selectTypeBean = datas.get(position);
                        }
                        else
                        {
                            if (selectTypeBean.getId() == datas.get(position)
                                    .getId())
                            {
                                selectTypeBean = null;
                            }
                            else
                            {
                                selectTypeBean = datas.get(position);
                            }
                        }
                        return true;
                    }
                });
    }
}
