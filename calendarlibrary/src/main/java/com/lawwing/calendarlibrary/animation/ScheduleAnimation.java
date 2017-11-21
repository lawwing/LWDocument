package com.lawwing.calendarlibrary.animation;

import com.lawwing.calendarlibrary.ScheduleLayout;
import com.lawwing.calendarlibrary.model.ScheduleState;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * Created by lawwing on 2017/11/21.
 */
public class ScheduleAnimation extends Animation
{
    
    private ScheduleLayout mScheduleLayout;
    
    private ScheduleState mState;
    
    private float mDistanceY;
    
    public ScheduleAnimation(ScheduleLayout scheduleLayout, ScheduleState state,
            float distanceY)
    {
        mScheduleLayout = scheduleLayout;
        mState = state;
        mDistanceY = distanceY;
        setDuration(300);
        setInterpolator(new DecelerateInterpolator(1.5f));
    }
    
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        super.applyTransformation(interpolatedTime, t);
        if (mState == ScheduleState.OPEN)
        {
            mScheduleLayout.onCalendarScroll(mDistanceY);
        }
        else
        {
            mScheduleLayout.onCalendarScroll(-mDistanceY);
        }
    }
    
}
