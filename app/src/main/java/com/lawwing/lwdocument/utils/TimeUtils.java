package com.lawwing.lwdocument.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.lawwing.lwdocument.model.SaveDateModel;

/**
 * Created by lawwing on 2017/11/8.
 */

public class TimeUtils
{
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    
    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills()
    {
        return System.currentTimeMillis();
    }
    
    /**
     * 将时间戳转为时间字符串
     * <p>
     * 格式为yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds)
    {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }
    
    /**
     * 将时间戳转为时间字符串
     * <p>
     * 格式为用户自定义
     * </p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds,
            SimpleDateFormat format)
    {
        return format.format(new Date(milliseconds));
    }
    
    public static SaveDateModel getCurDateModel()
    {
        SaveDateModel model = new SaveDateModel();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurTimeMills());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        model.setYear(year);
        model.setMonth(month);
        model.setDay(day);
        model.setTimeString("" + (year * 10000 + month * 100 + day));
        model.setId((long) (year * 10000 + month * 100 + day));
        return model;
    }
}
