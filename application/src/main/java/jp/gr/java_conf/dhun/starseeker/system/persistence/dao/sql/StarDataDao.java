/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.model.StarApproxMagnitude;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星データのDAO
 * 
 * @author jun
 * 
 */
public class StarDataDao {

    private final SQLiteDatabase db;

    public StarDataDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<StarData> findAll() {
        String[] columns = StarData.FieldNames.ALL_COLUMNS;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = null;
        try {
            cursor = db.query(StarData.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

            List<StarData> results = new ArrayList<StarData>(cursor.getCount());
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

    public List<StarData> findByMagnitudeRange(StarApproxMagnitude approxMagnitude) {
        String[] columns = StarData.FieldNames.ALL_COLUMNS;
        String selection = String.format("%s >= ? and %s < ?", StarData.FieldNames.MAGNITUDE, StarData.FieldNames.MAGNITUDE);
        String[] selectionArgs = { String.valueOf(approxMagnitude.getLowerMagnitude()), String.valueOf(approxMagnitude.getUpperMagnitude()) };
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = null;
        try {
            cursor = db.query(StarData.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

            List<StarData> results = new ArrayList<StarData>(cursor.getCount());
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

    private StarData convertToEntity(Cursor cursor) {
        StarData result = new StarData();
        result.setHipNumber(cursor.getInt(cursor.getColumnIndex(StarData.FieldNames.HIP_NUMBER)));
        result.setRightAscension(cursor.getFloat(cursor.getColumnIndex(StarData.FieldNames.RIGHT_ASCENSION)));
        result.setDeclination(cursor.getFloat(cursor.getColumnIndex(StarData.FieldNames.DECLINATION)));
        result.setMagnitude(cursor.getFloat(cursor.getColumnIndex(StarData.FieldNames.MAGNITUDE)));
        result.setName(cursor.getString(cursor.getColumnIndex(StarData.FieldNames.NAME)));
        result.setMemo(cursor.getString(cursor.getColumnIndex(StarData.FieldNames.MEMO)));

        return result;
    }
}
