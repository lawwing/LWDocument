package com.lawwing.lwdocument.adapter;

import com.lawwing.dateselectview.BaseCalendarListAdapter;
import com.lawwing.lwdocument.model.CommentInfoModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lawwing on 2017/11/20. 日历下面数据的适配器
 */

public class CommentDateListAdapter
        extends BaseCalendarListAdapter<CommentInfoModel>
{
    public CommentDateListAdapter(Context context)
    {
        super(context);
    }
    
    @Override
    public View getItemView(CommentInfoModel model, String month, int pos,
            View convertView, ViewGroup parent)
    {
        return null;
    }
    
    @Override
    public View getSectionHeaderView(String date, View convertView,
            ViewGroup parent)
    {
        return super.getSectionHeaderView(date, convertView, parent);
    }
}
