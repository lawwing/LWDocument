package com.lawwing.lwdocument.model;

/**
 * Created by lawwing on 2017/11/18.
 */

public class ColorModel
{
    private Long id;
    
    private String color;
    
    private long createTime;
    
    private boolean isSelect = false;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public void setColor(String color)
    {
        this.color = color;
    }
    
    public long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isSelect()
    {
        return isSelect;
    }
    
    public void setSelect(boolean select)
    {
        isSelect = select;
    }
}
