package com.lawwing.calendarlibrary.listener;

/**
 * Created by lawwing on 2017/11/21.
 */
public interface OnMonthClickListener
{
    void onClickThisMonth(int year, int month, int day);
    
    void onClickLastMonth(int year, int month, int day);
    
    void onClickNextMonth(int year, int month, int day);
}
