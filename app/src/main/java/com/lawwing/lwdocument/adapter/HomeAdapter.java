package com.lawwing.lwdocument.adapter;

import java.util.List;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.lawwing.lwdocument.itemdelagate.HomeItemDelagate;
import com.lawwing.lwdocument.itemdelagate.HomeMoreDelagate;
import com.lawwing.lwdocument.model.HomeBaseModel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by lawwing on 2017/11/11.
 */

public class HomeAdapter extends RecyclerView.Adapter
{
    
    private AdapterDelegatesManager<List<HomeBaseModel>> delegatesManager;
    
    private List<HomeBaseModel> items;
    
    private String flag;
    
    public HomeAdapter(Activity activity, List<HomeBaseModel> items,
            String flag)
    {
        this.items = items;
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new HomeItemDelagate(activity, flag));
        delegatesManager.addDelegate(new HomeMoreDelagate(activity));
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType)
    {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }
    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        delegatesManager.onBindViewHolder(items, position, holder);
    }
    
    @Override
    public int getItemCount()
    {
        return (items.size()) > 0 ? items.size() : 0;
    }
    
    @Override
    public int getItemViewType(int position)
    {
        return delegatesManager.getItemViewType(items, position);
    }
    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position,
            List payloads)
    {
        delegatesManager.onBindViewHolder(items, position, holder, payloads);
    }
    
}
