package com.lawwing.lwdocument;

import static com.lawwing.lwdocument.base.StaticDatas.CLOSEWHEEL;
import static com.lawwing.lwdocument.base.StaticDatas.DATE_COMMENT;
import static com.lawwing.lwdocument.base.StaticDatas.OPENWHEEL;
import static com.lawwing.lwdocument.base.StaticDatas.SETTING;
import static com.lawwing.lwdocument.base.StaticDatas.TYPE_COMMENT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.base.StaticDatas;
import com.lawwing.lwdocument.event.AddTypeEvent;
import com.lawwing.lwdocument.event.ChangeTitleContent;
import com.lawwing.lwdocument.event.CommentListMoreEvent;
import com.lawwing.lwdocument.event.DateClickEvent;
import com.lawwing.lwdocument.event.DeleteCommentEvent;
import com.lawwing.lwdocument.event.MonthChangeEvent;
import com.lawwing.lwdocument.event.OpenWheelEvent;
import com.lawwing.lwdocument.fragment.AboutUsFragment;
import com.lawwing.lwdocument.fragment.ContentFragment;
import com.lawwing.lwdocument.fragment.DateCommentFragment;
import com.lawwing.lwdocument.fragment.SelectFileFragment;
import com.lawwing.lwdocument.fragment.TransTypeDialogFragment;
import com.lawwing.lwdocument.fragment.TypeCommentFragment;
import com.lawwing.lwdocument.fragment.TypeManagerFragment;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.utils.ScaleAnimationUtils;
import com.lawwing.lwdocument.utils.ScreenUtils;
import com.lawwing.lwdocument.utils.ShareUtils;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.Manifest;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.lawwing.homeslidemenu.interfaces.Resourceble;
import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;
import cn.lawwing.homeslidemenu.model.SlideMenuItem;
import cn.lawwing.homeslidemenu.util.ViewAnimator;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import pub.devrel.easypermissions.EasyPermissions;

