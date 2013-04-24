/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver.ObservationSiteLocationResolverType;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author jun
 */
public class ObservationSiteLocationDao extends AbstractSqlDao<ObservationSiteLocation, Integer> {

    public ObservationSiteLocationDao(SQLiteDatabase db) {
        super(db);
    }

    public ObservationSiteLocation findByPk(Integer pk) {
        for (ObservationSiteLocation entity : listAll()) {
            if (entity.getId().equals(pk)) {
                return entity;
            }
        }
        return null;
    }

    public List<ObservationSiteLocation> listAll() {
        List<ObservationSiteLocation> result = new ArrayList<ObservationSiteLocation>();

        result.addAll(listLocationProviderObservationSiteLocations());
        result.addAll(listChooseObservationSiteLocation());

        return result;
    }

    public List<ObservationSiteLocation> listLocationProviderObservationSiteLocations() {
        List<ObservationSiteLocation> locations = new ArrayList<ObservationSiteLocation>();
        locations.add(newInstance(ObservationSiteLocationResolverType.GPS, "0°0'", "0°0'", "0°0'", TimeZone.getDefault().getID(), "現在地(GPS)")); // XXX strings.xml
        locations.add(newInstance(ObservationSiteLocationResolverType.NETWORK, "0°0'", "0°0'", "0°0'", TimeZone.getDefault().getID(), "現在地(簡易GPS)")); // XXX
                                                                                                                                                      // strings.xml
        return locations;
    }

    private ObservationSiteLocation newInstance(ObservationSiteLocationResolverType resolverType, String longitude, String latitude, String altitude, String timezoneName, String name) {
        ObservationSiteLocation result = new ObservationSiteLocation(  //
                StarLocationUtil.convertAngleStringToFloat(latitude),  // 緯度
                StarLocationUtil.convertAngleStringToFloat(longitude), // 経度
                StarLocationUtil.convertAngleStringToFloat(altitude)); // 高度
        result.setId(resolverType == ObservationSiteLocationResolverType.GPS ? ObservationSiteLocation.ID_GPS : ObservationSiteLocation.ID_NETWORK);
        result.setResolverType(resolverType);
        result.setName(name);
        result.setTimeZone(TimeZone.getTimeZone(timezoneName));
        return result;
    }

    // @see http://www.gsi.go.jp/KOKUJYOHO/center.htm
    // @see http://世界地図.biz/006_1/cat3/
    public List<ObservationSiteLocation> listChooseObservationSiteLocation() {
        int nextId = 0;
        List<ObservationSiteLocation> locations = new ArrayList<ObservationSiteLocation>();

        locations.add(newInstance(nextId++, "+139°41'", "+35°41'", "0°0'", "Asia/Tokyo", "東京－都庁")); // XXX strings.xml
        locations.add(newInstance(nextId++, "+135°45'", "+35°01'", "0°0'", "Asia/Tokyo", "京都－府庁"));
        locations.add(newInstance(nextId++, "+138°43'", "+35°21'", "0°0'", "Asia/Tokyo", "山梨－富士山"));
        locations.add(newInstance(nextId++, "+141°56'", "+45°31'", "0°0'", "Asia/Tokyo", "北端－北海道－宗谷岬"));
        locations.add(newInstance(nextId++, "+136°04'", "+20°25'", "0°0'", "Asia/Tokyo", "南端－東京－沖ノ鳥島"));
        locations.add(newInstance(nextId++, "+153°59'", "+24°16'", "0°0'", "Asia/Tokyo", "東端－東京－南鳥島"));
        locations.add(newInstance(nextId++, "+122°56'", "+24°26'", "0°0'", "Asia/Tokyo", "西端－沖縄－与那国島"));
        locations.add(newInstance(nextId++, "+105°83'", "+21°03'", "0°0'", "Asia/Ho_Chi_Minh", "ベトナム－ホーチミン"));
        locations.add(newInstance(nextId++, "-000°12'", "+51°49'", "0°0'", "Europe/London", "イギリス－ロンドン"));
        locations.add(newInstance(nextId++, "-154°40'", "+18°55'", "0°0'", "US/Hawaii", "アメリカ－ハワイ"));
        locations.add(newInstance(nextId++, "+059°33'", "+18°07'", "0°0'", "Europe/Stockholm", "スウェーデン－ストックホルム"));
        locations.add(newInstance(nextId++, "-023°32'", "-46°38'", "0°0'", "Brazil/East", "サンパウロ"));

        return locations;
    }

    private ObservationSiteLocation newInstance(int id, String longitude, String latitude, String altitude, String timezoneName, String name) {
        ObservationSiteLocation result = new ObservationSiteLocation(  //
                StarLocationUtil.convertAngleStringToFloat(latitude),  // 緯度
                StarLocationUtil.convertAngleStringToFloat(longitude), // 経度
                StarLocationUtil.convertAngleStringToFloat(altitude)); // 高度
        result.setId(id);
        result.setResolverType(ObservationSiteLocationResolverType.LIST_CHOOSE);
        result.setName(name);
        result.setTimeZone(TimeZone.getTimeZone(timezoneName));
        return result;
    }

    @Override
    protected String getTableName() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String[] getAllColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObservationSiteLocation convertToEntity(Cursor cursor) {
        throw new UnsupportedOperationException();
    }
}
