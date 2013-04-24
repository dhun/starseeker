/*
 * Copyright (c) 2010 ArsNova Ltd.
 */
package jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import android.content.Context;
import android.location.LocationManager;

/**
 * 観測地点リゾルバの抽象実装<br>
 * 
 * @author jun
 */
public abstract class AbstractObservationSiteLocationResolver implements IObservationSiteLocationResolver {

    public static IObservationSiteLocationResolver newResolver(Context context, ObservationSiteLocation location) throws IllegalStateException {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        switch (location.getResolverType()) {
        case GPS:
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return new ObservationSiteLocationGpsResolver(context, ObservationSiteLocationResolverType.GPS);
            } else {
                throw new IllegalStateException("GPSは現在利用できません.\nシステム設定を変更してください."); // XXX strings.xml
            }

        case NETWORK:
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                return new ObservationSiteLocationGpsResolver(context, ObservationSiteLocationResolverType.NETWORK);
            } else {
                throw new IllegalStateException("簡易GPS(ネットワーク位置プロバイダ)は現在利用できません.\nシステム設定を変更してください."); // XXX strings.xml
            }

        default:
            ObservationSiteLocationChooseResolver chooseResolver = new ObservationSiteLocationChooseResolver(context);
            chooseResolver.setObservationSiteLocation(location);
            return chooseResolver;
        }

    }

    protected final LocationManager locationManager;
    protected final ObservationSiteLocationResolverType locationProviderType;
    protected ObservationSiteLocationResolverListener onResolveObservationSiteLocationListener;

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     * @param locationProviderType 位置プロバイダの種類
     */
    public AbstractObservationSiteLocationResolver(Context context, ObservationSiteLocationResolverType locationProviderType) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locationProviderType = locationProviderType;
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }
}
