package com.lawwing.lwdocument.animations;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by lawwing on 2017/11/27.
 */

public class AlphaAnimationUtils
{
    /**
     * 
     * @param listener
     * @param duration
     * @param startAlpha
     * @param endAlpha
     * @return
     */
    public static AlphaAnimation alphaAnimation(
            Animation.AnimationListener listener, int duration,
            float startAlpha, float endAlpha)
    {
        AlphaAnimation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        return animation;
    }
}
