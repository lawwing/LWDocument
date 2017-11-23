package com.lawwing.lwdocument.fragment;

import java.util.List;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.SelectFileViewPagerAdapter;
import com.lawwing.lwdocument.base.BaseFragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lawwing on 2017/11/22.
 */

public class SelectFileFragment extends BaseFragment implements ScreenShotable
{
    private View containerView;
    
    private Bitmap bitmap;
    
    TabLayout mTablayout;
    
    ViewPager mViewpager;
    
    private String[] mTitles = new String[] { "WORD", "EXCEL", "PPT", "PDF" };
    
    private int[] tabIcons = { R.mipmap.file_word, R.mipmap.file_excel,
            R.mipmap.file_ppt, R.mipmap.file_pdf };
    
    private SelectFileViewPagerAdapter pageradapter;
    
    public static SelectFileFragment newInstance()
    {
        SelectFileFragment contentFragment = new SelectFileFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_show_document_list,
                container,
                false);
        mTablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mViewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        
        setViewPager(0);
        
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
                SelectFileFragment.this.bitmap = bitmap;
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
    
    private void setViewPager(int i)
    {
        pageradapter = new SelectFileViewPagerAdapter(
                getActivity().getSupportFragmentManager());
        mViewpager.setOffscreenPageLimit(pageradapter.getCount());
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels)
            {
                
            }
            
            @Override
            public void onPageSelected(int position)
            {
                
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
        mViewpager.setAdapter(pageradapter);
        mTablayout.setupWithViewPager(mViewpager);
        
        setupTabIcons();
        mViewpager.setCurrentItem(i);
    }
    
    /**
     * 设置带图片的tab
     *
     * @return
     */
    private void setupTabIcons()
    {
        mTablayout.getTabAt(0).setCustomView(getTabView(0));
        mTablayout.getTabAt(1).setCustomView(getTabView(1));
        mTablayout.getTabAt(2).setCustomView(getTabView(2));
        mTablayout.getTabAt(3).setCustomView(getTabView(3));
    }
    
    /**
     * 获取带图片的tab
     *
     * @param position
     * @return
     */
    public View getTabView(int position)
    {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tab_title);
        txt_title.setTextColor(Color.parseColor("#333333"));
        txt_title.setText(mTitles[position]);
        txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        ImageView img_title = (ImageView) view.findViewById(R.id.tab_img);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }
    
}
