package com.lawwing.lwdocument;

import java.util.ArrayList;
import java.util.List;

import com.lawwing.lwdocument.adapter.OfficeFileAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.model.FileBean;
import com.lawwing.lwdocument.utils.FileUtils;
import com.lawwing.lwdocument.utils.TitleBarUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pub.devrel.easypermissions.EasyPermissions;

public class SelectFileActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks
{
    private RecyclerView recyclerView;
    
    private TextView tipsText;
    
    private OfficeFileAdapter adapter;
    
    private ArrayList<FileBean> datas;
    
    private TitleBarUtils titleBarUtils;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, SelectFileActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tipsText = (TextView) findViewById(R.id.tipsText);
        
        titleBarUtils = new TitleBarUtils(SelectFileActivity.this);
        titleBarUtils.initTitle("文档列表");
        datas = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new OfficeFileAdapter(datas, SelectFileActivity.this);
        recyclerView.setAdapter(adapter);
        String[] perms = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (!EasyPermissions.hasPermissions(SelectFileActivity.this, perms))
        {
            EasyPermissions.requestPermissions(SelectFileActivity.this,
                    "需要访问手机存储权限！",
                    10086,
                    perms);
        }
        else
        {
            getLocalOffice();
        }
    }
    
    private Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (datas.size() > 0)
            {
                tipsText.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
            else
            {
                tipsText.setVisibility(View.VISIBLE);
                tipsText.setText("没有找到文件");
            }
        }
    };
    
    private void getLocalOffice()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                datas.addAll(
                        FileUtils.getSpecificTypeOfFile(SelectFileActivity.this,
                                new String[] { ".docx", ".doc", ".xlsx", "xls",
                                        "ppt", "pptx", "pdf" }));
                handle.sendMessage(new Message());
            }
        }.start();
    }
    
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        // 成功
        if (requestCode == 10086)
        {
            if (perms.size() == 2)
            {
                getLocalOffice();
            }
        }
    }
    
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        
        // 失败
        if (requestCode == 10086)
        {
            showLongToast("请务必打开全部权限才能扫描文档");
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
    
}
