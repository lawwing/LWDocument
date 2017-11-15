package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.ShareGridAdapter;
import com.lawwing.lwdocument.model.ShareModel;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lawwing on 2017/11/15.
 */

public class ShareDialogFregment extends DialogFragment
{
    @BindView(R.id.shareRecyclerView)
    RecyclerView shareRecyclerView;
    
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    
    private String path;
    
    private Activity activity;
    
    private ArrayList<ShareModel> shareModels;
    
    private ShareGridAdapter adapter;
    
    public ShareDialogFregment(Activity activity, String path)
    {
        this.path = path;
        this.activity = activity;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_share_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.drawable.bg_menu_more_list);
        ButterKnife.bind(this, dialog); // Dialog即View
        shareModels = new ArrayList<>();
        contentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
        initRecyclerView();
        
        initShareData();
        adapter.notifyDataSetChanged();
        // 下面写各种事件
        return dialog;
    }
    
    private void initShareData()
    {
        ShareModel qqModel = new ShareModel("QQ分享",
                R.drawable.ssdk_oks_classic_qq);
        shareModels.add(qqModel);
        ShareModel wechatModel = new ShareModel("微信分享",
                R.drawable.ssdk_oks_classic_wechat);
        shareModels.add(wechatModel);
        ShareModel wechatmomentsModel = new ShareModel("朋友圈分享",
                R.drawable.ssdk_oks_classic_wechatmoments);
        shareModels.add(wechatmomentsModel);
        ShareModel qqzoneModel = new ShareModel("QQ空间分享",
                R.drawable.ssdk_oks_classic_qzone);
        shareModels.add(qqzoneModel);
        ShareModel weiboModel = new ShareModel("微博分享",
                R.drawable.ssdk_oks_classic_sinaweibo);
        shareModels.add(weiboModel);
    }
    
    private void initRecyclerView()
    {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        shareRecyclerView.setLayoutManager(manager);
        adapter = new ShareGridAdapter(shareModels, getActivity());
        shareRecyclerView.setAdapter(adapter);
    }
    
}
