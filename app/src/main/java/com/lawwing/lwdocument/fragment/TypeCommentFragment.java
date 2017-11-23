package com.lawwing.lwdocument.fragment;

import com.itheima.wheelpicker.WheelPicker;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/23.
 */

public class TypeCommentFragment extends BaseFragment implements ScreenShotable {
    private View containerView;

    private Bitmap bitmap;

    private WheelPicker wheelPicker;

    private List<String> datas;

    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    public static TypeCommentFragment newInstance() {
        TypeCommentFragment contentFragment = new TypeCommentFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_type_comment_list, container, false);

        wheelPicker = (WheelPicker) rootView.findViewById(R.id.wheelPicker);
        mCommentTypeInfoDbDao = LWDApp.get().getDaoSession().getCommentTypeInfoDbDao();
        initDbDatas();
        wheelPicker.setData(datas);
        wheelPicker.setSelectedItemPosition(0);

        return rootView;
    }

    private void initDbDatas() {
        datas = new ArrayList<>();
        datas.add("全部");
        List<CommentTypeInfoDb> dbs = mCommentTypeInfoDbDao.loadAll();
        for (CommentTypeInfoDb db : dbs) {
            datas.add(db.getTypeName());
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    public void loadSubData() {

    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                TypeCommentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
