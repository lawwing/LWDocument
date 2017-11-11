package com.lawwing.lwdocument.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lawwing on 2017/11/11.
 */
@Entity
public class PaintInfoDb
{
    @Id
    private Long id;
    
    private String name;
    
    private String path;
    
    private long time;
    
    public String getPath()
    {
        return this.path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
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

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    @Generated(hash = 278412209)
    public PaintInfoDb(Long id, String name, String path, long time) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.time = time;
    }

    @Generated(hash = 777247145)
    public PaintInfoDb()
    {
    }
}
