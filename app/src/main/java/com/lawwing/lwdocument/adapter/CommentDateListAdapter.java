package com.lawwing.lwdocument.adapter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import com.lawwing.dateselectview.BaseCalendarListAdapter;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.fragment.DateCommentListFragment;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.utils.GlideUtils;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        
        ContentViewHolder contentViewHolder;
        if (convertView != null)
        {
            contentViewHolder = (ContentViewHolder) convertView.getTag();
        }
        else
        {
            convertView = inflater.inflate(R.layout.listitem_calendar_content,
                    null);
            contentViewHolder = new ContentViewHolder();
            contentViewHolder.titleTextView = (TextView) convertView
                    .findViewById(R.id.title);
            contentViewHolder.timeTextView = (TextView) convertView
                    .findViewById(R.id.time);
            contentViewHolder.newsImageView = (ImageView) convertView
                    .findViewById(R.id.image);
            convertView.setTag(contentViewHolder);
        }
        
        contentViewHolder.titleTextView.setText(model.getDocname());
        contentViewHolder.timeTextView
                .setText(TimeUtils.milliseconds2String(model.getTime()) + "");
        // GenericDraweeHierarchy hierarchy =
        // GenericDraweeHierarchyBuilder.newInstance(convertView.getResources())
        // .setRoundingParams(RoundingParams.asCircle())
        // .build();
        // contentViewHolder.newsImageView.setHierarchy(hierarchy);
        // contentViewHolder.newsImageView.setImageURI(Uri.parse(model.getImages().get(0)));
        GlideUtils.loadNormalPicture(model.getPath(),
                contentViewHolder.newsImageView);
        return convertView;
    }
    
    @Override
    public View getSectionHeaderView(String date, View convertView,
            ViewGroup parent)
    {
        HeaderViewHolder headerViewHolder;
        List<CommentInfoModel> modelList = dateDataMap.get(date);
        if (convertView != null)
        {
            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }
        else
        {
            convertView = inflater.inflate(R.layout.listitem_calendar_header,
                    null);
            headerViewHolder = new HeaderViewHolder();
            headerViewHolder.dayText = (TextView) convertView
                    .findViewById(R.id.header_day);
            headerViewHolder.yearMonthText = (TextView) convertView
                    .findViewById(R.id.header_year_month);
            headerViewHolder.isFavImage = (ImageView) convertView
                    .findViewById(R.id.header_btn_fav);
            convertView.setTag(headerViewHolder);
        }
        
        Calendar calendar = getCalendarByYearMonthDay(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day < 10)
        {
            dayStr = "0" + dayStr;
        }
        headerViewHolder.dayText.setText(dayStr);
        headerViewHolder.yearMonthText
                .setText(DateCommentListFragment.YEAR_MONTH_FORMAT
                        .format(calendar.getTime()));
        return convertView;
    }
    
    public Calendar getCalendarByYearMonthDay(String yearMonthDay)
    {
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTimeInMillis(
                    DateCommentListFragment.DAY_FORMAT.parse(yearMonthDay)
                            .getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return calendar;
    }
    
    private static class HeaderViewHolder
    {
        TextView dayText;
        
        TextView yearMonthText;
        
        ImageView isFavImage;
    }
    
    private static class ContentViewHolder
    {
        TextView titleTextView;
        
        TextView timeTextView;
        
        ImageView newsImageView;
    }
    
}
