package com.lawwing.lwdocument.fragment;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.widget.ColorPickerView;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lawwing on 2017/11/18.
 */

public class PickerColorDialogFragment extends DialogFragment
{
    @BindView(R.id.colorPickerView)
    ColorPickerView colorPickerView;
    
    @BindView(R.id.selectColorView)
    ImageView selectColorView;
    
    @BindView(R.id.enterColor)
    ImageView enterColor;
    
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_picker_color_dialog);
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
        
        colorPickerView.setOnColorChangedListenner(
                new ColorPickerView.OnColorChangedListener()
                {
                    /**
                     * 手指抬起，选定颜色时
                     */
                    @Override
                    public void onColorChanged(int r, int g, int b)
                    {
                        if (r == 0 && g == 0 && b == 0)
                        {
                            selectColorView.setColorFilter(Color.rgb(r, g, b));
                            return;
                        }
                        selectColorView.setColorFilter(Color.rgb(r, g, b));
                    }
                    
                    /**
                     * 颜色移动的时候
                     */
                    @Override
                    public void onMoveColor(int r, int g, int b)
                    {
                        if (r == 0 && g == 0 && b == 0)
                        {
                            return;
                        }
                        selectColorView.setColorFilter(Color.rgb(r, g, b));
                    }
                });
        
        enterColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 点击确定添加颜色到数据库
            }
        });
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
}
