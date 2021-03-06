package com.lawwing.lwdocument.gen;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMMENT_INFO_DB".
*/
public class CommentInfoDbDao extends AbstractDao<CommentInfoDb, Long> {

    public static final String TABLENAME = "COMMENT_INFO_DB";

    /**
     * Properties of entity CommentInfoDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Docname = new Property(2, String.class, "docname", false, "DOCNAME");
        public final static Property Path = new Property(3, String.class, "path", false, "PATH");
        public final static Property Docpath = new Property(4, String.class, "docpath", false, "DOCPATH");
        public final static Property TypeId = new Property(5, long.class, "typeId", false, "TYPE_ID");
        public final static Property Time = new Property(6, long.class, "time", false, "TIME");
        public final static Property DateId = new Property(7, long.class, "dateId", false, "DATE_ID");
        public final static Property IsTrueDelete = new Property(8, boolean.class, "isTrueDelete", false, "IS_TRUE_DELETE");
    };

    private Query<CommentInfoDb> commentTypeInfoDb_CommentInfoDbsQuery;
    private Query<CommentInfoDb> saveDateDb_CommentInfoDbsQuery;

    public CommentInfoDbDao(DaoConfig config) {
        super(config);
    }
    
    public CommentInfoDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMMENT_INFO_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"DOCNAME\" TEXT," + // 2: docname
                "\"PATH\" TEXT," + // 3: path
                "\"DOCPATH\" TEXT," + // 4: docpath
                "\"TYPE_ID\" INTEGER NOT NULL ," + // 5: typeId
                "\"TIME\" INTEGER NOT NULL ," + // 6: time
                "\"DATE_ID\" INTEGER NOT NULL ," + // 7: dateId
                "\"IS_TRUE_DELETE\" INTEGER NOT NULL );"); // 8: isTrueDelete
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMMENT_INFO_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CommentInfoDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String docname = entity.getDocname();
        if (docname != null) {
            stmt.bindString(3, docname);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(4, path);
        }
 
        String docpath = entity.getDocpath();
        if (docpath != null) {
            stmt.bindString(5, docpath);
        }
        stmt.bindLong(6, entity.getTypeId());
        stmt.bindLong(7, entity.getTime());
        stmt.bindLong(8, entity.getDateId());
        stmt.bindLong(9, entity.getIsTrueDelete() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CommentInfoDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String docname = entity.getDocname();
        if (docname != null) {
            stmt.bindString(3, docname);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(4, path);
        }
 
        String docpath = entity.getDocpath();
        if (docpath != null) {
            stmt.bindString(5, docpath);
        }
        stmt.bindLong(6, entity.getTypeId());
        stmt.bindLong(7, entity.getTime());
        stmt.bindLong(8, entity.getDateId());
        stmt.bindLong(9, entity.getIsTrueDelete() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CommentInfoDb readEntity(Cursor cursor, int offset) {
        CommentInfoDb entity = new CommentInfoDb( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // docname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // path
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // docpath
            cursor.getLong(offset + 5), // typeId
            cursor.getLong(offset + 6), // time
            cursor.getLong(offset + 7), // dateId
            cursor.getShort(offset + 8) != 0 // isTrueDelete
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CommentInfoDb entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDocname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPath(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDocpath(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTypeId(cursor.getLong(offset + 5));
        entity.setTime(cursor.getLong(offset + 6));
        entity.setDateId(cursor.getLong(offset + 7));
        entity.setIsTrueDelete(cursor.getShort(offset + 8) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CommentInfoDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CommentInfoDb entity) {
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
    
    /** Internal query to resolve the "commentInfoDbs" to-many relationship of CommentTypeInfoDb. */
    public List<CommentInfoDb> _queryCommentTypeInfoDb_CommentInfoDbs(long typeId) {
        synchronized (this) {
            if (commentTypeInfoDb_CommentInfoDbsQuery == null) {
                QueryBuilder<CommentInfoDb> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TypeId.eq(null));
                commentTypeInfoDb_CommentInfoDbsQuery = queryBuilder.build();
            }
        }
        Query<CommentInfoDb> query = commentTypeInfoDb_CommentInfoDbsQuery.forCurrentThread();
        query.setParameter(0, typeId);
        return query.list();
    }

    /** Internal query to resolve the "commentInfoDbs" to-many relationship of SaveDateDb. */
    public List<CommentInfoDb> _querySaveDateDb_CommentInfoDbs(long dateId) {
        synchronized (this) {
            if (saveDateDb_CommentInfoDbsQuery == null) {
                QueryBuilder<CommentInfoDb> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.DateId.eq(null));
                saveDateDb_CommentInfoDbsQuery = queryBuilder.build();
            }
        }
        Query<CommentInfoDb> query = saveDateDb_CommentInfoDbsQuery.forCurrentThread();
        query.setParameter(0, dateId);
        return query.list();
    }

}
