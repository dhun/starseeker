/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import java.util.TimeZone;

import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver.ObservationSiteLocationResolverType;

/**
 * 観測地点の位置.<br/>
 * 精度はLocationListenerから取得できる値と同じdouble.
 * 
 * @author jun
 * 
 */
public class ObservationSiteLocation {

    public static final int ID_GPS = -1;     // GPSを利用した観測地点のID
    public static final int ID_NETWORK = -2; // NETWORKを利用した観測地点のID

    private Integer id;         // ID
    private ObservationSiteLocationResolverType resolverType;

    private double latitude;    // 緯度
    private double longitude;   // 経度
    private double altitude;    // 高度

    private TimeZone timeZone;  // タイムゾーン
    private String name;        // 観測地点の名前

    /**
     * コンストラクタ.<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     * @param altitude 高度
     */
    public ObservationSiteLocation(double latitude, double longitude) {
        this(latitude, longitude, 0, "");
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     * @param altitude 高度
     */
    public ObservationSiteLocation(double latitude, double longitude, double altitude) {
        this(latitude, longitude, altitude, "");
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     * @param name 名前
     */
    public ObservationSiteLocation(double latitude, double longitude, String name) {
        this(latitude, longitude, 0, name);
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     * @param altitude 高度
     * @param name 名前
     */
    public ObservationSiteLocation(double latitude, double longitude, double altitude, String name) {
        setLocation(latitude, longitude, altitude);
        setName(name);
    }

    /**
     * 位置を設定します.<br/>
     * 高度は変更しません.
     * 
     * @param latitude 緯度
     * @param longitude 経度
     */
    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 位置を設定します.<br/>
     * 
     * @param latitude 緯度
     * @param longitude 経度
     * @param altitude 高度
     */
    public void setLocation(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * IDを取得します.<br>
     */
    public Integer getId() {
        return id;
    }

    /**
     * IDを取得します.<br>
     */
    public void setId(Integer id) {
        this.id = id;
        ;
    }

    /**
     * 観測地点リゾルバの種別を取得します.<br>
     */
    public ObservationSiteLocationResolverType getResolverType() {
        return resolverType;
    }

    /**
     * 観測地点リゾルバの種別を取得します.<br>
     */
    public void setResolverType(ObservationSiteLocationResolverType resolverType) {
        this.resolverType = resolverType;
        ;
    }

    /**
     * 緯度を取得します.<br>
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 経度を取得します.<br>
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * 高度を取得します.<br>
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * タイムゾーンを取得します.<br>
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * タイムゾーンを設定します.<br>
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * 名前を取得します.<br>
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を設定します.<br>
     */
    public void setName(String name) {
        this.name = (null == name) ? "" : name;
    }
}
