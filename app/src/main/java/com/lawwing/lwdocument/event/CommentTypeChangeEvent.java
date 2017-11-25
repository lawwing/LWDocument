package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentTypeInfoModel;

/**
 * Created by lawwing on 2017/11/25.
 */

public class CommentTypeChangeEvent
{
    private String flag;
    
    private CommentTypeInfoModel typeInfoModel;
    
    private int position;
    
    public CommentTypeChangeEvent(String flag,
            CommentTypeInfoModel typeInfoModel, int position)
    {
        this.flag = flag;
        this.typeInfoModel = typeInfoModel;
        this.position = position;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public CommentTypeInfoModel getTypeInfoModel()
    {
        return typeInfoModel;
    }
    
    public int getPosition()
    {
        return position;
    }
}
