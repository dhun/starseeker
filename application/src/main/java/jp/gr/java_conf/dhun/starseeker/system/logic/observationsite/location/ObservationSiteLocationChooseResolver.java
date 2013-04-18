/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import android.content.Context;

/**
 * 観測地点をリスト選択で指定する観測地点の位置のリゾルバ.<br/>
 * 
 * @author jun
 * 
 */
public class ObservationSiteLocationChooseResolver implements IObservationSiteLocationResolver {
    /** 観測地点の位置を解決できたことの通知を受け取るリスナ */
    private ObservationSiteLocationResolverListener onResolveObservationSiteLocationListener;

    private int index;
    private ObservationSiteLocation location;

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public ObservationSiteLocationChooseResolver(Context context) {
    }

    public void setObservationSiteLocation(ObservationSiteLocation location) {
        this.location = location;
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public void resume() {
        onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(index, location);
    }

    @Override
    public void pause() {
    }

    @Override
    public void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
