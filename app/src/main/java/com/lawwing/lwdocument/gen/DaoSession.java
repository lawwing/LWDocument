package com.lawwing.lwdocument.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lawwing.lwdocument.gen.CommentInfoDb;

import com.lawwing.lwdocument.gen.CommentInfoDbDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig commentInfoDbDaoConfig;

    private final CommentInfoDbDao commentInfoDbDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        commentInfoDbDaoConfig = daoConfigMap.get(CommentInfoDbDao.class).clone();
        commentInfoDbDaoConfig.initIdentityScope(type);

        commentInfoDbDao = new CommentInfoDbDao(commentInfoDbDaoConfig, this);

        registerDao(CommentInfoDb.class, commentInfoDbDao);
    }
    
    public void clear() {
        commentInfoDbDaoConfig.getIdentityScope().clear();
    }

    public CommentInfoDbDao getCommentInfoDbDao() {
        return commentInfoDbDao;
    }

}
