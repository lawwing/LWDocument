package com.lawwing.lwdocument.utils;

import com.lawwing.lwdocument.LWDApp;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * author lawwing time 2017/4/6 10:33 describe
 **/
public class ShareUtils {
    /**
     * @param silent   暂时填true
     * @param platform 平台
     * @param title    标题
     * @param content  内容
     * @param url      分享的链接
     * @param imageUrl 分享的头像路径
     * @param listener 监听器，用来监听分享成功或者失败后进行的操作
     */
    public static void showShare(boolean silent, String platform, String title,
                                 String content, String url, String imageUrl,
                                 PlatformActionListener listener) {
        final OnekeyShare oks = new OnekeyShare();
        ShareSDK.initSDK(LWDApp.get().getApplicationContext());
        oks.disableSSOWhenAuthorize();

        if (platform != null) {
            oks.setPlatform(platform);
        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(content);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(title);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        oks.setImageUrl(imageUrl);
        // 启动分享GUI
        oks.show(LWDApp.get().getApplicationContext());

        Platform platform1 = ShareSDK
                .getPlatform(LWDApp.get().getApplicationContext(), platform);

        platform1.setPlatformActionListener(listener);
    }

    /**
     * @param silent
     * @param platform
     * @param title
     * @param content
     * @param path
     * @param listener
     */
    public static void showImageShare(boolean silent, String platform, String title, String content, String path, PlatformActionListener listener) {
        final OnekeyShare oks = new OnekeyShare();
        ShareSDK.initSDK(LWDApp.get().getApplicationContext());
        oks.disableSSOWhenAuthorize();

        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.setImagePath(path);
        oks.show(LWDApp.get().getApplicationContext());
        Platform platform1 = ShareSDK
                .getPlatform(LWDApp.get().getApplicationContext(), platform);

        platform1.setPlatformActionListener(listener);
    }
}
