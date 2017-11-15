package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.PaintInfoModel;

/**
 * Created by lawwing on 2017/11/15.
 */
public class PaintLongEvent
{
    private PaintInfoModel model;
    
    private int position;
    
    public PaintLongEvent(int position, PaintInfoModel model)
    {
        this.position = position;
        this.model = model;
    }
    
    public PaintInfoModel getModel()
    {
        return model;
    }
    
    public int getPosition()
    {
        return position;
    }
}
