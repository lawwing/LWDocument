package com.lawwing.lwdocument.fragment;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.base.BaseFragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/20.
 */

public class DateCommentListFragment extends BaseFragment
        implements ScreenShotable
{
    private Bitmap bitmap;
    
    private View containerView;
    
    public static DateCommentListFragment newInstance(int resId)
    {
        DateCommentListFragment dateCommentListFragment = new DateCommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        dateCommentListFragment.setArguments(bundle);
        return dateCommentListFragment;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater
                .inflate(R.layout.fragment_date_comment_list, container, false);
        return rootView;
    }
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
    }
    
    @Override
    public void takeScreenShot()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                DateCommentListFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
}
