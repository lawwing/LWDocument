package com.lawwing.lwdocument.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMMENT_TYPE_INFO_DB".
*/
public class CommentTypeInfoDbDao extends AbstractDao<CommentTypeInfoDb, Long> {

    public static final String TABLENAME = "COMMENT_TYPE_INFO_DB";

    /**
     * Properties of entity CommentTypeInfoDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TypeName = new Property(1, String.class, "typeName", false, "TYPE_NAME");
        public final static Property CreateTime = new Property(2, long.class, "createTime", false, "CREATE_TIME");
        public final static Property IsEdit = new Property(3, boolean.class, "isEdit", false, "IS_EDIT");
        public final static Property IsShow = new Property(4, boolean.class, "isShow", false, "IS_SHOW");
    };

    private DaoSession daoSession;


    public CommentTypeInfoDbDao(DaoConfig config) {
        super(config);
    }
    
    public CommentTypeInfoDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMMENT_TYPE_INFO_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TYPE_NAME\" TEXT," + // 1: typeName
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 2: createTime
                "\"IS_EDIT\" INTEGER NOT NULL ," + // 3: isEdit
                "\"IS_SHOW\" INTEGER NOT NULL );"); // 4: isShow
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMMENT_TYPE_INFO_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CommentTypeInfoDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(2, typeName);
        }
        stmt.bindLong(3, entity.getCreateTime());
        stmt.bindLong(4, entity.getIsEdit() ? 1L: 0L);
        stmt.bindLong(5, entity.getIsShow() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CommentTypeInfoDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(2, typeName);
        }
        stmt.bindLong(3, entity.getCreateTime());
        stmt.bindLong(4, entity.getIsEdit() ? 1L: 0L);
        stmt.bindLong(5, entity.getIsShow() ? 1L: 0L);
    }

    @Override
    protected final void attachEntity(CommentTypeInfoDb entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CommentTypeInfoDb readEntity(Cursor cursor, int offset) {
        CommentTypeInfoDb entity = new CommentTypeInfoDb( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // typeName
            cursor.getLong(offset + 2), // createTime
            cursor.getShort(offset + 3) != 0, // isEdit
            cursor.getShort(offset + 4) != 0 // isShow
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CommentTypeInfoDb entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTypeName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.getLong(offset + 2));
        entity.setIsEdit(cursor.getShort(offset + 3) != 0);
        entity.setIsShow(cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CommentTypeInfoDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CommentTypeInfoDb entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
