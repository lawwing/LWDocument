package com.lawwing.lwdocument.model;

/**
 * Created by lawwing on 2017/11/22.
 */

public class SaveDateModel
{
    private Long id;
    
    private int year;
    
    private int month;
    
    private int day;
    
    // 格式2017-11-22
    private String timeString;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public void setYear(int year)
    {
        this.year = year;
    }
    
    public int getMonth()
    {
        return month;
    }
    
    public void setMonth(int month)
    {
        this.month = month;
    }
    
    public int getDay()
    {
        return day;
    }
    
    public void setDay(int day)
    {
        this.day = day;
    }
    
    public String getTimeString()
    {
        return timeString;
    }
    
    public void setTimeString(String timeString)
    {
        this.timeString = timeString;
    }
}
