/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarEntity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author jun
 * 
 */
public class StarDataDao {

    private final SQLiteDatabase db;

    public StarDataDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<StarEntity> findAll() {
        String[] columns = StarEntity.FieldNames.ALL_COLUMNS;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = db.query(StarEntity.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        List<StarEntity> results = new ArrayList<StarEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
            StarEntity result = new StarEntity();
            result.setHipNumber(cursor.getInt(cursor.getColumnIndex(StarEntity.FieldNames.HIP_NUMBER)));
            result.setRightAscension(cursor.getFloat(cursor.getColumnIndex(StarEntity.FieldNames.RIGHT_ASCENSION)));
            result.setDeclination(cursor.getFloat(cursor.getColumnIndex(StarEntity.FieldNames.DECLINATION)));
            result.setMagnitude(cursor.getFloat(cursor.getColumnIndex(StarEntity.FieldNames.MAGNITUDE)));
            result.setName(cursor.getString(cursor.getColumnIndex(StarEntity.FieldNames.NAME)));
            result.setMemo(cursor.getString(cursor.getColumnIndex(StarEntity.FieldNames.MEMO)));
            results.add(result);
        }
        return results;
    }
}
