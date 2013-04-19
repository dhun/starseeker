/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.model.star.StarMagnitude;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星データのDAO
 * 
 * @author jun
 * 
 */
public class StarDataDao extends AbstractSqlDao<StarData, Integer> {

    public StarDataDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected String getTableName() {
        return StarData.TABLE_NAME;
    }

    @Override
    protected String[] getAllColumns() {
        return StarData.FieldNames.ALL_COLUMNS;
    }

    public StarData findByPk(Integer hipNumber) {
        String selection = StarData.FieldNames.HIP_NUMBER + " = ?";
        String[] selectionArgs = { String.valueOf(hipNumber) };
        String orderBy = null;

        return find(selection, selectionArgs, orderBy);
    }

    public List<StarData> findAll() {
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = null;

        return list(selection, selectionArgs, orderBy);
    }

    public List<StarData> findByMagnitudeRange(StarMagnitude magnitude) {
        String selection = String.format("%s >= ? and %s < ?", StarData.FieldNames.MAGNITUDE, StarData.FieldNames.MAGNITUDE);
        String[] selectionArgs = { String.valueOf(magnitude.getLowerMagnitude()), String.valueOf(magnitude.getUpperMagnitude()) };
        String orderBy = null;

        return list(selection, selectionArgs, orderBy);
    }

    @Override
    protected StarData convertToEntity(Cursor cursor) {
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
