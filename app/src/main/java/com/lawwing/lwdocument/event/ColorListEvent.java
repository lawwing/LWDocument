package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.ColorModel;

/**
 * Created by lawwing on 2017/11/18.
 */

public class ColorListEvent
{
    private String type;
    
    private int poision;
    
    private ColorModel model;
    
    public ColorListEvent(String type, int poision, ColorModel model)
    {
        this.type = type;
        this.poision = poision;
        this.model = model;
    }
    
    public String getType()
    {
        return type;
    }
    
    public int getPoision()
    {
        return poision;
    }
    
    public ColorModel getModel()
    {
        return model;
    }
}
