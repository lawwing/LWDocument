package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.CommentTypeClickEvent;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/19.
 */

public class CommentTypeListAdapter extends
        RecyclerView.Adapter<CommentTypeListAdapter.CommentTypeListHolder>
{
    private Activity activity;
    
    private ArrayList<CommentTypeInfoModel> datas;
    
    private String type = "";
    
    private LayoutInflater inflater;
    
    public CommentTypeListAdapter(Activity activity,
            ArrayList<CommentTypeInfoModel> datas, String type)
    {
        this.activity = activity;
        this.datas = datas;
        this.type = type;
        this.inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public CommentTypeListHolder onCreateViewHolder(ViewGroup parent,
            int viewType)
    {
        return new CommentTypeListHolder(
                inflater.inflate(R.layout.item_commenttype_scrollmenu_layout,
                        parent,
                        false));
    }
    
    @Override
    public void onBindViewHolder(CommentTypeListHolder holder, int position)
    {
        final CommentTypeInfoModel model = datas.get(position);
        
        if (model != null)
        {
            holder.bossLayout.setSwipeEnable(true);
            holder.typeText.setText(model.getTypeName());
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if ("select".equals(type))
                    {
                        LWDApp.getEventBus()
                                .post(new CommentTypeClickEvent(type, model));
                        activity.finish();
                    }
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas == null ? 0 : datas.size();
    }
    
    public class CommentTypeListHolder extends RecyclerView.ViewHolder
    {
        SwipeHorizontalMenuLayout bossLayout;
        
        TextView typeText;
        
        public CommentTypeListHolder(View itemView)
        {
            super(itemView);
            bossLayout = (SwipeHorizontalMenuLayout) itemView
                    .findViewById(R.id.bossLayout);
            typeText = (TextView) itemView.findViewById(R.id.typeText);
        }
    }
}
