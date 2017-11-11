package com.lawwing.lwdocument.itemdelagate;

import java.util.List;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.HomeBaseModel;
import com.lawwing.lwdocument.utils.GlideUtils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by lawwing on 2017/11/11.
 */

public class HomeItemDelagate extends AdapterDelegate<List<HomeBaseModel>>
{
    private LayoutInflater inflater;
    
    private Activity activity;
    
    public HomeItemDelagate(Activity activity)
    {
        inflater = activity.getLayoutInflater();
        this.activity = activity;
    }
    
    @Override
    protected boolean isForViewType(@NonNull List<HomeBaseModel> items,
            int position)
    {
        return items.get(position) instanceof CommentInfoModel;
    }
    
    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent)
    {
        return new HomeHolder(
                inflater.inflate(R.layout.item_home_layout, parent, false));
    }
    
    @Override
    protected void onBindViewHolder(@NonNull List<HomeBaseModel> items,
            int position, @NonNull RecyclerView.ViewHolder oldholder,
            @NonNull List<Object> payloads)
    {
        final CommentInfoModel model = (CommentInfoModel) items.get(position);
        HomeHolder holder = (HomeHolder) oldholder;
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
