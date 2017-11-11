package com.lawwing.lwdocument.event;

/**
 * Created by lawwing on 2017/11/11.
 */

public class SaveCommentEvent
{
    private String flag;
    
    public SaveCommentEvent(String flag)
    {
        this.flag = flag;
    }
    
    public String getFlag()
    {
        return flag;
    }
}
