package com.lawwing.lwdocument;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.adapter.HomeAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.event.HomeDeleteEvent;
import com.lawwing.lwdocument.event.SaveCommentEvent;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.HomeBaseModel;
import com.lawwing.lwdocument.utils.TitleBarUtils;
import com.tubb.smrv.SwipeMenuRecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class CheckCommentPicListActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks
{
    @BindView(R.id.commentRecyclerView)
    SwipeMenuRecyclerView commentRecyclerView;
    
    @BindView(R.id.nullTips)
    TextView nullTips;
    
    private HomeAdapter adapter;
    
    private ArrayList<HomeBaseModel> datas;
    
    private CommentInfoDbDao mCommentInfoDao;
    
    private TitleBarUtils titleBarUtils;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, CheckCommentPicListActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_comment_pic_list);
        ButterKnife.bind(this);
        mCommentInfoDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        datas = new ArrayList<>();
        
        titleBarUtils = new TitleBarUtils(CheckCommentPicListActivity.this);
        titleBarUtils.initTitle("批注列表");
        nullTips.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 进入查看全部文档页面
                startActivity(ShowDocumentListActivity
                        .newInstance(CheckCommentPicListActivity.this));
            }
        });
        LWDApp.getEventBus().register(this);
        String[] perms = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (!EasyPermissions.hasPermissions(CheckCommentPicListActivity.this,
                perms))
        {
            EasyPermissions.requestPermissions(CheckCommentPicListActivity.this,
                    "需要访问手机存储权限！",
                    10086,
                    perms);
        }
        else
        {
            initRecyclerView();
            
            getLocalCommentPicture();
        }
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    private void initRecyclerView()
    {
        nullTips.setVisibility(View.VISIBLE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerView.setLayoutManager(manager);
        
        adapter = new HomeAdapter(CheckCommentPicListActivity.this, datas,
                "commentList");
        commentRecyclerView.setAdapter(adapter);
    }
    
    private void getLocalCommentPicture()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                // getPicByFileSystem();
                getPicByDB();
            }
        }.start();
    }
    
    private void getPicByDB()
    {
        datas.clear();
        List<CommentInfoDb> comments = mCommentInfoDao.loadAll();
        
        for (CommentInfoDb comment : comments)
        {
            CommentInfoModel model = new CommentInfoModel();
            model.setName(comment.getName());
            model.setPath(comment.getPath());
            model.setDocpath(comment.getDocpath());
            model.setDocname(comment.getDocname());
            model.setId(comment.getId());
            model.setTime(comment.getTime());
            datas.add(model);
        }
        handler.sendMessage(new Message());
    }
    
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (datas.size() > 0)
            {
                nullTips.setVisibility(View.GONE);
            }
            else
            {
                nullTips.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        }
    };
    
    @Subscribe
    public void onEventMainThread(final HomeDeleteEvent event)
    {
        if (event != null)
        {
            String flag = event.getFlag();
            if ("commentList".equals(flag))
            {
                if (event.getModel() != null)
                {
                    new AlertDialog.Builder(this).setTitle("警告")
                            .setMessage("是否删除该批注，删除后将无法查看")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("删除",
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which)
                                        {
                                            mCommentInfoDao.deleteByKey(
                                                    event.getModel().getId());
                                            datas.remove(event.getPosition());
                                            // 删除卡片操作
                                            adapter.notifyItemRemoved(
                                                    event.getPosition());
                                            adapter.notifyDataSetChanged();
                                            LWDApp.getEventBus()
                                                    .post(new SaveCommentEvent(
                                                            "列表删除"));
                                        }
                                    })
                            .create()
                            .show();
                }
            }
        }
    }
    
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        // 成功
        if (requestCode == 10086)
        {
            if (perms.size() == 2)
            {
                initRecyclerView();
                
                getLocalCommentPicture();
            }
        }
    }
    
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        
        // 失败
        if (requestCode == 10086)
        {
            showShortToast("请务必打开全部权限才能查看列表");
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode,
            String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults,
                this);
    }
    
    @Subscribe
    public void onEventMainThread(SaveCommentEvent event)
    {
        if (event != null)
        {
            String flag = event.getFlag();
            if ("批注".equals(flag))
            {
                getLocalCommentPicture();
            }
            else if ("列表删除".equals(flag))
            {
                getLocalCommentPicture();
            }
        }
    }
}
