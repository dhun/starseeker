/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.observationsite.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 観測地点を手動で選択する観測地点の位置のリゾルバ.<br/>
 * 
 * @author jun
 * 
 */
public class ManualObservationSiteLocationResolver implements IObservationSiteLocationResolver {

    // ********************************************************************************
    // == class

    /** 観測地点の選択肢 */
    private static final List<ObservationSiteLocation> locations;

    /**
     * @see http://www.gsi.go.jp/KOKUJYOHO/center.htm
     * @see http://世界地図.biz/006_1/cat3/
     */
    static {
        locations = new ArrayList<ObservationSiteLocation>();
        locations.add(newObservationSiteLocation("+139°41'", "+35°41'", "0°0'", "東京－都庁")); // XXX strings.xml
        locations.add(newObservationSiteLocation("+135°45'", "+35°01'", "0°0'", "京都－府庁"));
        locations.add(newObservationSiteLocation("+138°43'", "+35°21'", "0°0'", "山梨－富士山"));
        locations.add(newObservationSiteLocation("+141°56'", "+45°31'", "0°0'", "北端－北海道－宗谷岬"));
        locations.add(newObservationSiteLocation("+136°04'", "+20°25'", "0°0'", "南端－東京－沖ノ鳥島"));
        locations.add(newObservationSiteLocation("+153°59'", "+24°16'", "0°0'", "東端－東京－南鳥島"));
        locations.add(newObservationSiteLocation("+122°56'", "+24°26'", "0°0'", "西端－沖縄－与那国島"));
        locations.add(newObservationSiteLocation("+105°83'", "+21°03'", "0°0'", "ベトナム－ハノイ"));
        locations.add(newObservationSiteLocation("-000°12'", "+51°49'", "0°0'", "イギリス－ロンドン"));
        locations.add(newObservationSiteLocation("-154°40'", "+18°55'", "0°0'", "アメリカ－ハワイ"));
        locations.add(newObservationSiteLocation("+059°33'", "+18°07'", "0°0'", "スウェーデン－ストックホルム"));
    }

    private static ObservationSiteLocation newObservationSiteLocation(String latitude, String longitude, String altitude, String name) {
        ObservationSiteLocation result = new ObservationSiteLocation(   //
                StarLocationUtil.convertAngleStringToDouble(latitude),  //
                StarLocationUtil.convertAngleStringToDouble(longitude), //
                StarLocationUtil.convertAngleStringToDouble(altitude),  //
                name);
        return result;
    }

    /**
     * 観測地点の選択肢を取得します.<br/>
     */
    public static List<ObservationSiteLocation> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    // ********************************************************************************
    // == instance

    /** 観測地点の位置を解決できたことの通知を受け取るリスナ */
    private ObservationSiteLocationResolverListener onResolveObservationSiteLocationListener;

    /**
     * コンストラクタ.<br/>
     */
    public ManualObservationSiteLocationResolver() {
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }

    @Override
    public void setObservationSiteLocation(ObservationSiteLocation location) {
        if (null != onResolveObservationSiteLocationListener) {
            onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(location);
        }
    }
}
