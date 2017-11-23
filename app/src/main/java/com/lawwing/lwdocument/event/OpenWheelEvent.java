package com.lawwing.lwdocument.event;

/**
 * Created by lawwing on 2017/11/23.
 */

public class OpenWheelEvent
{
    private String type;
    
    public OpenWheelEvent(String type)
    {
        this.type = type;
    }
    
    public String getType()
    {
        return type;
    }
}
