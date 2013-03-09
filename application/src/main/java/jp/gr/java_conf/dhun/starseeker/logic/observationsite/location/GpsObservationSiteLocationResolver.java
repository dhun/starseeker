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
    private OnResolveObservationSiteLocationListener onResolveObservationSiteLocationListener;

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
    public void prepare() {
        if (null != locationManager) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this); // TODO パラメータを確認する
        }
    }

    @Override
    public void pause() {
        if (null != locationManager) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void setOnResolveObservationSiteLocationListener(OnResolveObservationSiteLocationListener listener) {
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
        if (null != onResolveObservationSiteLocationListener) {
            ObservationSiteLocation result = new ObservationSiteLocation( //
                    location.getAltitude(),     //
                    location.getLongitude(),    //
                    location.getLatitude(),    //
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
        switch (status) {
        case LocationProvider.AVAILABLE:
            LogUtils.v(getClass(), "onStatusChanged;status=LocationProvider.AVAILABLE");
            break;
        case LocationProvider.OUT_OF_SERVICE:
            LogUtils.v(getClass(), "onStatusChanged;status=LocationProvider.OUT_OF_SERVICE");
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
            LogUtils.v(getClass(), "onStatusChanged;status=LocationProvider.TEMPORARILY_UNAVAILABLE");
            break;
        }
    }
}
