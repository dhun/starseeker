/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 観測地点の位置.<br/>
 * 
 * @author jun
 * 
 */
public class ObservationSiteLocation {

    // 初期値は京都
    private static final double DEFAULT_LATITUDE = StarLocationUtil.convertAngleStringToDouble("+35°01'");
    private static final double DEFAULT_LONGITUDE = StarLocationUtil.convertAngleStringToDouble("+135°46'");

    private double latitude;  // 緯度
    private double longitude; // 経度

    /**
     * コンストラクタ.<br/>
     * 緯度と軽度は初期値となります.<br/>
     */
    public ObservationSiteLocation() {
        this(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     */
    public ObservationSiteLocation(double latitude, double longitude) {
        setLocation(latitude, longitude);
    }

    /**
     * 位置を設定します..<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     */
    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 緯度を取得します.<br>
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 緯度を取得します.<br>
     */
    public double getLongitude() {
        return longitude;
    }

}
