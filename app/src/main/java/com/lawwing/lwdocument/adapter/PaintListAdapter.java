package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.PaintLongEvent;
import com.lawwing.lwdocument.event.SelectPictureEvent;
import com.lawwing.lwdocument.model.PaintInfoModel;
import com.lawwing.lwdocument.utils.GlideUtils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lawwing on 2017/11/11.
 */

public class PaintListAdapter
        extends RecyclerView.Adapter<PaintListAdapter.PaintListHolder>
{
    // 选择图片模式
    public final static String SELECT_MODE = "0";
    
    // 查看大图模式
    public final static String CHECK_MODE = "1";
    
    private Activity activity;
    
    private ArrayList<PaintInfoModel> datas;
    
    private LayoutInflater inflater;
    
    private String mode;
    
    public PaintListAdapter(Activity activity, ArrayList<PaintInfoModel> datas,
            String mode)
    {
        this.activity = activity;
        this.datas = datas;
        this.mode = mode;
        inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public PaintListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new PaintListHolder(
                inflater.inflate(R.layout.item_paint_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(PaintListHolder holder, final int position)
    {
        final PaintInfoModel model = datas.get(position);
        if (model != null)
        {
            GlideUtils.loadNormalPicture(model.getPath(), holder.paintImage);
            holder.paintNameText
                    .setText(model.getName().replaceAll(".jpg", ""));
            holder.paintImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (CHECK_MODE.equals(mode))
                    {
                        activity.startActivity(CheckCommentPicActivity
                                .newInstance(activity, model.getPath()));
                    }
                    else if (SELECT_MODE.equals(mode))
                    {
                        LWDApp.getEventBus()
                                .post(new SelectPictureEvent(model));
                    }
                }
            });
            holder.paintImage
                    .setOnLongClickListener(new View.OnLongClickListener()
                    {
                        @Override
                        public boolean onLongClick(View view)
                        {
                            LWDApp.getEventBus()
                                    .post(new PaintLongEvent(position, model));
                            return true;
                        }
                    });
        }
        
    }
    
    @Override
    public int getItemCount()
    {
        return datas != null ? datas.size() : 0;
    }
    
    public class PaintListHolder extends RecyclerView.ViewHolder
    {
        ImageView paintImage;
        
        TextView paintNameText;
        
        public PaintListHolder(View itemView)
        {
            super(itemView);
            paintImage = (ImageView) itemView.findViewById(R.id.paintImage);
            paintNameText = (TextView) itemView
                    .findViewById(R.id.paintNameText);
        }
    }
}
