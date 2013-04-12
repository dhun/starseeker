/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationPath;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星座パスのDAO
 * 
 * @author jun
 * 
 */
public class ConstellationPathDao {

    private final SQLiteDatabase db;

    public ConstellationPathDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<ConstellationPath> findAll() {
        String[] columns = ConstellationPath.FieldNames.ALL_COLUMNS;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = null;
        try {
            cursor = db.query(ConstellationPath.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

            List<ConstellationPath> results = new ArrayList<ConstellationPath>(cursor.getCount());
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

    private ConstellationPath convertToEntity(Cursor cursor) {
        ConstellationPath result = new ConstellationPath();
        result.setConstellationCode(cursor.getString(cursor.getColumnIndex(ConstellationPath.FieldNames.CONSTELLATION_CODE)));
        result.setHipNumberFrom(cursor.getInt(cursor.getColumnIndex(ConstellationPath.FieldNames.HIP_NUMBER_FROM)));
        result.setHipNumberTo(cursor.getInt(cursor.getColumnIndex(ConstellationPath.FieldNames.HIP_NUMBER_TO)));

        return result;
    }
}
