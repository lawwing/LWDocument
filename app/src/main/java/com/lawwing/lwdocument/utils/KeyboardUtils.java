package com.lawwing.lwdocument.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by lawwing on 2017/11/23.
 */

public class KeyboardUtils
{
    
    private KeyboardUtils()
    {
        throw new UnsupportedOperationException("u can't fuck me...");
    }
    
    /**
     * 避免输入法面板遮挡
     * <p>
     * 在manifest.xml中activity中设置
     * </p>
     * <p>
     * android:windowSoftInputMode="stateVisible|adjustResize"
     * </p>
     */
    
    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(Activity activity)
    {
        View view = activity.getWindow().peekDecorView();
        if (view != null)
        {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    /**
     * 动态隐藏软键盘
     *
     * @param context 上下文
     * @param edit 输入框
     */
    public static void hideSoftInput(Context context, EditText edit)
    {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }
    
    /**
     * 动态显示软键盘
     *
     * @param context 上下文
     * @param edit 输入框
     */
    public static void showSoftInput(Context context, EditText edit)
    {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(edit, 0);
    }
    
    /**
     * 隐藏软键盘
     *
     * @param textView
     */
    public static void hideKeyBoard(Context context, TextView textView)
    {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        
        if (imm.isActive())
        {
            imm.hideSoftInputFromWindow(textView.getApplicationWindowToken(),
                    0);
        }
    }
}