package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.calendarlibrary.ScheduleRecyclerView;
import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.CommentListMoreEvent;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.utils.GlideUtils;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/23.
 */
public class DateCommentAdapter extends
        ScheduleRecyclerView.Adapter<DateCommentAdapter.DateCommentHolder>
{
    private ArrayList<CommentInfoModel> datas;
    
    private Activity activity;
    
    private LayoutInflater inflater;
    
    private String type;
    
    public DateCommentAdapter(ArrayList<CommentInfoModel> datas,
            Activity activity, String type)
    {
        this.datas = datas;
        this.activity = activity;
        this.type = type;
        inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public DateCommentHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new DateCommentHolder(
                inflater.inflate(R.layout.item_date_comment, parent, false));
    }
    
    @Override
    public void onBindViewHolder(final DateCommentHolder holder,
            final int position)
    {
        final CommentInfoModel model = datas.get(position);
        if (model != null)
        {
            GlideUtils.loadNormalPicture(model.getPath(), holder.commentImage);
            holder.fileName.setText(model.getName().split("\\.")[0]);
            holder.docName.setText("原文件：" + model.getDocname());
            holder.typeName.setText("分类：" + model.getTypeName());
            holder.createTime
                    .setText(TimeUtils.milliseconds2String(model.getTime()));
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.startActivity(CheckCommentPicActivity
                            .newInstance(activity, model.getPath()));
                }
            });
            holder.moreBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int[] location = new int[2];
                    holder.moreBtn.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    LWDApp.getEventBus().post(new CommentListMoreEvent(type,
                            model, position, x, y));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas == null ? 0 : datas.size();
    }
    
    public class DateCommentHolder extends RecyclerView.ViewHolder
    {
        ImageView commentImage;
        
        TextView fileName;
        
        TextView docName;
        
        TextView createTime;
        
        ImageView moreBtn;
        
        TextView typeName;
        
        LinearLayout bossLayout;
        
        public DateCommentHolder(View itemView)
        {
            super(itemView);
            commentImage = (ImageView) itemView.findViewById(R.id.commentImage);
            fileName = (TextView) itemView.findViewById(R.id.fileName);
            moreBtn = (ImageView) itemView.findViewById(R.id.moreBtn);
            docName = (TextView) itemView.findViewById(R.id.docName);
            typeName = (TextView) itemView.findViewById(R.id.typeName);
            createTime = (TextView) itemView.findViewById(R.id.createTime);
            bossLayout = (LinearLayout) itemView.findViewById(R.id.bossLayout);
        }
    }
}
