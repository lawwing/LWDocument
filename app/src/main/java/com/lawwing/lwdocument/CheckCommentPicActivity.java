package com.lawwing.lwdocument;

import java.util.HashMap;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.event.ShareSelectEvent;
import com.lawwing.lwdocument.fragment.ShareDialogFregment;
import com.lawwing.lwdocument.utils.GlideUtils;
import com.lawwing.lwdocument.utils.ShareUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class CheckCommentPicActivity extends BaseActivity
{
    
    @BindView(R.id.commentBigImage)
    ImageView commentBigImage;
    
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    
    private String path = "";
    
    private ShareDialogFregment fregment;
    
    public static Intent newInstance(Activity activity, String path)
    {
        Intent intent = new Intent(activity, CheckCommentPicActivity.class);
        intent.putExtra("path", path);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getIntentData();
        setContentView(R.layout.activity_check_comment_pic);
        ButterKnife.bind(this);
        GlideUtils.loadNormalPicture(path, commentBigImage);
        shareBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fregment = new ShareDialogFregment(CheckCommentPicActivity.this,
                        path);
                fregment.show(getFragmentManager(), "分享");
                // ShareUtils.showImageShare(true,
                // QQ.NAME,
                // "QQ邀请",
                // "QQ邀请",
                // path,
                // listener);
            }
        });
        LWDApp.getEventBus().register(this);
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    @Subscribe
    public void onEventMainThread(ShareSelectEvent event)
    {
        if (event != null)
        {
            if (fregment != null)
            {
                fregment.dismiss();
            }
            switch (event.getModel().getName())
            {
                case "QQ分享":
                    ShareUtils.showImageShare(true,
                            QQ.NAME,
                            "QQ分享",
                            "QQ分享",
                            path,
                            listener);
                    
                    break;
                case "微信分享":
                    ShareUtils.showImageShare(true,
                            Wechat.NAME,
                            "微信分享",
                            "微信分享",
                            path,
                            listener);
                    
                    break;
                case "朋友圈分享":
                    ShareUtils.showImageShare(true,
                            WechatMoments.NAME,
                            "朋友圈分享",
                            "朋友圈分享",
                            path,
                            listener);
                    
                    break;
                case "QQ空间分享":
                    ShareUtils.showImageShare(true,
                            QZone.NAME,
                            "QQ空间分享",
                            "QQ空间分享",
                            path,
                            listener);
                    
                    break;
                case "微博分享":
                    ShareUtils.showImageShare(true,
                            SinaWeibo.NAME,
                            "QQ邀请",
                            "QQ邀请",
                            path,
                            listener);
                    
                    break;
            }
        }
    }
    
    private PlatformActionListener listener = new PlatformActionListener()
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
    
    private void getIntentData()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            if (intent.hasExtra("path"))
            {
                path = intent.getStringExtra("path");
            }
        }
    }
}
