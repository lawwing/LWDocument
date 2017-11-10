package com.lawwing.lwdocument;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.utils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckCommentPicActivity extends BaseActivity {

    @BindView(R.id.commentBigImage)
    ImageView commentBigImage;

    private String path = "";

    public static Intent newInstance(Activity activity, String path) {
        Intent intent = new Intent(activity, CheckCommentPicActivity.class);
        intent.putExtra("path", path);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        setContentView(R.layout.activity_check_comment_pic);
        ButterKnife.bind(this);
        GlideUtils.loadNormalPicture(path, commentBigImage);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("path")) {
                path = intent.getStringExtra("path");
            }
        }
    }
}
