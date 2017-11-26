package com.lawwing.lwdocument;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lawwing.lwdocument.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentRecycleActivity extends BaseActivity
{
    
    @BindView(R.id.back_btn)
    ImageView backBtn;
    
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, CommentRecycleActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_recycle);
        ButterKnife.bind(this);
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
