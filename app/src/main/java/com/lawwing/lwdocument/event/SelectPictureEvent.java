package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.PaintInfoModel;

/**
 * Created by lawwing on 2017/11/13.
 */
public class SelectPictureEvent
{
    private PaintInfoModel model;
    
    public SelectPictureEvent(PaintInfoModel model)
    {
        this.model = model;
    }
    
    public PaintInfoModel getModel()
    {
        return model;
    }
}
