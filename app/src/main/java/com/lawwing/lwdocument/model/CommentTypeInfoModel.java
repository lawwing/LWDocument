package com.lawwing.lwdocument.model;

/**
 * Created by lawwing on 2017/11/19.
 */

public class CommentTypeInfoModel
{
    private Long id;
    
    private String typeName;
    
    private long createTime;
    
    // 是否可编辑
    private boolean isEdit = true;
    
    private boolean isShow = true;
    
    public boolean isShow()
    {
        return isShow;
    }
    
    public void setShow(boolean show)
    {
        isShow = show;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isEdit()
    {
        return isEdit;
    }
    
    public void setEdit(boolean edit)
    {
        isEdit = edit;
    }
    
    @Override
    public String toString()
    {
        return typeName;
    }
}
