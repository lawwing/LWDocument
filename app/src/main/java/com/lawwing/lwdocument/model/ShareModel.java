package com.lawwing.lwdocument.model;

/**
 * Created by lawwing on 2017/11/15.
 */
public class ShareModel
{
    private String name;
    
    private int imgUrl;
    
    public ShareModel(String name, int imgUrl)
    {
        this.name = name;
        this.imgUrl = imgUrl;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getImgUrl()
    {
        return imgUrl;
    }
    
    public void setImgUrl(int imgUrl)
    {
        this.imgUrl = imgUrl;
    }
}
