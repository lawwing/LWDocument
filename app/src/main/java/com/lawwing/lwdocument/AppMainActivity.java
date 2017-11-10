package com.lawwing.lwdocument;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.lawwing.lwdocument.adapter.HomeAdapter;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.utils.FileManager;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    
    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;
    
    private HomeAdapter adapter;
    
    private ArrayList<CommentInfoModel> datas;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        
        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        datas = new ArrayList<>();
        initRecyclerView();
        
        getLocalCommentPicture();
    }
    
    private void getLocalCommentPicture()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                File file = FileManager.getPhotoFolder();
                if (file.list().length > 0)
                {
                    datas.clear();
                    for (File file1 : file.listFiles())
                    {
                        if (!file1.isDirectory())
                        {
                            int index = file1.getName().lastIndexOf(".");
                            String extention = "";
                            
                            if (index != -1)
                            {
                                extention = file1.getName().substring(index + 1,
                                        file1.getName().length());
                            }
                            else
                            {
                                return;
                            }
                            if (extention.equals("jpg"))
                            {
                                CommentInfoModel model = new CommentInfoModel();
                                model.setName(file1.getName());
                                model.setPath(
                                        file1.getAbsolutePath().toString());
                                datas.add(model);
                            }
                        }
                    }
                    
                    Log.e("test", datas.get(0).getPath());
                    handler.sendMessage(new Message());
                }
            }
        }.start();
    }
    
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };
    
    private void initRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        homeRecyclerView.setLayoutManager(manager);
        
        adapter = new HomeAdapter(AppMainActivity.this, datas);
        homeRecyclerView.setAdapter(adapter);
    }
    
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        
        if (id == R.id.nav_check_alldoc)
        {
            // 进入查看全部文档页面
            startActivity(SelectFileActivity.newInstance(AppMainActivity.this));
        }
        else if (id == R.id.nav_check_comment)
        {
            
        }
        
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
