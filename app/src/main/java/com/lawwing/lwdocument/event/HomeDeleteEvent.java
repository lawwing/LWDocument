package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentInfoModel;

/**
 * Created by lawwing on 2017/11/11.
 */

public class HomeDeleteEvent
{
    private String flag;
    
    private CommentInfoModel model;
    
    private int position;
    
    public HomeDeleteEvent(String flag, CommentInfoModel model, int position)
    {
        this.flag = flag;
        this.model = model;
        this.position = position;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public CommentInfoModel getModel()
    {
        return model;
    }
    
    public int getPosition()
    {
        return position;
    }
}
