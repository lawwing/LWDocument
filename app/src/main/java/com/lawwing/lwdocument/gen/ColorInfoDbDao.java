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
 * DAO for table "COLOR_INFO_DB".
*/
public class ColorInfoDbDao extends AbstractDao<ColorInfoDb, Long> {

    public static final String TABLENAME = "COLOR_INFO_DB";

    /**
     * Properties of entity ColorInfoDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Color = new Property(1, String.class, "color", false, "COLOR");
        public final static Property CreateTime = new Property(2, long.class, "createTime", false, "CREATE_TIME");
    };


    public ColorInfoDbDao(DaoConfig config) {
        super(config);
    }
    
    public ColorInfoDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COLOR_INFO_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"COLOR\" TEXT," + // 1: color
                "\"CREATE_TIME\" INTEGER NOT NULL );"); // 2: createTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COLOR_INFO_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ColorInfoDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String color = entity.getColor();
        if (color != null) {
            stmt.bindString(2, color);
        }
        stmt.bindLong(3, entity.getCreateTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ColorInfoDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String color = entity.getColor();
        if (color != null) {
            stmt.bindString(2, color);
        }
        stmt.bindLong(3, entity.getCreateTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ColorInfoDb readEntity(Cursor cursor, int offset) {
        ColorInfoDb entity = new ColorInfoDb( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // color
            cursor.getLong(offset + 2) // createTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ColorInfoDb entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setColor(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ColorInfoDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ColorInfoDb entity) {
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
