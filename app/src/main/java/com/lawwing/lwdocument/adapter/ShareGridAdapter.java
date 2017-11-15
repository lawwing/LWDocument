package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.ShareSelectEvent;
import com.lawwing.lwdocument.model.ShareModel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/15.
 */
public class ShareGridAdapter
        extends RecyclerView.Adapter<ShareGridAdapter.ShareGridHolder>
{
    private ArrayList<ShareModel> datas;
    
    private Activity activity;
    
    private LayoutInflater inflater;
    
    public ShareGridAdapter(ArrayList<ShareModel> datas, Activity activity)
    {
        this.datas = datas;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public ShareGridHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ShareGridHolder(
                inflater.inflate(R.layout.item_share_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(ShareGridHolder holder, int position)
    {
        final ShareModel model = datas.get(position);
        if (model != null)
        {
            holder.shareName.setText(model.getName());
            holder.shareImage.setImageResource(model.getImgUrl());
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    LWDApp.getEventBus().post(new ShareSelectEvent(model));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas.size();
    }
    
    public class ShareGridHolder extends RecyclerView.ViewHolder
    {
        ImageView shareImage;
        
        TextView shareName;
        
        LinearLayout bossLayout;
        
        public ShareGridHolder(View itemView)
        {
            super(itemView);
            shareName = (TextView) itemView.findViewById(R.id.shareName);
            shareImage = (ImageView) itemView.findViewById(R.id.shareImage);
            bossLayout = (LinearLayout) itemView.findViewById(R.id.bossLayout);
        }
    }
}
