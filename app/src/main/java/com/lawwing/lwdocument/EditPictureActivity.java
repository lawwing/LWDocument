package com.lawwing.lwdocument;

import com.lawwing.lwdocument.adapter.PaintListAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.gen.PaintInfoDb;
import com.lawwing.lwdocument.gen.PaintInfoDbDao;
import com.lawwing.lwdocument.model.PaintInfoModel;
import com.lawwing.lwdocument.utils.TitleBarUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPictureActivity extends BaseActivity
{
    

    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, EditPictureActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_picture);
    }

}
