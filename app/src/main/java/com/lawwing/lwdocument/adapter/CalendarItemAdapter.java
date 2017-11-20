package com.lawwing.lwdocument.adapter;

import com.lawwing.dateselectview.BaseCalendarItemAdapter;
import com.lawwing.dateselectview.BaseCalendarItemModel;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.model.CommentCalendarItemModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/20.\ 日历的适配器
 */

public class CalendarItemAdapter
        extends BaseCalendarItemAdapter<CommentCalendarItemModel>
{
    
    public CalendarItemAdapter(Context context)
    {
        super(context);
    }
    
    @Override
    public View getView(String date, CommentCalendarItemModel model,
            View convertView, ViewGroup parent)
    {
        ViewGroup view = (ViewGroup) LayoutInflater.from(mContext)
                .inflate(R.layout.griditem_custom_calendar_item, null);
        
        TextView dayNum = (TextView) view.findViewById(R.id.tv_day_num);
        dayNum.setText(model.getDayNumber());
        
        view.setBackgroundResource(R.drawable.bg_shape_calendar_item_normal);
        
        if (model.isToday())
        {
            dayNum.setTextColor(mContext.getResources()
                    .getColor(com.lawwing.dateselectview.R.color.red_ff725f));
            dayNum.setText(mContext.getResources()
                    .getString(com.lawwing.dateselectview.R.string.today));
        }
        
        if (model.isHoliday())
        {
            dayNum.setTextColor(mContext.getResources()
                    .getColor(com.lawwing.dateselectview.R.color.red_ff725f));
        }
        
        if (model.getStatus() == BaseCalendarItemModel.Status.DISABLE)
        {
            dayNum.setTextColor(mContext.getResources()
                    .getColor(android.R.color.darker_gray));
        }
        
        if (!model.isCurrentMonth())
        {
            dayNum.setTextColor(mContext.getResources()
                    .getColor(com.lawwing.dateselectview.R.color.gray_bbbbbb));
            view.setClickable(true);
        }
        
        TextView dayNewsCount = (TextView) view
                .findViewById(R.id.tv_day_new_count);
        if (model.getCommentCount() > 0)
        {
            dayNewsCount.setText(String.format(
                    mContext.getResources()
                            .getString(R.string.calendar_item_new_count),
                    model.getCommentCount()));
            dayNewsCount.setVisibility(View.VISIBLE);
        }
        else
        {
            dayNewsCount.setVisibility(View.GONE);
        }
        
        ImageView isFavImageView = (ImageView) view
                .findViewById(R.id.image_is_fav);
        if (model.isFav())
        {
            isFavImageView.setVisibility(View.VISIBLE);
        }
        else
        {
            isFavImageView.setVisibility(View.GONE);
        }
        
        return view;
    }
}
