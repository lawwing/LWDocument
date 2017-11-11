package com.lawwing.lwdocument;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lawwing.lwdocument.utils.TitleBarUtils;

public class XjbPaintGalleryActivity extends AppCompatActivity
{
    private TitleBarUtils titleBarUtils;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, XjbPaintGalleryActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjb_paint_gallery);
        titleBarUtils = new TitleBarUtils(XjbPaintGalleryActivity.this);
        titleBarUtils.initTitle("欢迎来到瞎鸡巴涂廊");
    }
}
