/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location;

import java.util.TimeZone;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

/**
 * GPSを利用した観測地点の位置のリゾルバ.<br/>
 * 
 * @author jun
 * 
 */
public class ObservationSiteLocationGpsResolver extends AbstractObservationSiteLocationResolver implements LocationListener {

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     * @param locationProviderType 位置プロバイダの種類
     */
    public ObservationSiteLocationGpsResolver(Context context, ObservationSiteLocationResolverType locationProviderType) {
        super(context, locationProviderType);
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public Integer getObservationSiteLocationId() {
        return locationProviderType.getObservationSiteLocationId();
    }

    @Override
    public void resume() {
        boolean debug = true;   // FIXME 後で消すぞ
        if (debug) {
            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
            }
            Location location = new Location("mock");
            location.setAltitude(0.0);
            location.setLatitude(50.0);
            location.setLongitude(100.0);
            onLocationChanged(location);
            return;
        }

        if (null == locationManager) {
            throw new IllegalStateException("locationManager must be null.");
        }

        String providerName;
        switch (locationProviderType) {
        case GPS:
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                providerName = LocationManager.GPS_PROVIDER;
                break;
            }
            // break through;

        case NETWORK:
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                providerName = LocationManager.NETWORK_PROVIDER;
                break;
            }
            // break through;

        default:
            LogUtils.i(ObservationSiteLocationGpsResolver.class, "位置情報プロバイダを利用できません..");
            if (onResolveObservationSiteLocationListener != null) {
                onResolveObservationSiteLocationListener.onNotAvailableLocationProvider(this);
            }
            return;
        }

        LogUtils.i(ObservationSiteLocationGpsResolver.class, "[" + providerName + "]による位置情報の取得を開始します.");
        locationManager.requestLocationUpdates(providerName, 1000/* msec */, 1000/* meter */, this);
    }

    @Override
    public void pause() {
        if (null == locationManager) {
            throw new IllegalStateException("locationManager must be null.");
        }

        LogUtils.i(ObservationSiteLocationGpsResolver.class, "位置情報プロバイダによる位置情報の取得を停止します.");
        locationManager.removeUpdates(this);
    }

    // ********************************************************************************
    // LocationListener

    @Override
    public void onLocationChanged(Location location) {
        LogUtils.i(getClass(), String.format("位置情報プロバイダにより位置情報を取得しました. 緯度=[%6.2f], 経度=[%6.2f], 高度=[%6.2f], 精度=[%6.2f]" //
                , location.getLatitude() //
                , location.getLongitude() //
                , location.getAltitude() //
                , location.getAccuracy()));

        if (onResolveObservationSiteLocationListener != null) {
            ObservationSiteLocation result = new ObservationSiteLocation( //
                    location.getLatitude(),     // 緯度
                    location.getLongitude(),    // 経度
                    location.getAltitude());    // 高度
            result.setId(locationProviderType.getObservationSiteLocationId());
            result.setName("現在地"); // XXX strings.xml
            result.setTimeZone(TimeZone.getDefault());
            onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(this, result);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        LogUtils.v(getClass(), "onProviderEnabled:");
    }

    @Override
    public void onProviderDisabled(String provider) {
        LogUtils.v(getClass(), "onProviderDisabled:");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String statusName = "unknown";
        switch (status) {
        case LocationProvider.AVAILABLE:
            statusName = "LocationProvider.AVAILABLE";
            break;
        case LocationProvider.OUT_OF_SERVICE:
            statusName = "LocationProvider.OUT_OF_SERVICE";
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
            statusName = "LocationProvider.TEMPORARILY_UNAVAILABLE";
            break;
        }
        LogUtils.v(getClass(), "onStatusChanged:provider=" + provider + ", status=" + statusName);
    }
}
