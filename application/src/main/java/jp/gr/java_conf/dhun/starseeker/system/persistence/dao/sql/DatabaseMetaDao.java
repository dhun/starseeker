/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.DatabaseMeta;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author jun
 * 
 */
@Deprecated
public class DatabaseMetaDao {

    private final SQLiteDatabase db;

    public DatabaseMetaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public DatabaseMeta find() {
        String[] columns = DatabaseMeta.FieldNames.ALL_COLUMNS;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = db.query(DatabaseMeta.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        switch (cursor.getCount()) {
        case 0:
            return null;
        case 1:
            return convertToEntity(cursor);
        default:
            throw new IllegalStateException("too many rows.");
        }
    }

    private DatabaseMeta convertToEntity(Cursor cursor) {
        DatabaseMeta result = new DatabaseMeta();
        result.setRegistTimestamp(new Date(cursor.getLong(cursor.getColumnIndex(DatabaseMeta.FieldNames.TIMESTAMP))));

        return result;
    }
}
