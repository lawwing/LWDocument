package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentTypeInfoModel;

/**
 * Created by lawwing on 2017/11/23.
 */
public class SaveCommentClickEvent
{
    private CommentTypeInfoModel selectBean;
    
    private String name;
    
    public SaveCommentClickEvent(CommentTypeInfoModel selectBean, String name)
    {
        this.selectBean = selectBean;
        this.name = name;
    }
    
    public CommentTypeInfoModel getSelectBean()
    {
        return selectBean;
    }
    
    public String getName()
    {
        return name;
    }
}
