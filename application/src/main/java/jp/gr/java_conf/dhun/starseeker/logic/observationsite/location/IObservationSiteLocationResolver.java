package jp.gr.java_conf.dhun.starseeker.logic.observationsite.location;

import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;

/**
 * 観測地点の位置のリゾルバのインターフェース.<br/>
 * 
 * @author jun
 * 
 */
public interface IObservationSiteLocationResolver {

    /**
     * リゾルバを利用できる状態にします.<br/>
     * Activity.onResumeなどで呼び出してください.
     */
    void prepare();

    /**
     * リゾルバを停止します.<br/>
     * Activity.onPauseなどで呼び出してください.
     */
    void pause();

    /**
     * 観測地点の位置を解決できたことの通知を受け取るリスナを設定します.<br/>
     */
    void setOnResolveObservationSiteLocationListener(OnResolveObservationSiteLocationListener listener);

    /**
     * 観測地点の位置を設定します.<br/>
     */
    void setObservationSiteLocation(ObservationSiteLocation location);

    /**
     * 観測地点の位置を解決できたことの通知を受け取るリスナ.<br/>
     */
    interface OnResolveObservationSiteLocationListener {

        /**
         * 観測地点の位置を解決できたこと通知します.<br/>
         * 
         * @param location 観測地点の位置
         */
        void onResolveObservationSiteLocation(ObservationSiteLocation location);
    }
}