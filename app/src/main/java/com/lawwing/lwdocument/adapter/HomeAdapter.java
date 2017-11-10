package com.lawwing.lwdocument.adapter;

import java.util.ArrayList;

import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.utils.GlideUtils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by lawwing on 2017/11/11.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder>
{
    private ArrayList<CommentInfoModel> datas;
    
    private Activity activity;
    
    private LayoutInflater inflater;
    
    public HomeAdapter(Activity activity, ArrayList<CommentInfoModel> datas)
    {
        this.datas = datas;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new HomeHolder(
                inflater.inflate(R.layout.item_home_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(HomeHolder holder, int position)
    {
        final CommentInfoModel model = datas.get(position);
        if (model != null)
        {
            GlideUtils.loadNormalPicture(model.getPath(), holder.commentImage);
            holder.commentImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.startActivity(CheckCommentPicActivity
                            .newInstance(activity, model.getPath()));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas != null ? datas.size() : 0;
    }
    
    public class HomeHolder extends RecyclerView.ViewHolder
    {
        ImageView commentImage;
        
        public HomeHolder(View itemView)
        {
            super(itemView);
            commentImage = (ImageView) itemView.findViewById(R.id.commentImage);
        }
    }
}
