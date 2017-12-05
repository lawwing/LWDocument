package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentTypeInfoModel;

/**
 * Created by lawwing on 2017/12/5.
 */
public class RecycleCommentTransTypeEvent
{
    private CommentTypeInfoModel selectTypeBean;
    
    public RecycleCommentTransTypeEvent(CommentTypeInfoModel selectTypeBean)
    {
        this.selectTypeBean = selectTypeBean;
    }
    
    public CommentTypeInfoModel getSelectTypeBean()
    {
        return selectTypeBean;
    }
}