public class NewAppMainActivity extends AppCompatActivity implements
        ViewAnimator.ViewAnimatorListener, EasyPermissions.PermissionCallbacks
{
    private DrawerLayout drawerLayout;
    
    private RelativeLayout container_frame;
    
    private ActionBarDrawerToggle drawerToggle;
    
    private List<SlideMenuItem> list = new ArrayList<>();
    
    private DateCommentFragment dateCommentFragment;
    
    private ViewAnimator viewAnimator;
    
    private int res = R.mipmap.content_music;
    
    private LinearLayout linearLayout;
    
    private LinearLayout itemMenuLayout;
    
    private LinearLayout animlayout;
    
    private LinearLayout shareQQLayout;
    
    private LinearLayout shareWechatLayout;
    
    private LinearLayout shareWechatMemLayout;
    
    private LinearLayout shareQZoneLayout;
    
    private LinearLayout shareWeiboLayout;
    
    private LinearLayout checkOfficeBtn;
    
    private LinearLayout moveToOtherType;
    
    private LinearLayout deleteComment;
    
    private ImageView rightTwoBtn;
    
    private ImageView rightOneBtn;
    
    private Toolbar toolbar;
    
    private TransTypeDialogFragment transTypeFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_app_main);
        dateCommentFragment = DateCommentFragment.newInstance();
        // 修复BUG
        contentFragment = dateCommentFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, dateCommentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        container_frame = (RelativeLayout) findViewById(R.id.container_frame);
        itemMenuLayout = (LinearLayout) findViewById(R.id.itemMenuLayout);
        animlayout = (LinearLayout) findViewById(R.id.animlayout);
        rightTwoBtn = (ImageView) findViewById(R.id.rightTwoBtn);
        rightOneBtn = (ImageView) findViewById(R.id.rightOneBtn);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        initShareEvent();
        
        initOtherCommentMenu();
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
        
        itemMenuLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                closeMenu();
            }
        });
        rightOneBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LWDApp.getEventBus().post(new AddTypeEvent());
            }
        });
        rightTwoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(CommentRecycleActivity
                        .newInstance(NewAppMainActivity.this));
            }
        });
        LWDApp.getEventBus().register(this);
        
        String[] perms = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (!EasyPermissions.hasPermissions(this, perms))
        {
            EasyPermissions
                    .requestPermissions(this, "需要访问手机存储权限！", 10086, perms);
        }
        else
        {
            initView();
        }
    }
    
    private void initOtherCommentMenu()
    {
        checkOfficeBtn = (LinearLayout) findViewById(R.id.checkOfficeBtn);
        moveToOtherType = (LinearLayout) findViewById(R.id.moveToOtherType);
        deleteComment = (LinearLayout) findViewById(R.id.deleteComment);
        checkOfficeBtn.setOnClickListener(otherCommentMenuClick);
        moveToOtherType.setOnClickListener(otherCommentMenuClick);
        deleteComment.setOnClickListener(otherCommentMenuClick);
    }
    
    private View.OnClickListener otherCommentMenuClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.checkOfficeBtn:
                    startActivity(
                            MainActivity.newInstance(NewAppMainActivity.this,
                                    selectCommentInfo.getDocpath()));
                    break;
                case R.id.moveToOtherType:
                    isMoveToOther = true;
                    break;
                case R.id.deleteComment:
                    new AlertDialog.Builder(NewAppMainActivity.this)
                            .setTitle("警告")
                            .setMessage("是否删除该批阅，删除后将无法查看")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("删除",
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which)
                                        {
                                            LWDApp.getEventBus().post(
                                                    new DeleteCommentEvent(
                                                            selectCommentInfo,
                                                            selectPoision));
                                        }
                                    })
                            .create()
                            .show();
                    break;
            }
            closeMenu();
        }
    };
    
    private boolean isMoveToOther = false;
    
    private void showTransTypeDialog()
    {
        transTypeFragment = new TransTypeDialogFragment(selectCommentInfo,
                selectPoision);
        transTypeFragment.show(getFragmentManager(), "更换分类");
    }
    
    private void initShareEvent()
    {
        shareQQLayout = (LinearLayout) findViewById(R.id.shareQQLayout);
        shareWechatLayout = (LinearLayout) findViewById(R.id.shareWechatLayout);
        shareWechatMemLayout = (LinearLayout) findViewById(
                R.id.shareWechatMemLayout);
        shareQZoneLayout = (LinearLayout) findViewById(R.id.shareQZoneLayout);
        shareWeiboLayout = (LinearLayout) findViewById(R.id.shareWeiboLayout);
        shareQQLayout.setOnClickListener(shareClick);
        shareWechatLayout.setOnClickListener(shareClick);
        shareWechatMemLayout.setOnClickListener(shareClick);
        shareQZoneLayout.setOnClickListener(shareClick);
        shareWeiboLayout.setOnClickListener(shareClick);
    }
    
    private PlatformActionListener sharelistener = new PlatformActionListener()
    {
        
        @Override
        public void onComplete(Platform platform, int i,
                HashMap<String, Object> hashMap)
        {
            showShortToast("分享成功");
        }
        
        @Override
        public void onError(Platform platform, int i, Throwable throwable)
        {
            showShortToast("分享失败");
        }
        
        @Override
        public void onCancel(Platform platform, int i)
        {
            showShortToast("分享取消");
        }
    };
    
    private View.OnClickListener shareClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.shareQQLayout:
                    ShareUtils.showImageShare(true,
                            QQ.NAME,
                            "QQ分享",
                            "QQ分享",
                            selectCommentInfo.getPath(),
                            sharelistener);
                    break;
                case R.id.shareWechatLayout:
                    
                    ShareUtils.showImageShare(true,
                            Wechat.NAME,
                            "微信分享",
                            "微信分享",
                            selectCommentInfo.getPath(),
                            sharelistener);
                    break;
                case R.id.shareWechatMemLayout:
                    
                    ShareUtils.showImageShare(true,
                            WechatMoments.NAME,
                            "朋友圈分享",
                            "朋友圈分享",
                            selectCommentInfo.getPath(),
                            sharelistener);
                    
                    break;
                case R.id.shareQZoneLayout:
                    
                    ShareUtils.showImageShare(true,
                            QZone.NAME,
                            "QQ空间分享",
                            "QQ空间分享",
                            selectCommentInfo.getPath(),
                            sharelistener);
                    break;
                case R.id.shareWeiboLayout:
                    
                    ShareUtils.showImageShare(true,
                            SinaWeibo.NAME,
                            "QQ邀请",
                            "QQ邀请",
                            selectCommentInfo.getPath(),
                            sharelistener);
                    break;
                default:
                    break;
            }
            closeMenu();
        }
    };
    
    private void closeMenu()
    {
        if (isUpMenu)
        {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(ScaleAnimationUtils
                    .leftUpToRightDownSmallAnimation(closeListen, 500));
            animlayout.startAnimation(animationSet);
        }
        else
        {
            
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(ScaleAnimationUtils
                    .leftDownToRightUpSmallAnimation(closeListen, 500));
            animlayout.startAnimation(animationSet);
        }
    }
    
    private Animation.AnimationListener closeListen = new Animation.AnimationListener()
    {
        @Override
        public void onAnimationStart(Animation animation)
        {
            
        }
        
        @Override
        public void onAnimationEnd(Animation animation)
        {
            itemMenuLayout.setVisibility(View.GONE);
            if (isMoveToOther)
            {
                showTransTypeDialog();
            }
            isMoveToOther = false;
        }
        
        @Override
        public void onAnimationRepeat(Animation animation)
        {
            
        }
    };
    
    private void initView()
    {
        
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    private void createMenuList()
    {
        SlideMenuItem menuItem0 = new SlideMenuItem(DATE_COMMENT,
                R.mipmap.icn_date_comment);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(TYPE_COMMENT,
                R.mipmap.icn_type_comment);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(StaticDatas.FILE_LIST,
                R.mipmap.icn_file_list);
        list.add(menuItem2);
        SlideMenuItem menuItem4 = new SlideMenuItem(StaticDatas.SETTING,
                R.mipmap.icn_setting);
        list.add(menuItem4);
        SlideMenuItem menuItem3 = new SlideMenuItem(StaticDatas.ABOUT_US,
                R.mipmap.icn_about_us);
        list.add(menuItem3);
        
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
        toolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (nowItemName.equals(TYPE_COMMENT))
                {
                    LWDApp.getEventBus().post(new OpenWheelEvent(OPENWHEEL));
                }
            }
        });
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
                LWDApp.getEventBus().post(new OpenWheelEvent(CLOSEWHEEL));
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
    
    private ScreenShotable contentFragment;
    
    private ScreenShotable replaceFragment(ScreenShotable screenShotable,
            int topPosition, String name)
    {
        
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils
                .createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        
        findViewById(R.id.content_overlay).setBackgroundDrawable(
                new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        
        if (name.equals(SETTING))
        {
            rightOneBtn.setVisibility(View.VISIBLE);
            rightTwoBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            rightOneBtn.setVisibility(View.GONE);
            rightTwoBtn.setVisibility(View.GONE);
            
        }
        
        if (name.equals(DATE_COMMENT))
        {
            toolbar.setTitle(TimeUtils.milliseconds2String(
                    TimeUtils.getCurTimeMills(),
                    new SimpleDateFormat("yyyy年MM月", Locale.getDefault())));
            contentFragment = DateCommentFragment.newInstance();
        }
        else if (name.equals(StaticDatas.FILE_LIST))
        {
            toolbar.setTitle("文档列表");
            contentFragment = SelectFileFragment.newInstance();
        }
        else if (name.equals(StaticDatas.ABOUT_US))
        {
            toolbar.setTitle("关于我们");
            contentFragment = AboutUsFragment.newInstance();
        }
        else if (name.equals(TYPE_COMMENT))
        {
            toolbar.setTitle("批阅分类-全部 ⇓");
            contentFragment = TypeCommentFragment.newInstance();
        }
        else if (name.equals(SETTING))
        {
            toolbar.setTitle("分类管理");
            contentFragment = TypeManagerFragment.newInstance();
        }
        else
        {
            contentFragment = ContentFragment.newInstance(this.res);
        }
        
        if (name != nowItemName)
        {
            nowItemName = name;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, (BaseFragment) contentFragment)
                    .commit();
        }
        return contentFragment;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }
    
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem,
            ScreenShotable screenShotable, int position)
    {
        switch (slideMenuItem.getName())
        {
            
            default:
                if (nowItemName == slideMenuItem.getName())
                {
                    return contentFragment;
                }
                return replaceFragment(screenShotable,
                        position,
                        slideMenuItem.getName());
        }
    }
    
    private String nowItemName = DATE_COMMENT;
    
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
    
    // 记录用户首次点击返回键的时间
    private long firstTime = 0;
    
    /**
     * 第一种解决办法 onKeyDown
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawers();
                return true;
            }
            if (isOpenMenu())
            {
                closeMenu();
                return true;
            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000)
            {
                Toast.makeText(NewAppMainActivity.this,
                        "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            }
            else
            {
                System.exit(0);
            }
        }
        
        return super.onKeyUp(keyCode, event);
    }
    
    private boolean isOpenMenu()
    {
        if (itemMenuLayout.getVisibility() == View.VISIBLE)
        {
            return true;
        }
        else
        {
            return false;
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
                initView();
            }
        }
    }
    
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        
        // 失败
        if (requestCode == 10086)
        {
            showShortToast("请务必打开全部权限才能扫描文档");
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
    
    protected void showShortToast(String message)
    {
        
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
    }
    
    @Subscribe
    public void changeTitleContent(ChangeTitleContent event)
    {
        if (event != null)
        {
            toolbar.setTitle(event.getTitle());
        }
    }
    
    private CommentInfoModel selectCommentInfo;
    
    private int selectPoision = -1;
    
    @Subscribe
    public void moreClick(CommentListMoreEvent event)
    {
        if (event != null)
        {
            selectCommentInfo = event.getModel();
            selectPoision = event.getPoision();
            itemMenuLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) animlayout
                    .getLayoutParams();
            AnimationSet animationSet = new AnimationSet(true);
            int baseY = 0;
            int marginTop = 0;
            int bijiaoY = 0;
            switch (event.getFlag())
            {
                case "日历更多操作":
                    baseY = event.getY();
                    bijiaoY = (int) (ScreenUtils.getScreenHeight(this)
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.activity_title_height) * 1.5
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.menulayout_height));
                    marginTop = baseY
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.menulayout_height)
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.image_height) * 2;
                    
                    break;
                case "类型更多操作":
                    baseY = event.getY();
                    bijiaoY = (int) ScreenUtils.getScreenHeight(this)
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.menulayout_height)
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.menu_item_height);
                    marginTop = baseY
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.menulayout_height)
                            - getResources().getDimensionPixelOffset(
                                    R.dimen.image_height) * 2;
                    
                    break;
                default:
                    break;
            }
            
            if (bijiaoY < baseY)
            {
                isUpMenu = true;
                params.topMargin = marginTop;
                // 上面
                animationSet.addAnimation(
                        ScaleAnimationUtils.rightDownToLeftUpBiggerAnimation(
                                new Animation.AnimationListener()
                                {
                                    @Override
                                    public void onAnimationStart(
                                            Animation animation)
                                    {
                                        
                                    }
                                    
                                    @Override
                                    public void onAnimationEnd(
                                            Animation animation)
                                    {
                                    }
                                    
                                    @Override
                                    public void onAnimationRepeat(
                                            Animation animation)
                                    {
                                        
                                    }
                                },
                                500));
                
            }
            else
            {
                params.topMargin = baseY + 10;
                
                isUpMenu = false;
                animationSet.addAnimation(
                        ScaleAnimationUtils.rightUpToLeftDownBiggerAnimation(
                                new Animation.AnimationListener()
                                {
                                    
                                    @Override
                                    public void onAnimationStart(
                                            Animation animation)
                                    {
                                        
                                    }
                                    
                                    @Override
                                    public void onAnimationEnd(
                                            Animation animation)
                                    {
                                    }
                                    
                                    @Override
                                    public void onAnimationRepeat(
                                            Animation animation)
                                    {
                                        
                                    }
                                },
                                500));
            }
            animlayout.setLayoutParams(params);
            
            animlayout.startAnimation(animationSet);
        }
    }
    
    private boolean isUpMenu = true;
}
