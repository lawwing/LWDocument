package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentInfoModel;

/**
 * Created by lawwing on 2017/11/25.
 */

public class DeleteCommentEvent
{
    private CommentInfoModel selectCommentInfo;
    
    private int selectPoision = -1;
    
    public DeleteCommentEvent(CommentInfoModel selectCommentInfo,
            int selectPoision)
    {
        this.selectCommentInfo = selectCommentInfo;
        this.selectPoision = selectPoision;
    }
    
    public CommentInfoModel getSelectCommentInfo()
    {
        return selectCommentInfo;
    }
    
    public int getSelectPoision()
    {
        return selectPoision;
    }
}
