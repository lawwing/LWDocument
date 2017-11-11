package com.lawwing.lwdocument;

import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.utils.SystemUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity
{
    @BindView(R.id.appNameText)
    TextView appNameText;
    
    @BindView(R.id.appVersionText)
    TextView appVersionText;
    
    @BindView(R.id.authorNameText)
    TextView authorNameText;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, AboutUsActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        appNameText.setText(getResources().getText(R.string.app_name));
        appVersionText.setText(SystemUtils.getVersion());
    }
}
