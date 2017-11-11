package com.lawwing.lwdocument.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lawwing on 2017/11/11.
 */

@Entity
public class CommentInfoDb
{
    @Id
    private Long id;
    
    private String name;
    
    private String docname;
    
    private String path;
    
    private String docpath;
    
    private long time = 0;
    
    public long getTime()
    {
        return this.time;
    }
    
    public void setTime(long time)
    {
        this.time = time;
    }
    
    public String getDocpath()
    {
        return this.docpath;
    }
    
    public void setDocpath(String docpath)
    {
        this.docpath = docpath;
    }
    
    public String getPath()
    {
        return this.path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getDocname()
    {
        return this.docname;
    }
    
    public void setDocname(String docname)
    {
        this.docname = docname;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Long getId()
    {
        return this.id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    @Generated(hash = 464876029)
    public CommentInfoDb(Long id, String name, String docname, String path,
            String docpath, long time)
    {
        this.id = id;
        this.name = name;
        this.docname = docname;
        this.path = path;
        this.docpath = docpath;
        this.time = time;
    }
    
    @Generated(hash = 1788982875)
    public CommentInfoDb()
    {
    }
    
}
