package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.ColorListEvent;
import com.lawwing.lwdocument.model.ColorModel;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by lawwing on 2017/11/18.
 */

public class ColorListAdapter
        extends RecyclerView.Adapter<ColorListAdapter.ColorListHolder>
{
    private Activity activity;
    
    private ArrayList<ColorModel> datas;
    
    private LayoutInflater inflater;
    
    public ColorListAdapter(Activity activity, ArrayList<ColorModel> datas)
    {
        this.activity = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public ColorListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ColorListHolder(
                inflater.inflate(R.layout.item_color_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(ColorListHolder holder, final int position)
    {
        if (datas.size() > position)
        {
            final ColorModel model = datas.get(position);
            holder.colorImage.setVisibility(View.VISIBLE);
            holder.addImage.setVisibility(View.GONE);
            holder.colorImage
                    .setColorFilter(Color.parseColor(model.getColor()));
            holder.colorImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    LWDApp.getEventBus().post(
                            new ColorListEvent("select", position, model));
                }
            });
            if (model.isSelect())
            {
                holder.ringImage.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.ringImage.setVisibility(View.GONE);
            }
        }
        else
        {
            holder.colorImage.setVisibility(View.GONE);
            holder.ringImage.setVisibility(View.GONE);
            holder.addImage.setVisibility(View.VISIBLE);
            holder.addImage.setImageResource(R.mipmap.add_color);
            holder.addImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // 点击添加更多颜色
                    LWDApp.getEventBus()
                            .post(new ColorListEvent("add", position, null));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas == null ? 1 : datas.size() + 1;
    }
    
    public class ColorListHolder extends RecyclerView.ViewHolder
    {
        ImageView colorImage;
        
        ImageView ringImage;
        
        ImageView addImage;
        
        public ColorListHolder(View itemView)
        {
            super(itemView);
            colorImage = (ImageView) itemView.findViewById(R.id.colorImage);
            addImage = (ImageView) itemView.findViewById(R.id.addImage);
            ringImage = (ImageView) itemView.findViewById(R.id.ringImage);
        }
    }
}
