package com.lawwing.lwdocument.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by lawwing on 2017/11/11.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    
    public void showLongToast(String content)
    {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
    
    public void showShortToast(String content)
    {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
    
}
