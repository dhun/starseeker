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

    private boolean locaated;   // 配置済であるかどうか
    private double azimuth;     // 方位(A)の数値表現
    private double altitude;    // 高度(h)の数値表現

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
        this.locaated = false;
    }

    /**
     * 星を再配置します.<br/>
     * 
     * @param azimuth 方位(A)の数値表現
     * @param altitude 高度(h)の数値表現
     */
    public void relocate(double azimuth, double altitude) {
        this.locaated = true;
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     */
    public double getRightAscension() {
        return rightAscension;
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     */
    public double getDeclination() {
        return declination;
    }

    /**
     * 星が配置済であるかどうかを取得します.<br/>
     */
    public boolean isLocated() {
        return locaated;
    }

    /**
     * 方位(A)の数値表現を取得します.<br/>
     */
    public double getAzimuth() {
        assert (isLocated());
        return azimuth;
    }

    /**
     * 高度(h)の数値表現を取得します.<br/>
     */
    public double getAltitude() {
        assert (isLocated());
        return altitude;
    }

    /**
     * 名前を取得します.<br/>
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を設定します.<br/>
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toLongString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 方位(A)=[%s], 高度(h)=[%s], 名前=[%s]" //
                , StarLocationUtil.convertAngleDoubleToString(getRightAscension())  //
                , StarLocationUtil.convertAngleDoubleToString(getDeclination())     //
                , StarLocationUtil.convertAngleDoubleToString(getAzimuth())         //
                , StarLocationUtil.convertAngleDoubleToString(getAltitude())        //
                , getName());
    }

    @Override
    public String toString() {
        return String.format("方位(A)=[%s], 高度(h)=[%s], 名前=[%s]" //
                , StarLocationUtil.convertAngleDoubleToString(getAzimuth())         //
                , StarLocationUtil.convertAngleDoubleToString(getAltitude())        //
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
