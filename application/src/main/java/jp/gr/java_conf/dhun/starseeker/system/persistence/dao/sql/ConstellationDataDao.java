/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.model.StarApproxMagnitude;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationData;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationPathData;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星座データのDAO
 * 
 * @author jun
 * 
 */
public class ConstellationDataDao extends AbstractSqlDao<ConstellationData, String> {

    public ConstellationDataDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected String getTableName() {
        return ConstellationData.TABLE_NAME;
    }

    @Override
    protected String[] getAllColumns() {
        return ConstellationData.FieldNames.ALL_COLUMNS;
    }

    public List<ConstellationData> findAll() {
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = null;

        return list(selection, selectionArgs, orderBy);
    }

    public List<ConstellationData> findLessThanUpperMagnitude(StarApproxMagnitude approxMagnitude) {
        StringBuilder selection = new StringBuilder();
        selection.append(ConstellationData.TABLE_NAME + "." + ConstellationData.FieldNames.CONSTELLATION_CODE + " in (");
        selection.append("  select x." + ConstellationPathData.FieldNames.CONSTELLATION_CODE);
        selection.append("    from " + ConstellationPathData.TABLE_NAME + " x");
        selection.append("    join " + StarData.TABLE_NAME + " y");
        selection.append("      on y." + StarData.FieldNames.HIP_NUMBER + " = x." + ConstellationPathData.FieldNames.HIP_NUMBER_FROM);
        selection.append("      or y." + StarData.FieldNames.HIP_NUMBER + " = x." + ConstellationPathData.FieldNames.HIP_NUMBER_TO);
        selection.append("   where y." + StarData.FieldNames.MAGNITUDE + " < ?)");

        String[] selectionArgs = { String.valueOf(approxMagnitude.getUpperMagnitude()) };

        String orderBy = null;

        return list(selection.toString(), selectionArgs, orderBy);
    }

    @Override
    protected ConstellationData convertToEntity(Cursor cursor) {
        ConstellationData result = new ConstellationData();
        result.setConstellationCode(cursor.getString(cursor.getColumnIndex(ConstellationData.FieldNames.CONSTELLATION_CODE)));
        result.setConstellationName(cursor.getString(cursor.getColumnIndex(ConstellationData.FieldNames.CONSTELLATION_NAME)));
        result.setJapaneseName(cursor.getString(cursor.getColumnIndex(ConstellationData.FieldNames.JAPANESE_NAME)));
        result.setRightAscension(cursor.getFloat(cursor.getColumnIndex(ConstellationData.FieldNames.RIGHT_ASCENSION)));
        result.setDeclination(cursor.getFloat(cursor.getColumnIndex(ConstellationData.FieldNames.DECLINATION)));

        return result;
    }
}
