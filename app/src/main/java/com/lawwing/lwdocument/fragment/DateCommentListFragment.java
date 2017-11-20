package com.lawwing.lwdocument.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import com.lawwing.dateselectview.CalendarHelper;
import com.lawwing.dateselectview.CalendarListView;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.CalendarItemAdapter;
import com.lawwing.lwdocument.adapter.CommentDateListAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.model.CommentCalendarItemModel;
import com.lawwing.lwdocument.model.CommentInfoModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/20.
 */

public class DateCommentListFragment extends BaseFragment
        implements ScreenShotable
{
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(
            "yyyyMMdd");
    
    public static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat(
            "yyyy年MM月");
    
    private TreeMap<String, List<CommentInfoModel>> listTreeMap = new TreeMap<>();
    
    private Handler handler = new Handler();
    
    private Bitmap bitmap;
    
    private View containerView;
    
    private CalendarListView calendarListView;
    
    private CommentDateListAdapter dayNewsListAdapter;
    
    private CalendarItemAdapter calendarItemAdapter;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
    public static DateCommentListFragment newInstance(int resId)
    {
        DateCommentListFragment dateCommentListFragment = new DateCommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        dateCommentListFragment.setArguments(bundle);
        return dateCommentListFragment;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        this.containerView = view.findViewById(R.id.container);
        
        calendarListView = (CalendarListView) view
                .findViewById(R.id.calendar_listview);
        
        dayNewsListAdapter = new CommentDateListAdapter(getActivity());
        calendarItemAdapter = new CalendarItemAdapter(getActivity());
        calendarListView.setCalendarListViewAdapter(calendarItemAdapter,
                dayNewsListAdapter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -7);
        loadCommentList(DAY_FORMAT.format(calendar.getTime()));
        // actionBar.setTitle(YEAR_MONTH_FORMAT.format(calendar.getTime()));
        calendarListView
                .setOnListPullListener(new CalendarListView.onListPullListener()
                {
                    @Override
                    public void onRefresh()
                    {
                        String date = listTreeMap.firstKey();
                        Calendar calendar = getCalendarByYearMonthDay(date);
                        calendar.add(Calendar.MONTH, -1);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        loadCommentList(DAY_FORMAT.format(calendar.getTime()));
                    }
                    
                    @Override
                    public void onLoadMore()
                    {
                        String date = listTreeMap.lastKey();
                        Calendar calendar = getCalendarByYearMonthDay(date);
                        calendar.add(Calendar.MONTH, 1);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        loadCommentList(DAY_FORMAT.format(calendar.getTime()));
                    }
                });
        
        //
        calendarListView.setOnMonthChangedListener(
                new CalendarListView.OnMonthChangedListener()
                {
                    @Override
                    public void onMonthChanged(String yearMonth)
                    {
                        Calendar calendar = CalendarHelper
                                .getCalendarByYearMonth(yearMonth);
                        // actionBar.setTitle(
                        // YEAR_MONTH_FORMAT.format(calendar.getTime()));
                        loadCalendarData(yearMonth);
                        Toast.makeText(getActivity(),
                                YEAR_MONTH_FORMAT.format(calendar.getTime()),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        
        calendarListView.setOnCalendarViewItemClickListener(
                new CalendarListView.OnCalendarViewItemClickListener()
                {
                    @Override
                    public void onDateSelected(View View, String selectedDate,
                            int listSection,
                            SelectedDateRegion selectedDateRegion)
                    {
                        
                    }
                });
    }
    
    private void loadCalendarData(final String date)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(1000);
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Random random = new Random();
                            if (date.equals(
                                    calendarListView.getCurrentSelectedDate()
                                            .substring(0, 7)))
                            {
                                for (String d : listTreeMap.keySet())
                                {
                                    if (date.equals(d.substring(0, 7)))
                                    {
                                        CommentCalendarItemModel customCalendarItemModel = calendarItemAdapter
                                                .getDayModelList()
                                                .get(d);
                                        if (customCalendarItemModel != null)
                                        {
                                            customCalendarItemModel
                                                    .setCommentCount(
                                                            listTreeMap.get(d)
                                                                    .size());
                                            customCalendarItemModel.setFav(
                                                    random.nextBoolean());
                                        }
                                        
                                    }
                                }
                                calendarItemAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
        }.start();
    }
    
    private void loadCommentList(String date)
    {
        Calendar calendar = getCalendarByYearMonthDay(date);
        String key = CalendarHelper.YEAR_MONTH_FORMAT
                .format(calendar.getTime());
        Random random = new Random();
        final List<String> set = new ArrayList<>();
        while (set.size() < 5)
        {
            int i = random.nextInt(29);
            if (i > 0)
            {
                if (!set.contains(key + "-" + i))
                {
                    if (i < 10)
                    {
                        set.add(key + "-0" + i);
                    }
                    else
                    {
                        set.add(key + "-" + i);
                    }
                }
            }
        }
        List<CommentInfoDb> dbs = mCommentInfoDbDao.loadAll();
        
        // 获取数据
        for (int i = 0; i < dbs.size(); i++)
        {
            CommentInfoModel model = new CommentInfoModel();
            model.setName(dbs.get(i).getName());
            model.setDocname(dbs.get(i).getDocname());
            model.setTime(dbs.get(i).getTime());
            model.setPath(dbs.get(i).getPath());
            model.setDocpath(dbs.get(i).getDocpath());
            model.setId(dbs.get(i).getId());
            model.setTypeId(dbs.get(i).getTypeId());
            int index = random.nextInt(5);
            String day = set.get(index);
            if (listTreeMap.get(day) != null)
            {
                List<CommentInfoModel> list = listTreeMap.get(day);
                list.add(model);
            }
            else
            {
                List<CommentInfoModel> list = new ArrayList<CommentInfoModel>();
                list.add(model);
                listTreeMap.put(day, list);
            }
        }
        
        dayNewsListAdapter.setDateDataMap(listTreeMap);
        dayNewsListAdapter.notifyDataSetChanged();
        calendarItemAdapter.notifyDataSetChanged();
    }
    
    public static Calendar getCalendarByYearMonthDay(String yearMonthDay)
    {
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTimeInMillis(DAY_FORMAT.parse(yearMonthDay).getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return calendar;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater
                .inflate(R.layout.fragment_date_comment_list, container, false);
        return rootView;
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
                DateCommentListFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
}
