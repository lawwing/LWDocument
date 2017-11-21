package com.lawwing.calendarlibrary.listener;

/**
 * Created by lawwing on 2017/11/21.
 */

public interface OnCalendarClickListener
{
    void onClickDate(int year, int month, int day);
    
    void onPageChange(int year, int month, int day);
}
