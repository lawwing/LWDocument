package com.lawwing.lwdocument;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lawwing.lwdocument.utils.TitleBarUtils;

public class XjbPaintActivity extends AppCompatActivity
{
    private TitleBarUtils titleBarUtils;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, XjbPaintActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjb_paint);
        
        titleBarUtils = new TitleBarUtils(XjbPaintActivity.this);
        titleBarUtils.initTitle("瞎鸡巴画");

    }
}
