package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.CommentTypeManagerAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.event.AddTypeEvent;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
            datas.add(model);
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
            final EditText editText = new EditText(getActivity());
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
}
