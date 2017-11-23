package com.lawwing.lwdocument.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.base.BaseFragment;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/23.
 */

public class TypeManagerFragment extends BaseFragment implements ScreenShotable
{
    private View containerView;
    
    private Bitmap bitmap;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    public static TypeManagerFragment newInstance()
    {
        TypeManagerFragment contentFragment = new TypeManagerFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater
                .inflate(R.layout.fragment_type_manager, container, false);
        return rootView;
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
                TypeManagerFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
    }
}
