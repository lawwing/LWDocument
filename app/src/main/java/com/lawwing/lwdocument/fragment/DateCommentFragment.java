package com.lawwing.lwdocument.fragment;

import com.lawwing.calendarlibrary.MonthCalendarView;
import com.lawwing.calendarlibrary.ScheduleLayout;
import com.lawwing.calendarlibrary.WeekCalendarView;
import com.lawwing.calendarlibrary.listener.OnCalendarClickListener;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.event.DateClickEvent;
import com.lawwing.lwdocument.event.MonthChangeEvent;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/21.
 */

public class DateCommentFragment extends BaseFragment
        implements ScreenShotable, OnCalendarClickListener
{
    private View containerView;
    
    private Bitmap bitmap;
    
    private MonthCalendarView mcvCalendar;
    
    private WeekCalendarView wcvCalendar;
    
    private ScheduleLayout slSchedule;
    
    private TextView dateText;
    
    public static DateCommentFragment newInstance()
    {
        DateCommentFragment contentFragment = new DateCommentFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater
                .inflate(R.layout.fragment_date_comment_list, container, false);
        
        mcvCalendar = (MonthCalendarView) rootView
                .findViewById(R.id.mcvCalendar);
        wcvCalendar = (WeekCalendarView) rootView
                .findViewById(R.id.wcvCalendar);
        slSchedule = (ScheduleLayout) rootView.findViewById(R.id.slSchedule);
        dateText = (TextView) rootView.findViewById(R.id.dateText);
        dateText.setText(TimeUtils.milliseconds2String(
                TimeUtils.getCurTimeMills(),
                new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())));
        mcvCalendar.setOnCalendarClickListener(this);
        wcvCalendar.setOnCalendarClickListener(this);
        slSchedule.setOnCalendarClickListener(this);
        return rootView;
    }
    
    @Override
    public void takeScreenShot()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                DateCommentFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
        
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
    }
    
    @Override
    public void onClickDate(int year, int month, int day)
    {
        dateText.setText(year + "年" + (month + 1) + "月" + day + "日");
        
        LWDApp.getEventBus().post(new DateClickEvent(year, month + 1, day));
    }
    
    @Override
    public void onPageChange(int year, int month, int day)
    {
        mcvCalendar.getCurrentMonthView().addTaskHint(11);
        wcvCalendar.getCurrentWeekView().addTaskHint(11);
        LWDApp.getEventBus().post(new MonthChangeEvent(year, month + 1, day));
    }
}
