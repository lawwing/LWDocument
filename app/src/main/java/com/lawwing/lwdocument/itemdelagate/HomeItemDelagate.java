package com.lawwing.lwdocument.itemdelagate;

import java.util.List;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.MainActivity;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.event.HomeDeleteEvent;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.HomeBaseModel;
import com.lawwing.lwdocument.utils.GlideUtils;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/11.
 */

public class HomeItemDelagate extends AdapterDelegate<List<HomeBaseModel>>
{
    private LayoutInflater inflater;
    
    private Activity activity;
    
    private String flag;
    
    public HomeItemDelagate(Activity activity, String flag)
    {
        inflater = activity.getLayoutInflater();
        this.activity = activity;
        this.flag = flag;
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
        return new HomeHolder(inflater
                .inflate(R.layout.item_home_scrollmenu_layout, parent, false));
    }
    
    @Override
    protected void onBindViewHolder(@NonNull List<HomeBaseModel> items,
            final int position, @NonNull RecyclerView.ViewHolder oldholder,
            @NonNull List<Object> payloads)
    {
        final CommentInfoModel model = (CommentInfoModel) items.get(position);
        final HomeHolder holder = (HomeHolder) oldholder;
        if (model != null)
        {
            holder.docName.setText(model.getDocname());
            holder.bossLayout.setSwipeEnable(true);
            GlideUtils.loadNormalPicture(model.getPath(), holder.commentImage);
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.startActivity(CheckCommentPicActivity
                            .newInstance(activity, model.getPath()));
                }
            });
            
            holder.checkDocumentBtn
                    .setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            holder.bossLayout.smoothCloseMenu();
                            activity.startActivity(MainActivity
                                    .newInstance(activity, model.getDocpath()));
                        }
                    });
            
            holder.deleteBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    holder.bossLayout.smoothCloseMenu();
                    LWDApp.getEventBus()
                            .post(new HomeDeleteEvent(flag, model, position));
                }
            });
        }
    }
    
    public class HomeHolder extends RecyclerView.ViewHolder
    {
        ImageView commentImage;
        
        ImageView checkDocumentBtn;
        
        ImageView deleteBtn;
        
        TextView docName;
        
        SwipeHorizontalMenuLayout bossLayout;
        
        public HomeHolder(View itemView)
        {
            super(itemView);
            commentImage = (ImageView) itemView.findViewById(R.id.commentImage);
            checkDocumentBtn = (ImageView) itemView
                    .findViewById(R.id.checkDocumentBtn);
            deleteBtn = (ImageView) itemView.findViewById(R.id.deleteBtn);
            bossLayout = (SwipeHorizontalMenuLayout) itemView
                    .findViewById(R.id.bossLayout);
            docName = (TextView) itemView.findViewById(R.id.docName);
        }
    }
    
}
