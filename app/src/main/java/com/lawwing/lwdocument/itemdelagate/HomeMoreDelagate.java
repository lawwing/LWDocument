package com.lawwing.lwdocument.itemdelagate;

import java.util.List;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.lawwing.lwdocument.CheckCommentPicActivity;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.HomeBaseModel;
import com.lawwing.lwdocument.model.HomeBottomModel;
import com.lawwing.lwdocument.utils.GlideUtils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lawwing on 2017/11/11.
 */

public class HomeMoreDelagate extends AdapterDelegate<List<HomeBaseModel>>
{
    private LayoutInflater inflater;
    
    private Activity activity;
    
    public HomeMoreDelagate(Activity activity)
    {
        inflater = activity.getLayoutInflater();
        this.activity = activity;
    }
    
    @Override
    protected boolean isForViewType(@NonNull List<HomeBaseModel> items,
            int position)
    {
        return items.get(position) instanceof HomeBottomModel;
    }
    
    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent)
    {
        return new HomeBottomHolder(inflater
                .inflate(R.layout.item_home_bottom_layout, parent, false));
    }
    
    @Override
    protected void onBindViewHolder(@NonNull List<HomeBaseModel> items,
            int position, @NonNull RecyclerView.ViewHolder oldholder,
            @NonNull List<Object> payloads)
    {
        final HomeBottomModel model = (HomeBottomModel) items.get(position);
        HomeBottomHolder holder = (HomeBottomHolder) oldholder;
        if (model != null)
        {
            holder.loadMoreBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(activity, "加载更多", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    private class HomeBottomHolder extends RecyclerView.ViewHolder
    {
        TextView loadMoreBtn;
        
        public HomeBottomHolder(View view)
        {
            super(view);
            loadMoreBtn = (TextView) view.findViewById(R.id.loadMoreBtn);
        }
    }
}
