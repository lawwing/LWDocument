package com.lawwing.lwdocument.model;

import com.lawwing.dateselectview.BaseCalendarItemModel;

/**
 * Created by lawwing on 2017/11/20.
 */

public class CommentCalendarItemModel extends BaseCalendarItemModel
{
    // 日期对应的条数
    private int commentCount;
    
    // 是否喜欢
    private boolean isFav;
    
    public int getCommentCount()
    {
        return commentCount;
    }
    
    public void setCommentCount(int commentCount)
    {
        this.commentCount = commentCount;
    }
    
    public boolean isFav()
    {
        return isFav;
    }
    
    public void setFav(boolean fav)
    {
        isFav = fav;
    }
}
