package com.lawwing.lwdocument;

import java.util.List;

import com.lawwing.lwdocument.adapter.SelectFileViewPagerAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.utils.TitleBarUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class ShowDocumentListActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks
{
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    
    private String[] mTitles = new String[] { "WORD", "EXCEL", "PPT", "PDF" };
    
    private int[] tabIcons = { R.mipmap.file_word, R.mipmap.file_excel,
            R.mipmap.file_ppt, R.mipmap.file_pdf };
    
    private SelectFileViewPagerAdapter pageradapter;
    
    private TitleBarUtils titleBarUtils;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, ShowDocumentListActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_document_list);
        ButterKnife.bind(this);
        titleBarUtils = new TitleBarUtils(ShowDocumentListActivity.this);
        titleBarUtils.initTitle("文档列表");
        
        String[] perms = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (!EasyPermissions.hasPermissions(ShowDocumentListActivity.this,
                perms))
        {
            EasyPermissions.requestPermissions(ShowDocumentListActivity.this,
                    "需要访问手机存储权限！",
                    10086,
                    perms);
        }
        else
        {
            setViewPager(0);
        }
    }
    
    private void setViewPager(int i)
    {
        pageradapter = new SelectFileViewPagerAdapter(
                getSupportFragmentManager());
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
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tab_title);
        txt_title.setTextColor(Color.parseColor("#333333"));
        txt_title.setText(mTitles[position]);
        txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        ImageView img_title = (ImageView) view.findViewById(R.id.tab_img);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }
    
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        // 成功
        if (requestCode == 10086)
        {
            if (perms.size() == 2)
            {
                setViewPager(0);
            }
        }
    }
    
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        
        // 失败
        if (requestCode == 10086)
        {
            showLongToast("请务必打开全部权限才能扫描文档");
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode,
            String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults,
                this);
    }
}
