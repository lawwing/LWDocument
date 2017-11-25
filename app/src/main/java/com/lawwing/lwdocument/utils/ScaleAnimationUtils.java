package com.lawwing.lwdocument.utils;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by lawwing on 2017/11/25. 后面四个参数是控制运动方向
 */

public class ScaleAnimationUtils
{
    
    /**
     * 右上到左下放大 已搞定
     *
     * @return
     */
    public static ScaleAnimation rightUpToLeftDownBiggerAnimation(
            Animation.AnimationListener listener, int duration)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.1f, 0.0f,
                1.1f, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
    
    /**
     * 右下到左上放大 已搞定
     *
     * @return
     */
    public static ScaleAnimation rightDownToLeftUpBiggerAnimation(
            Animation.AnimationListener listener, int duration)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.1f, 0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
    
    /**
     * 左下到右上放大(已搞定)
     *
     * @return
     */
    public static ScaleAnimation leftDownToRightUpBiggerAnimation(
            Animation.AnimationListener listener, int duration)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1.1f, 0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
    
    /**
     * 左上到右下放大 已搞定
     *
     * @return
     */
    public static ScaleAnimation leftUpToRightDownBiggerAnimation(
            Animation.AnimationListener listener, int duration)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1.1f, 0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
    
    /**
     * =====================================================================
     * =====================================================================
     * 
     * 下面是缩小
     * 
     * =====================================================================
     * =====================================================================
     */
    /**
     * 左上右下缩小 已搞定
     *
     * @return
     */
    public static ScaleAnimation leftUpToRightDownSmallAnimation(
            Animation.AnimationListener listener, int duration)
    {
        // 效果是左上右下
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
        
    }
    
    /**
     * 左下到右上缩小 已搞定
     *
     * @return
     */
    public static ScaleAnimation leftDownToRightUpSmallAnimation(
            Animation.AnimationListener listener, int duration)
    {
        // 效果是左下到右上
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
    
    /**
     * 右上到左下缩小(已搞定)
     *
     * @return
     */
    public static ScaleAnimation rightUpToLeftDownSmallAnimation(
            Animation.AnimationListener listener, int duration)
    {
        // 效果是右上到左下
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
    
    /**
     * 右下左上缩小 已搞定
     *
     * @return
     */
    public static ScaleAnimation rightDownToLeftUpSmallAnimation(
            Animation.AnimationListener listener, int duration)
    {
        // 右下左上
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }
}
