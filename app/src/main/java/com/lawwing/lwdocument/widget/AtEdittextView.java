package com.lawwing.lwdocument.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by lawwing on 2017/12/2.
 */

public class AtEdittextView extends AppCompatEditText
{
    public AtEdittextView(Context context)
    {
        super(context);
    }
    
    public AtEdittextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public AtEdittextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore,
            int lengthAfter)
    {
        /**
         * 删除多少个字就是lengthBefore，添加文字的时候这个参数为0
         * 
         * 添加多少个字就是lengthAfter，删除文字的时候这个参数为0
         * 
         * lengthBefore和lengthAfter有值的时候，表明是替换，lengthBefore是删除的字数，lengthAfter是添加的字数
         */
        Log.e("test",
                "text:" + text + "   start:" + start + "  lengthBefore:"
                        + lengthBefore + "  lengthAfter:" + lengthAfter);
        // 光标位置
        int poi = start;
        if (text.length() > 0)
        {
            if (lengthAfter > lengthBefore)
            {
                if (text.charAt(poi) == '@')
                {
                    getText().insert(poi + 1, "666 ");
                }
            }
            else
            {
                // 删除的时候
                getText().delete(0, 3);
            }
        }
        
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
    
}
