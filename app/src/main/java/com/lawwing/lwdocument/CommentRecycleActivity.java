package com.lawwing.lwdocument;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentRecycleActivity extends BaseActivity
{
    
    @BindView(R.id.back_btn)
    ImageView backBtn;
    
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    
    @BindView(R.id.managerBtn)
    TextView managerBtn;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
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
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        List<CommentInfoDb> dbs = mCommentInfoDbDao.queryBuilder()
                .where(CommentInfoDbDao.Properties.TypeId.eq(-1))
                .list();
    }
    
    @OnClick({ R.id.managerBtn })
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.managerBtn:
                if (bottomLayout.getVisibility() == View.VISIBLE)
                {
                    bottomLayout.setVisibility(View.GONE);
                }
                else
                {
                    bottomLayout.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
