package com.lawwing.lwdocument.gen;

import java.util.List;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lawwing on 2017/11/19.
 */
@Entity
public class CommentTypeInfoDb
{
    @Id
    private Long id;
    
    private String typeName;
    
    private long createTime;
    
    // 一对多
    @ToMany(referencedJoinProperty = "typeId")
    private List<CommentInfoDb> commentInfoDbs;
    
    // 是否可编辑
    private boolean isEdit = true;
    
    /** Used for active entity operations. */
    @Generated(hash = 2146236101)
    private transient CommentTypeInfoDbDao myDao;
    
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    
    public boolean getIsEdit()
    {
        return this.isEdit;
    }
    
    public void setIsEdit(boolean isEdit)
    {
        this.isEdit = isEdit;
    }
    
    public long getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }
    
    public String getTypeName()
    {
        return this.typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public Long getId()
    {
        return this.id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Convenient call for
     * {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}. Entity must
     * attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh()
    {
        if (myDao == null)
        {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    
    /**
     * Convenient call for
     * {@link org.greenrobot.greendao.AbstractDao#update(Object)}. Entity must
     * attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update()
    {
        if (myDao == null)
        {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    
    /**
     * Convenient call for
     * {@link org.greenrobot.greendao.AbstractDao#delete(Object)}. Entity must
     * attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete()
    {
        if (myDao == null)
        {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    
    /**
     * Resets a to-many relationship, making the next get call to query for a
     * fresh result.
     */
    @Generated(hash = 190872840)
    public synchronized void resetCommentInfoDbs()
    {
        commentInfoDbs = null;
    }
    
    /**
     * To-many relationship, resolved on first access (and after reset). Changes
     * to to-many relations are not persisted, make changes to the target
     * entity.
     */
    @Generated(hash = 985814597)
    public List<CommentInfoDb> getCommentInfoDbs()
    {
        if (commentInfoDbs == null)
        {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null)
            {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentInfoDbDao targetDao = daoSession.getCommentInfoDbDao();
            List<CommentInfoDb> commentInfoDbsNew = targetDao
                    ._queryCommentTypeInfoDb_CommentInfoDbs(id);
            synchronized (this)
            {
                if (commentInfoDbs == null)
                {
                    commentInfoDbs = commentInfoDbsNew;
                }
            }
        }
        return commentInfoDbs;
    }
    
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1195494763)
    public void __setDaoSession(DaoSession daoSession)
    {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommentTypeInfoDbDao()
                : null;
    }
    
    @Generated(hash = 1703824513)
    public CommentTypeInfoDb(Long id, String typeName, long createTime,
            boolean isEdit)
    {
        this.id = id;
        this.typeName = typeName;
        this.createTime = createTime;
        this.isEdit = isEdit;
    }
    
    @Generated(hash = 416910148)
    public CommentTypeInfoDb()
    {
    }
}
