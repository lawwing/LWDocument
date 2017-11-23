package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;
import java.util.List;

import com.itheima.wheelpicker.WheelPicker;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.DateCommentAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/23.
 */

public class TypeCommentFragment extends BaseFragment implements ScreenShotable
{
    private View containerView;
    
    private Bitmap bitmap;
    
    private WheelPicker wheelPicker;
    
    private RecyclerView recyclerView;
    
    private List<String> datas;
    
    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
    private DateCommentAdapter adapter;
    
    private ArrayList<CommentInfoModel> listDatas;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    public static TypeCommentFragment newInstance()
    {
        TypeCommentFragment contentFragment = new TypeCommentFragment();
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
                .inflate(R.layout.fragment_type_comment_list, container, false);
        
        wheelPicker = (WheelPicker) rootView.findViewById(R.id.wheelPicker);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mCommentTypeInfoDbDao = LWDApp.get()
                .getDaoSession()
                .getCommentTypeInfoDbDao();
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        initWheelPicker();
        initCommentListData();
        initRecyclerView();
        return rootView;
    }
    
    private void initWheelPicker()
    {
        initDbDatas();
        wheelPicker.setData(datas);
        wheelPicker.setSelectedItemPosition(0);
    }
    
    private void initRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        
        adapter = new DateCommentAdapter(listDatas, getActivity());
        recyclerView.setAdapter(adapter);
    }
    
    private void initCommentListData()
    {
        listDatas = new ArrayList<>();
        List<CommentInfoDb> dbs = mCommentInfoDbDao.queryBuilder()
                .orderDesc(CommentInfoDbDao.Properties.Time)
                .list();
        for (CommentInfoDb comment : dbs)
        {
            CommentInfoModel model = new CommentInfoModel();
            model.setName(comment.getName());
            model.setPath(comment.getPath());
            model.setDocpath(comment.getDocpath());
            model.setDocname(comment.getDocname());
            model.setId(comment.getId());
            model.setTime(comment.getTime());
            model.setTypeId(comment.getTypeId());
            model.setDateId(comment.getDateId());
            listDatas.add(model);
        }
    }
    
    private void initDbDatas()
    {
        datas = new ArrayList<>();
        datas.add("全部");
        List<CommentTypeInfoDb> dbs = mCommentTypeInfoDbDao.loadAll();
        for (CommentTypeInfoDb db : dbs)
        {
            datas.add(db.getTypeName());
        }
    }
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
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
                TypeCommentFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
}
