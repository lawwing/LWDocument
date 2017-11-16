package com.lawwing.lwdocument;

import static com.lawwing.lwdocument.adapter.PaintListAdapter.CHECK_MODE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.adapter.PaintListAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.event.PaintLongEvent;
import com.lawwing.lwdocument.gen.PaintInfoDb;
import com.lawwing.lwdocument.gen.PaintInfoDbDao;
import com.lawwing.lwdocument.model.PaintInfoModel;
import com.lawwing.lwdocument.utils.TitleBarUtils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
        LWDApp.getEventBus().register(this);
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    @Subscribe
    public void onEventMainThread(final PaintLongEvent event)
    {
        if (event != null)
        {
            new AlertDialog.Builder(this).setTitle("菜单")
                    .setItems(new String[] { "删除" },
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which)
                                {
                                    switch (which)
                                    {
                                        case 0:
                                            new AlertDialog.Builder(
                                                    XjbPaintGalleryActivity.this)
                                                            .setTitle("警告")
                                                            .setMessage(
                                                                    "是否删除该涂鸦，删除后将无法查看")
                                                            .setNegativeButton(
                                                                    "取消",
                                                                    null)
                                                            .setPositiveButton(
                                                                    "删除",
                                                                    new DialogInterface.OnClickListener()
                                                                    {
                                                                        @Override
                                                                        public void onClick(
                                                                                DialogInterface dialog,
                                                                                int which)
                                                                        {
                                                                            // 删除
                                                                            mPaintInfoDbDao
                                                                                    .deleteByKey(
                                                                                            event.getModel()
                                                                                                    .getId());
                                                                            datas.remove(
                                                                                    event.getPosition());
                                                                            // 删除卡片操作
                                                                            adapter.notifyItemRemoved(
                                                                                    event.getPosition());
                                                                            adapter.notifyDataSetChanged();
                                                                            
                                                                            File file = new File(
                                                                                    event.getModel()
                                                                                            .getPath());
                                                                            if (file.exists())
                                                                            {
                                                                                file.delete();
                                                                            }
                                                                        }
                                                                    })
                                                            .create()
                                                            .show();
                                            break;
                                    }
                                }
                            })
                    .show();
        }
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
