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
    
    private int x;
    
    private int y;
    
    public CommentListMoreEvent(String flag, CommentInfoModel model,
            int poision, int x, int y)
    {
        this.flag = flag;
        this.model = model;
        this.poision = poision;
        this.x = y;
        this.y = y;
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
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
}
