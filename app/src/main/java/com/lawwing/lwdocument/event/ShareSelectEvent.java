package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.ShareModel;

/**
 * Created by lawwing on 2017/11/15.
 */
public class ShareSelectEvent
{
    private ShareModel model;
    
    public ShareSelectEvent(ShareModel model)
    {
        this.model = model;
    }
    
    public ShareModel getModel()
    {
        return model;
    }
}
