package com.lawwing.lwdocument.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lawwing on 2017/11/18.
 */
@Entity
public class ColorInfoDb
{
    @Id
    private Long id;
    
    private String color;
    
    private long createTime;

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1453638281)
    public ColorInfoDb(Long id, String color, long createTime) {
        this.id = id;
        this.color = color;
        this.createTime = createTime;
    }

    @Generated(hash = 795923756)
    public ColorInfoDb() {
    }
    
}
