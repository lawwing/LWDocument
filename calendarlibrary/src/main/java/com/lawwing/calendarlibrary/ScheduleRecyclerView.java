package com.lawwing.calendarlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lawwing on 2017/11/21.
 */
public class ScheduleRecyclerView extends RecyclerView
{
    
    public ScheduleRecyclerView(Context context)
    {
        this(context, null);
    }
    
    public ScheduleRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public ScheduleRecyclerView(Context context, @Nullable AttributeSet attrs,
            int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    public boolean isScrollTop()
    {
        return computeVerticalScrollOffset() == 0;
    }
    
    public void requestChildFocus(View child, View focused)
    {
        super.requestChildFocus(child, focused);
        if (getOnFocusChangeListener() != null)
        {
            getOnFocusChangeListener().onFocusChange(child, false);
            getOnFocusChangeListener().onFocusChange(focused, true);
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec) - 1);
    }
    
    private int measureHeight(int measureSpec)
    {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        
        if (mode == MeasureSpec.EXACTLY)
        {
            result = size;
        }
        else
        {
            result = 75;
            if (mode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, size);
            }
        }
        return result;
        
    }
    
    private int measureWidth(int measureSpec)
    {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        
        if (mode == MeasureSpec.EXACTLY)
        {
            result = size;
        }
        else
        {
            result = 75;// 根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, size);
            }
        }
        return result;
        
    }
    
}