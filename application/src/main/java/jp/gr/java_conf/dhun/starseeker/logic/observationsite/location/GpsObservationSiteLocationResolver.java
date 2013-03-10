/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.observationsite.location;

import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
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
public class GpsObservationSiteLocationResolver implements IObservationSiteLocationResolver, LocationListener {

    private final LocationManager locationManager;

    private ObservationSiteLocationResolverListener onResolveObservationSiteLocationListener;

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public GpsObservationSiteLocationResolver(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public void resume() {
        if (null != locationManager) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                LogUtils.i(GpsObservationSiteLocationResolver.class, "GPS_PROVIDERによる位置情報の取得を開始します.");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

                if (null != onResolveObservationSiteLocationListener) {
                    onResolveObservationSiteLocationListener.onStartResolveObservationSiteLocation();
                }
                return;
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                LogUtils.i(GpsObservationSiteLocationResolver.class, "NETWORK_PROVIDERによる位置情報の取得を開始します.");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                if (null != onResolveObservationSiteLocationListener) {
                    onResolveObservationSiteLocationListener.onStartResolveObservationSiteLocation();
                }
                return;
            }

            LogUtils.i(GpsObservationSiteLocationResolver.class, "位置情報プロバイダを利用できません..");
            onResolveObservationSiteLocationListener.onNotAvailableLocationProvider();
        }
    }

    @Override
    public void pause() {
        if (null != locationManager) {
            LogUtils.i(GpsObservationSiteLocationResolver.class, "位置情報プロバイダによる位置情報の取得を停止します.");
            locationManager.removeUpdates(this);

            if (null != onResolveObservationSiteLocationListener) {
                onResolveObservationSiteLocationListener.onStopResolveObservationSiteLocation();
            }
        }
    }

    @Override
    public void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }

    @Override
    public void setObservationSiteLocation(ObservationSiteLocation location) {
        throw new UnsupportedOperationException("GpsObservationSiteLocationResolverではこのメソッドをサポートしていません.");
    }

    // ********************************************************************************
    // LocationListener

    @Override
    public void onLocationChanged(Location location) {
        LogUtils.i(getClass(), "位置情報プロバイダにより位置情報を取得しました.");

        if (null != onResolveObservationSiteLocationListener) {
            ObservationSiteLocation result = new ObservationSiteLocation( //
                    location.getLatitude(),     // 緯度
                    location.getLongitude(),    // 経度
                    location.getAltitude(),     // 高度
                    "現在地"); // XXX strings.xml
            onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(result);
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
