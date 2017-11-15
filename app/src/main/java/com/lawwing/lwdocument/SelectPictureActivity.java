package com.lawwing.lwdocument;

import static com.lawwing.lwdocument.adapter.PaintListAdapter.SELECT_MODE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.adapter.PaintListAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.base.StaticDatas;
import com.lawwing.lwdocument.event.SelectPictureEvent;
import com.lawwing.lwdocument.gen.PaintInfoDb;
import com.lawwing.lwdocument.gen.PaintInfoDbDao;
import com.lawwing.lwdocument.model.PaintInfoModel;
import com.lawwing.lwdocument.utils.ImageUtils;
import com.lawwing.lwdocument.utils.TitleBarUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPictureActivity extends BaseActivity
{
    
    @BindView(R.id.selectPaintRecyclerView)
    RecyclerView selectPaintRecyclerView;
    
    private TitleBarUtils titleBarUtils;
    
    private PaintInfoDbDao mPaintInfoDbDao;
    
    private ArrayList<PaintInfoModel> datas;
    
    private PaintListAdapter adapter;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, SelectPictureActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        ButterKnife.bind(this);
        titleBarUtils = new TitleBarUtils(SelectPictureActivity.this);
        titleBarUtils.initTitle("选择图片");
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
    
    private void initRecyclerView()
    {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        selectPaintRecyclerView.setLayoutManager(manager);
        
        adapter = new PaintListAdapter(SelectPictureActivity.this, datas,
                SELECT_MODE);
        selectPaintRecyclerView.setAdapter(adapter);
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
    
    // 获取图像的bitmap
    private Bitmap bmp;
    
    @Subscribe
    public void onEventMainThread(SelectPictureEvent event)
    {
        if (event != null)
        {
            if (TextUtils.isEmpty(event.getModel().getPath()))
            {
                showShortToast("图片不存在");
                return;
            }
            File file = new File(event.getModel().getPath());
            if (file.exists())
            {
                // 获取图像的bitmap
                bmp = ImageUtils.getBitmap(event.getModel().getPath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                StaticDatas.bis = bitmapByte;
                String filename = file.getName();
                startActivity(CommentOfficeActivity.newIntance(
                        SelectPictureActivity.this,
                        filename,
                        event.getModel().getPath()));
            }
            else
            {
                showShortToast("图片不存在");
            }
        }
    }
}
