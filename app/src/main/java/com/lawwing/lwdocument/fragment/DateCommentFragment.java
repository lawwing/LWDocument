package com.lawwing.lwdocument.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lawwing.calendarlibrary.MonthCalendarView;
import com.lawwing.calendarlibrary.ScheduleLayout;
import com.lawwing.calendarlibrary.ScheduleRecyclerView;
import com.lawwing.calendarlibrary.WeekCalendarView;
import com.lawwing.calendarlibrary.listener.OnCalendarClickListener;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.DateCommentAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.event.DateClickEvent;
import com.lawwing.lwdocument.event.MonthChangeEvent;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.gen.SaveDateDb;
import com.lawwing.lwdocument.gen.SaveDateDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.SaveDateModel;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    
    private ScheduleRecyclerView rvScheduleList;
    
    private ScheduleLayout slSchedule;
    
    private TextView dateText;
    
    private LinearLayout rlNoTask;
    
    private SaveDateDbDao mSaveDateDbDao;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
    private DateCommentAdapter adapter;
    
    private ArrayList<CommentInfoModel> datas;
    
    public static DateCommentFragment newInstance()
    {
        DateCommentFragment contentFragment = new DateCommentFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    private boolean isInit = false;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        mSaveDateDbDao = LWDApp.get().getDaoSession().getSaveDateDbDao();
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        // DbUtils.saveDateDb(mSaveDateDbDao, 2017, 11, 28);
        new Thread()
        {
            @Override
            public void run()
            {
                while (!isInit)
                {
                    if (mcvCalendar.getCurrentMonthView() != null)
                    {
                        handler.sendMessage(new Message());
                        isInit = true;
                        break;
                    }
                    
                }
                super.run();
            }
        }.start();
        
    }
    
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            // 获取当前的月份
            SaveDateModel nowDate = TimeUtils.getCurDateModel();
            int year = nowDate.getYear();
            int month = nowDate.getMonth();
            int day = nowDate.getDay();
            initViewPoint(year, month);
            initDateList(year, month, day);
            super.handleMessage(msg);
        }
    };
    
    private void initViewPoint(int year, int month)
    {
        List<Integer> dates = new ArrayList<>();
        List<SaveDateDb> saveDbs = mSaveDateDbDao.queryBuilder()
                .where(SaveDateDbDao.Properties.Year.eq(year))
                .where(SaveDateDbDao.Properties.Month.eq(month))
                .build()
                .list();
        for (SaveDateDb temp : saveDbs)
        {
            dates.add(temp.getDay());
        }
        mcvCalendar.getCurrentMonthView().addTaskHints(dates);
        wcvCalendar.getCurrentWeekView().addTaskHints(dates);
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
        rvScheduleList = (ScheduleRecyclerView) rootView
                .findViewById(R.id.rvScheduleList);
        slSchedule = (ScheduleLayout) rootView.findViewById(R.id.slSchedule);
        rlNoTask = (LinearLayout) rootView.findViewById(R.id.rlNoTask);
        dateText = (TextView) rootView.findViewById(R.id.dateText);
        dateText.setText(TimeUtils.milliseconds2String(
                TimeUtils.getCurTimeMills(),
                new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())));
        mcvCalendar.setOnCalendarClickListener(this);
        wcvCalendar.setOnCalendarClickListener(this);
        slSchedule.setOnCalendarClickListener(this);
        initRecycler();
        return rootView;
    }
    
    private void initRecycler()
    {
        datas = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvScheduleList.setLayoutManager(manager);
        
        adapter = new DateCommentAdapter(datas, getActivity());
        rvScheduleList.setAdapter(adapter);
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
        initDateList(year, month + 1, day);
        mcvCalendar.getCurrentMonthView().setSelectYearMonth(year, month, day);
        wcvCalendar.getCurrentWeekView().setSelectYearMonth(year, month, day);
        LWDApp.getEventBus().post(new DateClickEvent(year, month + 1, day));
    }
    
    private void initDateList(int year, int month, int day)
    {
        datas.clear();
        List<SaveDateDb> resultDatas = mSaveDateDbDao.queryBuilder()
                .where(SaveDateDbDao.Properties.Year.eq(year))
                .where(SaveDateDbDao.Properties.Month.eq(month))
                .where(SaveDateDbDao.Properties.Day.eq(day))
                .list();
        
        for (SaveDateDb db : resultDatas)
        {
            List<CommentInfoDb> commentInfoDbs = db.getCommentInfoDbs();
            for (CommentInfoDb commentInfoDb : commentInfoDbs)
            {
                CommentInfoModel model = new CommentInfoModel();
                model.setDocname(commentInfoDb.getDocname());
                model.setDocpath(commentInfoDb.getDocpath());
                model.setId(commentInfoDb.getId());
                model.setName(commentInfoDb.getName());
                model.setPath(commentInfoDb.getPath());
                model.setTime(commentInfoDb.getTime());
                model.setTypeId(commentInfoDb.getTypeId());
                datas.add(model);
            }
        }
        if (datas.size() > 0)
        {
            rlNoTask.setVisibility(View.GONE);
        }
        else
        {
            rlNoTask.setVisibility(View.VISIBLE);
            
        }
        adapter.notifyDataSetChanged();
    }
    
    @Override
    public void onPageChange(int year, int month, int day)
    {
        initViewPoint(year, month + 1);
        LWDApp.getEventBus().post(new MonthChangeEvent(year, month + 1, day));
    }
}
