package com.lawwing.lwdocument.fragment;

import java.util.ArrayList;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.PickerColorBgAdapter;
import com.lawwing.lwdocument.model.ColorModel;

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
 * Created by lawwing on 2017/11/18.
 */

public class SelectColorDialogFragment extends DialogFragment
{
    @BindView(R.id.pickerBgRecyclerView)
    RecyclerView pickerBgRecyclerView;
    
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    
    private ArrayList<ColorModel> datas;
    
    private PickerColorBgAdapter adapter;
    
    // 一行的数目，默认是5个
    private int colNum = 5;
    
    public SelectColorDialogFragment(ArrayList<ColorModel> datas, int colNum)
    {
        this.datas = datas;
        this.colNum = colNum;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_picker_paintbg_dialog);
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
        
        initRecyclerView();
        
        contentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        
        return dialog;
    }
    
    private void initRecyclerView()
    {
        GridLayoutManager manager = new GridLayoutManager(getActivity(),
                colNum);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        pickerBgRecyclerView.setLayoutManager(manager);
        
        adapter = new PickerColorBgAdapter(datas, getActivity());
        pickerBgRecyclerView.setAdapter(adapter);
    }
}
