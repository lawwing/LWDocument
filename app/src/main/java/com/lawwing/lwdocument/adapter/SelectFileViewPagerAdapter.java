package com.lawwing.lwdocument.adapter;

import com.lawwing.lwdocument.fragment.ExcelListFragment;
import com.lawwing.lwdocument.fragment.PdfFragmnet;
import com.lawwing.lwdocument.fragment.PptListFragmnet;
import com.lawwing.lwdocument.fragment.WordFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by lawwing on 2017/11/11.
 */

public class SelectFileViewPagerAdapter extends FragmentPagerAdapter
{
    private String[] mTitles = new String[] { "文档", "表格", "投影", "电子书" };
    
    public SelectFileViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }
    
    @Override
    public Fragment getItem(int position)
    {
        if (position == 1)
        {
            // 表格
            return new ExcelListFragment();
        }
        else if (position == 2)
        {
            // 投影
            return new PptListFragmnet();
        }
        else if (position == 3)
        {
            // 电子书
            return new PdfFragmnet();
        }
        // 文档
        return new WordFragment();
    }
    
    @Override
    public int getCount()
    {
        return mTitles.length;
    }
    
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitles[position];
    }
    
    @Override
    public int getItemPosition(Object object)
    {
        
        /**
         * FragmentPagerAdapter 仅仅会在 destroyItem() 中 detach 这个 Fragment，在
         * instantiateItem() 时会使用旧的 Fragment，并触发 attach，因此没有释放资源及重建的过程。
         */
        
        return PagerAdapter.POSITION_NONE;
    }
}
