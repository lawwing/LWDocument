package com.lawwing.lwdocument.event;

/**
 * Created by lawwing on 2017/11/23.
 */

public class ChangeTitleContent
{
    private String title;
    
    public ChangeTitleContent(String title)
    {
        this.title = title;
    }
    
    public String getTitle()
    {
        return title;
    }
}
