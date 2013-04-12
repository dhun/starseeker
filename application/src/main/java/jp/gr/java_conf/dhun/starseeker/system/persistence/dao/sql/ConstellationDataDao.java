/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星座データのDAO
 * 
 * @author jun
 * 
 */
public class ConstellationDataDao {

    private final SQLiteDatabase db;

    public ConstellationDataDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<ConstellationData> findAll() {
        String[] columns = ConstellationData.FieldNames.ALL_COLUMNS;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = null;
        try {
            cursor = db.query(ConstellationData.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

            List<ConstellationData> results = new ArrayList<ConstellationData>(cursor.getCount());
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

    private ConstellationData convertToEntity(Cursor cursor) {
        ConstellationData result = new ConstellationData();
        result.setConstellationCode(cursor.getString(cursor.getColumnIndex(ConstellationData.FieldNames.CONSTELLATION_CODE)));
        result.setConstellationName(cursor.getString(cursor.getColumnIndex(ConstellationData.FieldNames.CONSTELLATION_NAME)));
        result.setJapaneseName(cursor.getString(cursor.getColumnIndex(ConstellationData.FieldNames.JAPANESE_NAME)));
        result.setRightAscension(cursor.getFloat(cursor.getColumnIndex(ConstellationData.FieldNames.RIGHT_ASCENSION)));
        result.setDeclination(cursor.getFloat(cursor.getColumnIndex(ConstellationData.FieldNames.DECLINATION)));

        return result;
    }
}
