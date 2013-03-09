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
        locations.add(newObservationSiteLocation("+139°41'", "+35°41'", "0°", "都庁")); // XXX strings.xml
        locations.add(newObservationSiteLocation("+135°45'", "+35°01'", "0°", "京都"));
        locations.add(newObservationSiteLocation("+138°43'", "+35°21'", "0°", "富士山"));
        locations.add(newObservationSiteLocation("+141°56'", "+45°31'", "0°", "北端－北海道－宗谷岬"));
        locations.add(newObservationSiteLocation("+136°04'", "+20°25'", "0°", "南端－東京－沖ノ鳥島"));
        locations.add(newObservationSiteLocation("+153°59'", "+24°16'", "0°", "東端－東京－南鳥島"));
        locations.add(newObservationSiteLocation("+122°56'", "+24°26'", "0°", "西端－沖縄－与那国島"));
        locations.add(newObservationSiteLocation("+105°83'", "+21°03'", "0°", "ベトナム－ハノイ"));
        locations.add(newObservationSiteLocation("-0.12", "+51.49'", "0°", "イギリス－ロンドン"));

    }

    private static ObservationSiteLocation newObservationSiteLocation(String latitude, String longitude, String altitude, String name) {
        ObservationSiteLocation result = new ObservationSiteLocation(   //
                StarLocationUtil.convertAngleStringToDouble(latitude),  //
                StarLocationUtil.convertAngleStringToDouble(longitude), //
                StarLocationUtil.convertAngleStringToDouble(altitude),
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
    private OnResolveObservationSiteLocationListener onResolveObservationSiteLocationListener;

    /**
     * コンストラクタ.<br/>
     */
    public ManualObservationSiteLocationResolver() {
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public void prepare() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void setOnResolveObservationSiteLocationListener(OnResolveObservationSiteLocationListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }

    @Override
    public void setObservationSiteLocation(ObservationSiteLocation location) {
        if (null != onResolveObservationSiteLocationListener) {
            onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(location);
        }
    }
}
