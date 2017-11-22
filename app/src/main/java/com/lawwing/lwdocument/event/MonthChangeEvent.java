package com.lawwing.lwdocument.event;

/**
 * Created by lawwing on 2017/11/22.
 */
public class MonthChangeEvent
{
    private int year;
    
    private int month;
    
    private int day;
    
    public MonthChangeEvent(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public int getMonth()
    {
        return month;
    }
    
    public int getDay()
    {
        return day;
    }
}
