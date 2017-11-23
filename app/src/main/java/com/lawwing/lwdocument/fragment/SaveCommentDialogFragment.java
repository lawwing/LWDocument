package com.lawwing.lwdocument.fragment;

import com.lawwing.lwdocument.R;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lawwing on 2017/11/23.
 */

public class SaveCommentDialogFragment extends DialogFragment
{
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    
    @BindView(R.id.nameEdittext)
    EditText nameEdittext;
    
    @BindView(R.id.addTypeBtn)
    ImageView addTypeBtn;
    
    @BindView(R.id.saveBtn)
    TextView saveBtn;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_save_comment_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.drawable.bg_menu_more_list);
        ButterKnife.bind(this, dialog); // Dialog即View
        
        contentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
            }
        });
        addTypeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
            }
        });
        return dialog;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater,
                container,
                savedInstanceState);
        return rootView;
    }
    
}
