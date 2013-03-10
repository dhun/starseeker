package jp.gr.java_conf.dhun.starseeker.logic.observationsite.location;

import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;

/**
 * 観測地点リゾルバのインターフェース.<br/>
 * 
 * @author jun
 * 
 */
public interface IObservationSiteLocationResolver {

    /**
     * リゾルバを再開します..<br/>
     * Activity.onResumeなどで呼び出してください.
     */
    void resume();

    /**
     * リゾルバを停止します.<br/>
     * Activity.onPauseなどで呼び出してください.
     */
    void pause();

    /**
     * 観測地点リゾルバのリスナを設定します.<br/>
     */
    void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener);

    /**
     * 観測地点の位置を設定します.<br/>
     */
    void setObservationSiteLocation(ObservationSiteLocation location);

    /**
     * 観測地点リゾルバのリスナ.<br/>
     */
    interface ObservationSiteLocationResolverListener {

        /**
         * 観測地点を解決できたことを通知します.<br/>
         * 
         * @param location 観測地点
         */
        void onResolveObservationSiteLocation(ObservationSiteLocation location);

        /**
         * 観測地点の解決を開始したことを通知します.<br/>
         */
        void onStartResolveObservationSiteLocation();

        /**
         * 観測地点の解決を停止したことを通知します.<br/>
         */
        void onStopResolveObservationSiteLocation();

        /**
         * 位置情報プロバイダを利用できないことを通知します.<br/>
         */
        void onNotAvailableLocationProvider();
    }
}