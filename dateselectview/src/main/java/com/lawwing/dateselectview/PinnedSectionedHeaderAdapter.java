package com.lawwing.dateselectview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lawwing on 2017/10/30.
 */
public interface PinnedSectionedHeaderAdapter
{
    
    boolean isSectionHeader(int position);
    
    int getSectionForPosition(int position);
    
    View getSectionHeaderView(int section, View convertView, ViewGroup parent);
    
    int getSectionHeaderViewType(int section);
    
    int getCount();
}
