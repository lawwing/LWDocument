package com.lawwing.lwdocument;

import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.utils.SystemUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class GuideActivity extends BaseActivity
{
    private TextView appNameText;
    
    private String filePath;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getIntentData();
        setContentView(R.layout.activity_guide);
        appNameText = (TextView) findViewById(R.id.appNameText);
        // 每隔100毫秒执行onTick中的方法一次
        // 直到执行完2000/1000次为止，最后会执行onFinish()中的方法
        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                
            }
            
            @Override
            public void onFinish()
            {
                
                if (TextUtils.isEmpty(filePath))
                {
                    startActivity(new Intent(GuideActivity.this,
                            AppMainActivity.class));
                }
                else
                {
                    Log.e("test", filePath);
                    Intent homeIntent = new Intent(GuideActivity.this,
                            AppMainActivity.class);
                    Intent officeViewIntent = MainActivity
                            .newInstance(GuideActivity.this, filePath);
                    
                    Intent[] intents = { homeIntent, officeViewIntent };
                    startActivities(intents);
                }
                
                finish();
            }
        };
        countDownTimer.start();
    }
    
    private void getIntentData()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            
            Uri uri = intent.getData();
            if (uri != null)
            {
                filePath = uri.getPath();
            }
        }
    }
}
