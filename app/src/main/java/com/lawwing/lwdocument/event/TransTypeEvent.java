package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;

/**
 * Created by lawwing on 2017/11/25.
 */

public class TransTypeEvent
{
    private CommentTypeInfoModel selectTypeBean;
    
    private CommentInfoModel selectCommentInfo;
    
    private int selectPoision;
    
    public TransTypeEvent(CommentTypeInfoModel selectTypeBean,
            CommentInfoModel selectCommentInfo, int selectPoision)
    {
        this.selectTypeBean = selectTypeBean;
        this.selectCommentInfo = selectCommentInfo;
        this.selectPoision = selectPoision;
    }
    
    public CommentTypeInfoModel getSelectTypeBean()
    {
        return selectTypeBean;
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
