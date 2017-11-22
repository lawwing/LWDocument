package com.lawwing.lwdocument.utils;

import com.lawwing.lwdocument.gen.SaveDateDb;
import com.lawwing.lwdocument.gen.SaveDateDbDao;

/**
 * Created by lawwing on 2017/11/22.
 */

public class DbUtils
{
    /**
     * 月份是显示多少就存多少
     * 
     * @param mSaveDateDbDao
     * @param year
     * @param month
     * @param day
     */
    public static long saveDateDb(SaveDateDbDao mSaveDateDbDao, int year,
            int month, int day)
    {
        long id = (long) (year * 10000 + month * 100 + day);
        SaveDateDb saveDateDb = new SaveDateDb();
        saveDateDb.setYear(year);
        saveDateDb.setMonth(month);
        saveDateDb.setDay(day);
        saveDateDb.setTimeString("" + (year * 10000 + month * 100 + day));
        saveDateDb.setId(id);
        mSaveDateDbDao.insertOrReplace(saveDateDb);
        return id;
    }
}
