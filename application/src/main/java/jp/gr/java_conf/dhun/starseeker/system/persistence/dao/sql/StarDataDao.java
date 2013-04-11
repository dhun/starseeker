/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.model.StarApproxMagnitude;
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

        Cursor cursor = null;
        try {
            cursor = db.query(StarEntity.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

            List<StarEntity> results = new ArrayList<StarEntity>(cursor.getCount());
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

    public List<StarEntity> findByMagnitudeRange(StarApproxMagnitude approxMagnitude) {
        String[] columns = StarEntity.FieldNames.ALL_COLUMNS;
        String selection = String.format("%s >= ? and %s < ?", StarEntity.FieldNames.MAGNITUDE, StarEntity.FieldNames.MAGNITUDE);
        String[] selectionArgs = { String.valueOf(approxMagnitude.getLowerMagnitude()), String.valueOf(approxMagnitude.getUpperMagnitude()) };
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = null;
        try {
            cursor = db.query(StarEntity.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

            List<StarEntity> results = new ArrayList<StarEntity>(cursor.getCount());
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

    private StarEntity convertToEntity(Cursor cursor) {
        StarEntity result = new StarEntity();
        result.setHipNumber(cursor.getInt(cursor.getColumnIndex(StarEntity.FieldNames.HIP_NUMBER)));
        result.setRightAscension(cursor.getFloat(cursor.getColumnIndex(StarEntity.FieldNames.RIGHT_ASCENSION)));
        result.setDeclination(cursor.getFloat(cursor.getColumnIndex(StarEntity.FieldNames.DECLINATION)));
        result.setMagnitude(cursor.getFloat(cursor.getColumnIndex(StarEntity.FieldNames.MAGNITUDE)));
        result.setName(cursor.getString(cursor.getColumnIndex(StarEntity.FieldNames.NAME)));
        result.setMemo(cursor.getString(cursor.getColumnIndex(StarEntity.FieldNames.MEMO)));

        return result;
    }
}
