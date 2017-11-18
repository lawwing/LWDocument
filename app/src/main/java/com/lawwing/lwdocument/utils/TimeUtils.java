package com.lawwing.lwdocument.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lawwing on 2017/11/8.
 */

public class TimeUtils {
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills() {
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
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>
     * 格式为用户自定义
     * </p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds,
                                             SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

}
