package jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;

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

    void setIndex(int index);

    /**
     * 観測地点リゾルバのリスナ.<br/>
     */
    interface ObservationSiteLocationResolverListener {

        /**
         * 観測地点を解決できたことを通知します.<br/>
         * 
         * @param location 観測地点
         */
        void onResolveObservationSiteLocation(int index, ObservationSiteLocation location);

        /**
         * 観測地点の解決を開始したことを通知します.<br/>
         */
        void onStartResolveObservationSiteLocation(int index);

        /**
         * 観測地点の解決を停止したことを通知します.<br/>
         */
        void onStopResolveObservationSiteLocation(int index);

        /**
         * 位置情報プロバイダを利用できないことを通知します.<br/>
         */
        void onNotAvailableLocationProvider(int index);
    }

    /**
     * 観測地点リゾルバの種別.<br/>
     */
    public enum ObservationSiteLocationResolverType {
        GPS(ObservationSiteLocation.ID_GPS),            // GPSを利用した観測地点リゾルバ
        NETWORK(ObservationSiteLocation.ID_NETWORK),    // NETWORKを利用した観測地点リゾルバ
        LIST_CHOOSE(null);                              // 一覧から選択する観測地点リゾルバ

        private final Integer observationSiteLocationId;

        private ObservationSiteLocationResolverType(Integer id) {
            this.observationSiteLocationId = id;
        }

        /**
         * リゾルバ種別に対する観測地点IDを取得します
         */
        public Integer getObservationSiteLocationId() {
            return observationSiteLocationId;
        }
    }
}