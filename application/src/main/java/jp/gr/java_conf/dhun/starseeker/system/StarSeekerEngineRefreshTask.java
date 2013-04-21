/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system;

import jp.gr.java_conf.dhun.starseeker.system.model.StarSeekerEngineConfig;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.os.AsyncTask;

/**
 * @author jun
 * 
 */
public class StarSeekerEngineRefreshTask extends AsyncTask<StarSeekerEngineConfig, Integer, StarSeekerEngine> {

    private final StarSeekerEngine starSeekerEngine;

    public StarSeekerEngineRefreshTask(StarSeekerEngine starSeekerEngine) {
        this.starSeekerEngine = starSeekerEngine;
    }

    @Override
    protected StarSeekerEngine doInBackground(StarSeekerEngineConfig... configs) {
        // パラメータチェック
        if (null == configs) {
            throw new IllegalArgumentException("configs must be not null.");
        } else if (1 != configs.length) {
            throw new IllegalArgumentException("configs's length must be one. length=[" + configs.length + "]");
        }

        StarSeekerEngineConfig config = configs[0];

        LogUtils.i(getClass(), String.format("緯度=[%6.2f], 経度=[%6.2f], 日時=[%tT], 等級=[%3.2f]" //
                , config.getObservationSiteLocation().getLongitude() //
                , config.getObservationSiteLocation().getLatitude() //
                , config.getCoordinatesCalculateBaseCalendar().getTime() //
                , config.getExtractUpperStarMagnitude() //
                ));

        // 観測地点の解決
        // FIXME

        // エンジンの設定
        starSeekerEngine.setObservationCondition(config.getObservationSiteLocation(), config.getCoordinatesCalculateBaseCalendar());
        starSeekerEngine.setExtractUpperStarMagnitude(config.getExtractUpperStarMagnitude());

        // 星の抽出
        starSeekerEngine.extract();

        // 星の配置
        starSeekerEngine.locate();

        starSeekerEngine.prepare();

        return starSeekerEngine;
    }
}
