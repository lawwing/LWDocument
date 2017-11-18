package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.XjbPaintActivity;
import com.lawwing.lwdocument.event.PickerColorBgEvent;
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

public class PickerColorBgAdapter
        extends RecyclerView.Adapter<PickerColorBgAdapter.PickerColorBgHolder>
{
    private ArrayList<ColorModel> datas;
    
    private Activity activity;
    
    private LayoutInflater inflater;
    
    public PickerColorBgAdapter(ArrayList<ColorModel> datas, Activity activity)
    {
        this.datas = datas;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public PickerColorBgHolder onCreateViewHolder(ViewGroup parent,
            int viewType)
    {
        return new PickerColorBgHolder(inflater
                .inflate(R.layout.item_oicker_colorbg_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(PickerColorBgHolder holder, int position)
    {
        final ColorModel model = datas.get(position);
        if (model != null)
        {
            holder.colorImage
                    .setColorFilter(Color.parseColor(model.getColor()));
            holder.colorImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    LWDApp.getEventBus().post(new PickerColorBgEvent(model));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas == null ? 0 : datas.size();
    }
    
    public class PickerColorBgHolder extends RecyclerView.ViewHolder
    {
        ImageView colorImage;
        
        public PickerColorBgHolder(View itemView)
        {
            super(itemView);
            colorImage = (ImageView) itemView.findViewById(R.id.colorImage);
        }
    }
}
