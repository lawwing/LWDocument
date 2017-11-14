package com.lawwing.lwdocument;

import static com.lawwing.lwdocument.adapter.PaintListAdapter.CHECK_MODE;

import java.util.ArrayList;
import java.util.List;

import com.lawwing.lwdocument.adapter.PaintListAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.gen.PaintInfoDb;
import com.lawwing.lwdocument.gen.PaintInfoDbDao;
import com.lawwing.lwdocument.model.PaintInfoModel;
import com.lawwing.lwdocument.utils.TitleBarUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XjbPaintGalleryActivity extends BaseActivity
{
    
    @BindView(R.id.paintRecyclerView)
    RecyclerView paintRecyclerView;
    
    private TitleBarUtils titleBarUtils;
    
    private PaintInfoDbDao mPaintInfoDbDao;
    
    private ArrayList<PaintInfoModel> datas;
    
    private PaintListAdapter adapter;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, XjbPaintGalleryActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjb_paint_gallery);
        ButterKnife.bind(this);
        titleBarUtils = new TitleBarUtils(XjbPaintGalleryActivity.this);
        titleBarUtils.initTitle("欢迎来到涂鸦画廊");
        mPaintInfoDbDao = LWDApp.get().getDaoSession().getPaintInfoDbDao();
        datas = new ArrayList<>();
        initRecyclerView();
        getPaintListByDb();
    }
    
    private void initRecyclerView()
    {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        paintRecyclerView.setLayoutManager(manager);
        
        adapter = new PaintListAdapter(XjbPaintGalleryActivity.this, datas,
                CHECK_MODE);
        paintRecyclerView.setAdapter(adapter);
    }
    
    private void getPaintListByDb()
    {
        datas.clear();
        List<PaintInfoDb> paints = mPaintInfoDbDao.queryBuilder()
                .orderDesc(PaintInfoDbDao.Properties.Time)
                .list();
        for (PaintInfoDb paint : paints)
        {
            PaintInfoModel model = new PaintInfoModel();
            model.setTime(paint.getTime());
            model.setId(paint.getId());
            model.setPath(paint.getPath());
            model.setName(paint.getName());
            datas.add(model);
        }
        adapter.notifyDataSetChanged();
    }
}
