package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.utils.GlideUtils;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/12/5.
 */
public class CommentRecycleAdapter
        extends RecyclerView.Adapter<CommentRecycleAdapter.CommentRecycleHolder>
{
    private Activity activity;
    
    private ArrayList<CommentInfoModel> datas;
    
    private boolean isSelectMode = false;
    
    private LayoutInflater inflater;
    
    public CommentRecycleAdapter(Activity activity,
            ArrayList<CommentInfoModel> datas, boolean isSelectMode)
    {
        this.activity = activity;
        this.datas = datas;
        this.isSelectMode = isSelectMode;
        inflater = LayoutInflater.from(activity);
    }
    
    public void setSelectMode(boolean selectMode)
    {
        isSelectMode = selectMode;
    }
    
    @Override
    public CommentRecycleHolder onCreateViewHolder(ViewGroup parent,
            int viewType)
    {
        return new CommentRecycleHolder(inflater
                .inflate(R.layout.item_comment_recycle_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(CommentRecycleHolder holder, int position)
    {
        final CommentInfoModel model = datas.get(position);
        if (model != null)
        {
            if (isSelectMode)
            {
                holder.checkImage.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.checkImage.setVisibility(View.GONE);
            }
            if (model.isSelect())
            {
                holder.checkImage.setImageResource(R.mipmap.check_box_press);
            }
            else
            {
                holder.checkImage.setImageResource(R.mipmap.check_box_normal);
            }
            GlideUtils.loadNormalPicture(model.getPath(), holder.commentImage);
            holder.fileName.setText(model.getName().split("\\.")[0]);
            holder.docName.setText("原文件：" + model.getDocname());
            holder.createTime
                    .setText(TimeUtils.milliseconds2String(model.getTime()));
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (isSelectMode)
                    {
                        checkClickEvent(model);
                    }
                    else
                    {
                        activity.startActivity(CheckCommentPicActivity
                                .newInstance(activity, model.getPath()));
                    }
                }
            });
            holder.checkImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    checkClickEvent(model);
                }
            });
        }
    }
    
    private void checkClickEvent(CommentInfoModel model)
    {
        if (model.isSelect())
        {
            model.setSelect(false);
        }
        else
        {
            model.setSelect(true);
        }
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemCount()
    {
        return datas != null ? datas.size() : 0;
    }
    
    public class CommentRecycleHolder extends RecyclerView.ViewHolder
    {
        ImageView commentImage;
        
        ImageView checkImage;
        
        TextView fileName;
        
        TextView docName;
        
        TextView createTime;
        
        LinearLayout bossLayout;
        
        public CommentRecycleHolder(View itemView)
        {
            super(itemView);
            commentImage = (ImageView) itemView.findViewById(R.id.commentImage);
            checkImage = (ImageView) itemView.findViewById(R.id.checkImage);
            fileName = (TextView) itemView.findViewById(R.id.fileName);
            docName = (TextView) itemView.findViewById(R.id.docName);
            createTime = (TextView) itemView.findViewById(R.id.createTime);
            bossLayout = (LinearLayout) itemView.findViewById(R.id.bossLayout);
        }
    }
}
