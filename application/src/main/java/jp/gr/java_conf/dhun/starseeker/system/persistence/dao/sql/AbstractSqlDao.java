/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerRuntimeException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLITEを利用するDAOの抽象実装
 * 
 * @author jun
 * 
 */
public abstract class AbstractSqlDao<ENTITY, PK> {

    protected final SQLiteDatabase db;

    protected AbstractSqlDao(SQLiteDatabase db) {
        this.db = db;
    }

    protected abstract String getTableName();

    protected abstract String[] getAllColumns();

    protected ENTITY find(String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = null;
        try {
            cursor = db.query(getTableName(), getAllColumns(), selection, selectionArgs, null, null, orderBy);
            if (cursor.moveToNext()) {
                ENTITY entity = convertToEntity(cursor);
                if (cursor.moveToNext()) {
                    throw new StarSeekerRuntimeException("too many rows.");
                } else {
                    return entity;
                }
            } else {
                return null;
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    protected List<ENTITY> list(String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = null;
        try {
            cursor = db.query(getTableName(), getAllColumns(), selection, selectionArgs, null, null, orderBy);

            List<ENTITY> results = new ArrayList<ENTITY>(cursor.getCount());
            while (cursor.moveToNext()) {
                results.add(convertToEntity(cursor));
            }
            return results;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    protected abstract ENTITY convertToEntity(Cursor cursor);
}
