package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.CommentTypeManagerAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.event.AddTypeEvent;
import com.lawwing.lwdocument.event.CommentTypeChangeEvent;
import com.lawwing.lwdocument.event.RecycleCommentTransTypeEvent;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/23.
 */

public class TypeManagerFragment extends BaseFragment implements ScreenShotable
{
    private View containerView;
    
    private Bitmap bitmap;
    
    private RecyclerView typeRecyclerView;
    
    private ArrayList<CommentTypeInfoModel> datas;
    
    private CommentTypeManagerAdapter adapter;
    
    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    public static TypeManagerFragment newInstance()
    {
        TypeManagerFragment contentFragment = new TypeManagerFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater
                .inflate(R.layout.fragment_type_manager, container, false);
        typeRecyclerView = (RecyclerView) rootView
                .findViewById(R.id.typeRecyclerView);
        mCommentTypeInfoDbDao = LWDApp.get()
                .getDaoSession()
                .getCommentTypeInfoDbDao();
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        datas = new ArrayList<>();
        initRecyclerView();
        initData();
        adapter.notifyDataSetChanged();
        LWDApp.getEventBus().register(this);
        return rootView;
    }
    
    @Override
    public void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    private void initData()
    {
        datas.clear();
        List<CommentTypeInfoDb> dbs = mCommentTypeInfoDbDao.loadAll();
        for (CommentTypeInfoDb db : dbs)
        {
            CommentTypeInfoModel model = new CommentTypeInfoModel();
            model.setId(db.getId());
            model.setCreateTime(db.getCreateTime());
            model.setTypeName(db.getTypeName());
            model.setEdit(db.getIsEdit());
            model.setShow(db.getIsShow());
            model.setCount(db.getCommentInfoDbs().size());
            if (db.getIsShow())
            {
                datas.add(model);
            }
        }
    }
    
    private void initRecyclerView()
    {
        // GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        typeRecyclerView.setLayoutManager(manager);
        adapter = new CommentTypeManagerAdapter(datas, getActivity());
        typeRecyclerView.setAdapter(adapter);
    }
    
    @Override
    public void takeScreenShot()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                TypeManagerFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
    }
    
    @Subscribe
    public void addType(AddTypeEvent event)
    {
        if (event != null)
        {
            int ccount = datas.size();
            
            if (ccount == 10)
            {
                showShortToast("最多只能添加十个分类，请删除其他分类再添加！");
                return;
            }
            final EditText editText = new EditText(getActivity());
            editText.setFilters(
                    new InputFilter[] { new InputFilter.LengthFilter(10) });
            new AlertDialog.Builder(getActivity()).setTitle("请输入类型名称（10个字以内）")
                    .setView(editText)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which)
                                {
                                    if (!TextUtils.isEmpty(
                                            editText.getText().toString()))
                                    {
                                        long count = mCommentTypeInfoDbDao
                                                .queryBuilder()
                                                .where(CommentTypeInfoDbDao.Properties.TypeName
                                                        .eq(editText.getText()
                                                                .toString()
                                                                .trim()))
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
                                            insertCommentType(editText.getText()
                                                    .toString()
                                                    .trim());
                                            initData();
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),
                                                "请输入类型名称",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton("取消", null)
                    .show();
        }
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
    
    @Subscribe
    public void changeType(final CommentTypeChangeEvent event)
    {
        if (event != null)
        {
            switch (event.getFlag())
            {
                case "edit":
                    // 编辑名称
                    final EditText editText = new EditText(getActivity());
                    editText.setText(event.getTypeInfoModel().getTypeName());
                    editText.setSelection(
                            editText.getText().toString().length());
                    editText.setFilters(new InputFilter[] {
                            new InputFilter.LengthFilter(10) });
                    new AlertDialog.Builder(getActivity()).setTitle("编辑分类名称")
                            .setView(editText)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which)
                                        {
                                            if (!TextUtils
                                                    .isEmpty(editText.getText()
                                                            .toString()
                                                            .trim()))
                                            {
                                                CommentTypeInfoDb commentTypeInfoDb = mCommentTypeInfoDbDao
                                                        .load(event
                                                                .getTypeInfoModel()
                                                                .getId());
                                                commentTypeInfoDb.setTypeName(
                                                        editText.getText()
                                                                .toString()
                                                                .trim());
                                                mCommentTypeInfoDbDao
                                                        .insertOrReplace(
                                                                commentTypeInfoDb);
                                                
                                                datas.get(event.getPosition())
                                                        .setTypeName(editText
                                                                .getText()
                                                                .toString()
                                                                .trim());
                                                adapter.notifyDataSetChanged();
                                            }
                                            else
                                            {
                                                showShortToast("名称不能为空！");
                                            }
                                        }
                                    })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                
                case "delete":
                    // 删除类型,实际上是隐藏。。。
                    boolean hasComment = event.getTypeInfoModel()
                            .getCount() == 0 ? false : true;
                    String message = "";
                    if (hasComment)
                    {
                        message = "是否删除该分类？\n删除后分类下的批阅将移动到回收站，可以从右上角回收站恢复批阅文件！";
                    }
                    else
                    {
                        message = "是否删除该分类？";
                    }
                    new AlertDialog.Builder(getActivity()).setTitle("警告")
                            .setMessage(message)
                            .setNegativeButton("取消", null)
                            .setPositiveButton("删除",
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which)
                                        {
                                            CommentTypeInfoDb commentTypeInfoDb = mCommentTypeInfoDbDao
                                                    .load(event
                                                            .getTypeInfoModel()
                                                            .getId());
                                            commentTypeInfoDb.setIsShow(false);
                                            mCommentTypeInfoDbDao
                                                    .update(commentTypeInfoDb);
                                            
                                            // 进行数据库操作，把类型为这个的typeId都变为-1
                                            List<CommentInfoDb> commentInfoDbs = commentTypeInfoDb
                                                    .getCommentInfoDbs();
                                            for (CommentInfoDb db : commentInfoDbs)
                                            {
                                                db.setTypeId(-1);
                                                mCommentInfoDbDao.update(db);
                                            }
                                            
                                            datas.get(event.getPosition())
                                                    .setShow(false);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                            .create()
                            .show();
                    
                    break;
                default:
                    break;
            }
        }
    }
    
    @Subscribe
    public void transComment(RecycleCommentTransTypeEvent event)
    {
        if (event != null)
        {
            
            LWDApp.get().setmDaoSession(LWDApp.get().getDaoSession());
            
            mCommentInfoDbDao = LWDApp.get()
                    .getDaoSession()
                    .getCommentInfoDbDao();
            initData();
            adapter.notifyDataSetChanged();
        }
    }
}
