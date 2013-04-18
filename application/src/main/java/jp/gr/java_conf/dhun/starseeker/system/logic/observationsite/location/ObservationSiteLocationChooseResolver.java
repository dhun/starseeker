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

    private ObservationSiteLocation location;
    private Object tag;

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
        onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(this, location);
    }

    @Override
    public void pause() {
    }

    @Override
    public void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }

    @Override
    public Object getTag() {
        return tag;

    }

    @Override
    public void setTag(Object tag) {
        this.tag = tag;
    }
}
