package com.lawwing.lwdocument.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lawwing on 2017/11/18.
 */

public class PaintDateListAdapter
        extends RecyclerView.Adapter<PaintDateListAdapter.PaintDateListHolder>
{
    @Override
    public PaintDateListHolder onCreateViewHolder(ViewGroup parent,
            int viewType)
    {
        return null;
    }
    
    @Override
    public void onBindViewHolder(PaintDateListHolder holder, int position)
    {
        
    }
    
    @Override
    public int getItemCount()
    {
        return 0;
    }
    
    public class PaintDateListHolder extends RecyclerView.ViewHolder
    {
        public PaintDateListHolder(View itemView)
        {
            super(itemView);
        }
    }
}
