package com.lawwing.lwdocument;

import java.util.HashMap;

import com.lawwing.lwdocument.base.BaseActivity;
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
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.moments.WechatMoments;

public class CheckCommentPicActivity extends BaseActivity
{
    
    @BindView(R.id.commentBigImage)
    ImageView commentBigImage;
    
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    
    private String path = "";
    
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
                ShareUtils.showImageShare(true,
                        QQ.NAME,
                        "QQ邀请",
                        "QQ邀请",
                        path,
                        listener);
            }
        });
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
