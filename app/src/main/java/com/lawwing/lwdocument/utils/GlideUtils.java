package com.lawwing.lwdocument.utils;

import com.bumptech.glide.Glide;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;

import android.widget.ImageView;

/**
 * Created by lawwing on 2017/11/11.
 */

public class GlideUtils
{
    public static void loadNormalPicture(String path, ImageView view)
    {
        Glide.with(LWDApp.get().getApplicationContext())
                .load(path)
                .error(R.mipmap.loadding_file)
                .placeholder(R.mipmap.loadding_file)
                .into(view);
    }
    
}
