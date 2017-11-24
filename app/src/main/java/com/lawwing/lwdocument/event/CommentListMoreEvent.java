package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentInfoModel;

/**
 * Created by lawwing on 2017/11/24.
 */
public class CommentListMoreEvent
{
    private String flag;
    
    private CommentInfoModel model;
    
    private int poision;
    
    public CommentListMoreEvent(String flag, CommentInfoModel model,
            int poision)
    {
        this.flag = flag;
        this.model = model;
        this.poision = poision;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public CommentInfoModel getModel()
    {
        return model;
    }
    
    public int getPoision()
    {
        return poision;
    }
}
