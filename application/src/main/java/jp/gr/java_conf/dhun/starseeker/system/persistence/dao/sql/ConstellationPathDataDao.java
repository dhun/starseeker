/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationPathData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星座パスデータのDAO
 * 
 * @author jun
 * 
 */
public class ConstellationPathDataDao extends AbstractSqlDao<ConstellationPathData, Integer> {

    public ConstellationPathDataDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected String getTableName() {
        return ConstellationPathData.TABLE_NAME;
    }

    @Override
    protected String[] getAllColumns() {
        return ConstellationPathData.FieldNames.ALL_COLUMNS;
    }

    public List<ConstellationPathData> findAll() {
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = null;

        return list(selection, selectionArgs, orderBy);
    }

    public List<ConstellationPathData> findByConstellationCode(String constellationCode) {
        String selection = String.format("%s = ?", ConstellationPathData.FieldNames.CONSTELLATION_CODE);
        String[] selectionArgs = { constellationCode };
        String orderBy = null;

        return list(selection, selectionArgs, orderBy);
    }

    @Override
    protected ConstellationPathData convertToEntity(Cursor cursor) {
        ConstellationPathData result = new ConstellationPathData();
        result.setConstellationCode(cursor.getString(cursor.getColumnIndex(ConstellationPathData.FieldNames.CONSTELLATION_CODE)));
        result.setHipNumberFrom(cursor.getInt(cursor.getColumnIndex(ConstellationPathData.FieldNames.HIP_NUMBER_FROM)));
        result.setHipNumberTo(cursor.getInt(cursor.getColumnIndex(ConstellationPathData.FieldNames.HIP_NUMBER_TO)));

        return result;
    }
}
