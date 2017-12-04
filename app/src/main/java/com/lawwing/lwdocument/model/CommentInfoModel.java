package com.lawwing.lwdocument.model;

/**
 * Created by lawwing on 2017/11/11.
 */

public class CommentInfoModel extends HomeBaseModel
{
    private Long id;
    
    private String name;
    
    private String docname;
    
    private String path;
    
    private String docpath;
    
    private long time = 0;
    
    private long typeId;
    
    private long dateId;
    
    private String typeName;
    
    private boolean isTrueDelete = false;
    
    public CommentInfoModel(String typeName)
    {
        this.typeName = typeName;
    }
    
    public long getDateId()
    {
        return dateId;
    }
    
    public void setDateId(long dateId)
    {
        this.dateId = dateId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getDocname()
    {
        return docname;
    }
    
    public void setDocname(String docname)
    {
        this.docname = docname;
    }
    
    public String getDocpath()
    {
        return docpath;
    }
    
    public void setDocpath(String docpath)
    {
        this.docpath = docpath;
    }
    
    public long getTime()
    {
        return time;
    }
    
    public void setTime(long time)
    {
        this.time = time;
    }
    
    public long getTypeId()
    {
        return typeId;
    }
    
    public void setTypeId(long typeId)
    {
        this.typeId = typeId;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public boolean isTrueDelete()
    {
        return isTrueDelete;
    }
    
    public void setTrueDelete(boolean trueDelete)
    {
        isTrueDelete = trueDelete;
    }
}
