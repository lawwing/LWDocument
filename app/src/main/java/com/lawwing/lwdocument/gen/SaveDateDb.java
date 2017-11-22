package com.lawwing.lwdocument.gen;

import java.util.List;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lawwing on 2017/11/22.
 */
@Entity
public class SaveDateDb
{
    @Id
    private Long id;
    
    private int year;
    
    private int month;
    
    private int day;
    
    // 格式2017-11-22
    private String timeString;
    
    @ToMany(referencedJoinProperty = "dateId")
    private List<CommentInfoDb> commentInfoDbs;

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 190872840)
    public synchronized void resetCommentInfoDbs() {
        commentInfoDbs = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 104358587)
    public List<CommentInfoDb> getCommentInfoDbs() {
        if (commentInfoDbs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentInfoDbDao targetDao = daoSession.getCommentInfoDbDao();
            List<CommentInfoDb> commentInfoDbsNew = targetDao._querySaveDateDb_CommentInfoDbs(id);
            synchronized (this) {
                if(commentInfoDbs == null) {
                    commentInfoDbs = commentInfoDbsNew;
                }
            }
        }
        return commentInfoDbs;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 427811477)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSaveDateDbDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 318773402)
    private transient SaveDateDbDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public String getTimeString() {
        return this.timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1410886104)
    public SaveDateDb(Long id, int year, int month, int day, String timeString) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.timeString = timeString;
    }

    @Generated(hash = 758173549)
    public SaveDateDb() {
    }
}
