package com.lawwing.lwdocument;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.base.StaticDatas;
import com.lawwing.lwdocument.event.DateClickEvent;
import com.lawwing.lwdocument.event.MonthChangeEvent;
import com.lawwing.lwdocument.fragment.ContentFragment;
import com.lawwing.lwdocument.fragment.DateCommentFragment;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.Subscribe;

import cn.lawwing.homeslidemenu.interfaces.Resourceble;
import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;
import cn.lawwing.homeslidemenu.model.SlideMenuItem;
import cn.lawwing.homeslidemenu.util.ViewAnimator;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class NewAppMainActivity extends AppCompatActivity
        implements ViewAnimator.ViewAnimatorListener
{
    private DrawerLayout drawerLayout;
    
    private ActionBarDrawerToggle drawerToggle;
    
    private List<SlideMenuItem> list = new ArrayList<>();
    
    private DateCommentFragment dateCommentFragment;
    
    private ViewAnimator viewAnimator;
    
    private int res = R.mipmap.content_music;
    
    private LinearLayout linearLayout;
    
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_app_main);
        dateCommentFragment = DateCommentFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, dateCommentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                drawerLayout.closeDrawers();
            }
        });
        
        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, dateCommentFragment,
                drawerLayout, this);
        LWDApp.getEventBus().register(this);
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    private void createMenuList()
    {
        SlideMenuItem menuItem0 = new SlideMenuItem(StaticDatas.DATE_COMMENT,
                R.mipmap.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(StaticDatas.TYPE_COMMENT,
                R.mipmap.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(StaticDatas.FILE_LIST,
                R.mipmap.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(StaticDatas.ABOUT_US,
                R.mipmap.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(StaticDatas.SETTING,
                R.mipmap.icn_4);
        list.add(menuItem4);
        
    }
    
    /**
     * 设置titlebar
     */
    private void setActionBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(
                TimeUtils.milliseconds2String(TimeUtils.getCurTimeMills(),
                        new SimpleDateFormat("yyyy年MM月", Locale.getDefault())));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                drawerLayout, /* DrawerLayout object */
                toolbar, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        )
        {
            
            /**
             * Called when a drawer has settled in a completely closed state.
             */
            @Override
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }
            
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                {
                    viewAnimator.showMenuContent();
                }
            }
            
            /** Called when a drawer has settled in a completely open state. */
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    
    private ScreenShotable replaceFragment(ScreenShotable screenShotable,
            int topPosition, String name)
    {
        this.res = this.res == R.mipmap.content_music ? R.mipmap.content_films
                : R.mipmap.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils
                .createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        
        findViewById(R.id.content_overlay).setBackgroundDrawable(
                new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ScreenShotable contentFragment;
        if (name.equals(StaticDatas.DATE_COMMENT))
        {
            toolbar.setTitle(TimeUtils.milliseconds2String(
                    TimeUtils.getCurTimeMills(),
                    new SimpleDateFormat("yyyy年MM月", Locale.getDefault())));
            contentFragment = DateCommentFragment.newInstance();
        }
        else
        {
            contentFragment = ContentFragment.newInstance(this.res);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, (BaseFragment) contentFragment)
                .commit();
        return contentFragment;
    }
    
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem,
            ScreenShotable screenShotable, int position)
    {
        switch (slideMenuItem.getName())
        {
            
            default:
                return replaceFragment(screenShotable,
                        position,
                        slideMenuItem.getName());
        }
    }
    
    @Override
    public void disableHomeButton()
    {
        getSupportActionBar().setHomeButtonEnabled(false);
    }
    
    @Override
    public void enableHomeButton()
    {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }
    
    @Override
    public void addViewToContainer(View view)
    {
        linearLayout.addView(view);
    }
    
    @Subscribe
    public void onMonthChangeEventResult(MonthChangeEvent event)
    {
        if (event != null)
        {
            toolbar.setTitle(event.getYear() + "年" + event.getMonth() + "月");
        }
    }
    
    @Subscribe
    public void onDateClickEventResult(DateClickEvent event)
    {
        if (event != null)
        {
            // toolbar.setTitle(event.getYear() + "年" + event.getMonth() + "月"
            // + event.getDay() + "日");
        }
    }
}
