package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.AboutUsActivity;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.AddTypeEvent;
import com.lawwing.lwdocument.event.CommentTypeChangeEvent;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.lawwing.lwdocument.utils.TimeUtils;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/24.
 */
public class CommentTypeManagerAdapter extends
        RecyclerView.Adapter<CommentTypeManagerAdapter.CommentTypeManagerHolder>
{
    private ArrayList<CommentTypeInfoModel> datas;
    
    private Activity activity;
    
    private LayoutInflater inflater;
    
    public CommentTypeManagerAdapter(ArrayList<CommentTypeInfoModel> datas,
            Activity activity)
    {
        this.datas = datas;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public CommentTypeManagerHolder onCreateViewHolder(ViewGroup parent,
            int viewType)
    {
        return new CommentTypeManagerHolder(
                inflater.inflate(R.layout.item_commenttype_manager_layout,
                        parent,
                        false));
    }
    
    @Override
    public void onBindViewHolder(CommentTypeManagerHolder holder,
            final int position)
    {
        if (datas != null && position < datas.size())
        {
            holder.addLayout.setVisibility(View.GONE);
            holder.contentLayout.setVisibility(View.VISIBLE);
            final CommentTypeInfoModel model = datas.get(position);
            if (model != null)
            {
                holder.typeNameText.setText(
                        model.getTypeName() + "（" + model.getCount() + "）");
                holder.timeText.setText(
                        TimeUtils.milliseconds2String(model.getCreateTime()));
                holder.editTypeBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        LWDApp.getEventBus().post(new CommentTypeChangeEvent(
                                "edit", model, position));
                    }
                });
                holder.deleteTypeBtn
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                LWDApp.getEventBus().post(
                                        new CommentTypeChangeEvent("delete",
                                                model, position));
                            }
                        });
            }
        }
        else
        {
            holder.addLayout.setVisibility(View.VISIBLE);
            holder.contentLayout.setVisibility(View.GONE);
            holder.addLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    LWDApp.getEventBus().post(new AddTypeEvent());
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        int count = datas != null ? datas.size() + 1 : 1;
        if (datas.size() >= 10)
        {
            count = 10;
        }
        return count;
    }
    
    public class CommentTypeManagerHolder extends RecyclerView.ViewHolder
    {
        CardView contentLayout;
        
        LinearLayout addLayout;
        
        TextView typeNameText;
        
        TextView timeText;
        
        ImageView deleteTypeBtn;
        
        ImageView editTypeBtn;
        
        public CommentTypeManagerHolder(View itemView)
        {
            super(itemView);
            contentLayout = (CardView) itemView
                    .findViewById(R.id.contentLayout);
            addLayout = (LinearLayout) itemView.findViewById(R.id.addLayout);
            typeNameText = (TextView) itemView.findViewById(R.id.typeNameText);
            timeText = (TextView) itemView.findViewById(R.id.timeText);
            deleteTypeBtn = (ImageView) itemView
                    .findViewById(R.id.deleteTypeBtn);
            editTypeBtn = (ImageView) itemView.findViewById(R.id.editTypeBtn);
        }
    }
}
