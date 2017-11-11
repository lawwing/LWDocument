package com.lawwing.lwdocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.adapter.HomeAdapter;
import com.lawwing.lwdocument.event.SaveCommentEvent;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.HomeBaseModel;
import com.lawwing.lwdocument.model.HomeBottomModel;
import com.lawwing.lwdocument.utils.FileManager;

import android.Manifest;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class AppMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EasyPermissions.PermissionCallbacks
{
    
    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;
    
    private HomeAdapter adapter;
    
    private ArrayList<HomeBaseModel> datas;
    
    boolean isOpen = false;
    
    private DrawerLayout drawer;
    
    private CommentInfoDbDao mCommentInfoDao;
    
    private int homeCardNum = 5;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCommentInfoDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                isOpen = false;
            }
            
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                isOpen = true;
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        datas = new ArrayList<>();
        String[] perms = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (!EasyPermissions.hasPermissions(AppMainActivity.this, perms))
        {
            EasyPermissions.requestPermissions(AppMainActivity.this,
                    "需要访问手机存储权限！",
                    10086,
                    perms);
        }
        else
        {
            initRecyclerView();
            
            getLocalCommentPicture();
        }
        
        LWDApp.getEventBus().register(this);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LWDApp.getEventBus().unregister(this);
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
        }
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
        List<CommentInfoDb> comments = mCommentInfoDao.queryBuilder()
                .orderDesc(CommentInfoDbDao.Properties.Time)
                .limit(homeCardNum)
                .list();
        
        long allcount = mCommentInfoDao.count();
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
        if (allcount > homeCardNum)
        {
            HomeBottomModel bottomModel = new HomeBottomModel();
            datas.add(bottomModel);
        }
        handler.sendMessage(new Message());
    }
    
    private void getPicByFileSystem()
    {
        File file = FileManager.getPhotoFolder();
        datas.clear();
        if (file.list().length > 0)
        {
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
                        model.setPath(file1.getAbsolutePath().toString());
                        datas.add(model);
                    }
                }
            }
            
            handler.sendMessage(new Message());
        }
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
            showLongToast("请务必打开全部权限才能查看首页");
        }
    }
    
    public void showLongToast(String content)
    {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
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
    
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!isOpen)
            {
                exitBy2Click(); // 调用双击退出函数
            }
            else
            {
                drawer.closeDrawers();
            }
        }
        return false;
    }
    
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    
    private void exitBy2Click()
    {
        Timer tExit = null;
        if (isExit == false)
        {
            isExit = true; // 准备退出
            showLongToast("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            
        }
        else
        {
            finish();
            System.exit(0);
        }
    }
}
