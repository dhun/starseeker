/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 星.<br/>
 * 
 * @author jun
 * 
 */
public class Star {

    private final double rightAscension; // 赤経(α)の数値表現
    private final double declination;    // 赤緯(δ)の数値表現

    private double azimuth;  // 方位(A)の数値表現
    private double altitude; // 高度(h)の数値表現

    private String name; // 名前

    /**
     * コンストラクタ.<br/>
     * 
     * @param rightAscension 赤経(α)の文字列表現
     * @param declination 赤緯(δ)の文字列表現
     */
    public Star(String rightAscension, String declination) {
        this( //
                StarLocationUtil.convertHourStringToDouble(rightAscension), //
                StarLocationUtil.convertAngleStringToDouble(declination));
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param rightAscension 赤経(α)の数値表現
     * @param declination 赤緯(δ)の数値表現
     */
    public Star(double rightAscension, double declination) {
        this.rightAscension = rightAscension;
        this.declination = declination;
    }

    /**
     * 星を再配置します.<br/>
     * 
     * @param azimuth 方位(A)の数値表現
     * @param altitude 高度(h)の数値表現
     */
    public void relocate(double azimuth, double altitude) {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    /**
     * 名前を取得します.<br/>
     * 
     * @return 名前.
     */
    public String getName() {
        return name;
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     * 
     * @return 赤経.
     */
    public double getRightAscension() {
        return rightAscension;
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     * 
     * @return 赤緯.
     */
    public double getDeclination() {
        return declination;
    }

    /**
     * 方位(A)の数値表現を取得します.<br/>
     * 
     * @return 方位.
     */
    public double getAzimuth() {
        return azimuth;
    }

    /**
     * 高度(h)の数値表現を取得します.<br/>
     * 
     * @return 高度.
     */
    public double getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 方位(A)=[%s], 高度(h)=[%s], 名前=[%s]" //
                , getRightAscension() //
                , getDeclination()    //
                , getAzimuth()        //
                , getAltitude()       //
                , getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(rightAscension);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(declination);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Star)) {
            return false;
        }
        Star other = (Star) obj;
        if (Double.doubleToLongBits(rightAscension) != Double.doubleToLongBits(other.rightAscension)) {
            return false;
        }
        if (Double.doubleToLongBits(declination) != Double.doubleToLongBits(other.declination)) {
            return false;
        }
        return true;
    }
}
