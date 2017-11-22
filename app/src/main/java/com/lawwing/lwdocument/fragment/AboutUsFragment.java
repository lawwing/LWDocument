package com.lawwing.lwdocument.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.utils.SystemUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/22.
 */

public class AboutUsFragment extends BaseFragment implements ScreenShotable
{
    private View containerView;
    
    private Bitmap bitmap;
    
    @BindView(R.id.appNameText)
    TextView appNameText;
    
    @BindView(R.id.appVersionText)
    TextView appVersionText;
    
    @BindView(R.id.authorNameText)
    TextView authorNameText;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    public static AboutUsFragment newInstance()
    {
        AboutUsFragment contentFragment = new AboutUsFragment();
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
                .inflate(R.layout.activity_about_us, container, false);
        ButterKnife.bind(this, rootView);
        appNameText.setText(getResources().getText(R.string.app_name));
        appVersionText.setText(SystemUtils.getVersion());
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
                AboutUsFragment.this.bitmap = bitmap;
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
