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
public class ObservationSiteLocationChooseResolver extends AbstractObservationSiteLocationResolver {

    private ObservationSiteLocation location;

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public ObservationSiteLocationChooseResolver(Context context) {
        super(context, ObservationSiteLocationResolverType.LIST_CHOOSE);
    }

    public void setObservationSiteLocation(ObservationSiteLocation location) {
        this.location = location;
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public Integer getObservationSiteLocationId() {
        return location.getId();
    }

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
}
