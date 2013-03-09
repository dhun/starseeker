/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

/**
 * 座標
 * 
 * @author jun
 */
public class BAK_Coordinates {

    private float longitude; // 経度(λ). 東経を - 西経を + として、その範囲は -180 から +180
    private float latitude;  // 緯度(ψ). 北緯を + 南緯を - として、その範囲は -90 から +90

    /**
     * 経度(λ)を取得します.<br/>
     * 東経を - 西経を + として、その範囲は -180 から +180 になります.
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * 経度(λ)を設定します.<br/>
     * 東経を - 西経を + として、その範囲は -180 から +180 になります.
     * 
     * @param longitude 経度(λ)
     */
    public void setLongitude(float longitude) {
        if (longitude < -180 || +180 < longitude) {
            throw new IllegalArgumentException("経度の範囲は -180 から +180 です. value=" + longitude);
        }
        this.longitude = longitude;
    }

    /**
     * 緯度(ψ)を取得します.<br/>
     * 北緯を + 南緯を - として、その範囲は -90 から +90 になります.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * 緯度(ψ)を設定します.<br/>
     * 北緯を + 南緯を - として、その範囲は -90 から +90 になります.
     * 
     * @param latitude 緯度(ψ)
     */
    public void setLatitude(float latitude) {
        if (latitude < -90 || +90 < latitude) {
            throw new IllegalArgumentException("緯度の範囲は -90 から +90 です. value=" + latitude);
        }
        this.latitude = latitude;
    }

    public BAK_Coordinates offset(float longitude, float latitude) {
        BAK_Coordinates result = new BAK_Coordinates();

        return result;
    }
}
