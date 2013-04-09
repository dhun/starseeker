/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarEntity;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 星.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author jun
 * 
 */
public class Star {

    private final StarEntity starEntity;    // 星エンティティ

    private boolean locaated;   // 配置済であるかどうか
    private float azimuth;      // 方位(A). -180 <= 0 <= +180. 数値表現ではないためStarLocationUtil.convertAngleFloatToString()をしてはいけない
    private float altitude;     // 高度(h). - 90 <= 0 <= + 90. 数値表現ではないためStarLocationUtil.convertAngleFloatToString()をしてはいけない

    private String displayText; // ディスプレイに出力するテキスト
    private float displayX;     // ディスプレイのX座標
    private float displayY;     // ディスプレイのY座標

    /**
     * コンストラクタ.<br/>
     * 
     * @param rightAscension 赤経(α)の文字列表現
     * @param declination 赤緯(δ)の文字列表現
     */
    @Deprecated
    public Star(String rightAscension, String declination) {
        this( //
                StarLocationUtil.convertHourStringToFloat(rightAscension), //
                StarLocationUtil.convertAngleStringToFloat(declination));
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param rightAscension 赤経(α)の数値表現
     * @param declination 赤緯(δ)の数値表現
     */
    @Deprecated
    public Star(float rightAscension, float declination) {
        this(new StarEntity());
        this.starEntity.setRightAscension(rightAscension);
        this.starEntity.setDeclination(declination);
        this.starEntity.setMagnitude(StarEntity.MAGGNITUDE_UNKNOWN_VALUE);
        this.starEntity.setName(null);
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param starEntity 星のエンティティ
     */
    public Star(StarEntity starEntity) {
        this.starEntity = starEntity;
        this.locaated = false;
    }

    /**
     * 星を再配置します.<br/>
     * 
     * @param azimuth 方位(A)の数値表現
     * @param altitude 高度(h)の数値表現
     */
    public void relocate(float azimuth, float altitude) {
        this.locaated = true;
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     */
    public float getRightAscension() {
        return starEntity.getRightAscension();
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     */
    public float getDeclination() {
        return starEntity.getDeclination();
    }

    /**
     * 名前を取得します.<br/>
     */
    public String getName() {
        return starEntity.getName();
    }

    /**
     * 名前を設定します.<br/>
     */
    @Deprecated
    public void setName(String name) {
        starEntity.setName(name);
    }

    /**
     * 備考を取得します.<br/>
     */
    public String getMemo() {
        return starEntity.getMemo();
    }

    /**
     * 備考を設定します.<br/>
     */
    @Deprecated
    public void setMemo(String memo) {
        starEntity.setName(memo);
    }

    /**
     * 等級を取得します.<br/>
     */
    public float getMagnitude() {
        return starEntity.getMagnitude();
    }

    /**
     * 等級を設定します.<br/>
     */
    @Deprecated
    public void setMagnitude(float magnitude) {
        starEntity.setMagnitude(magnitude);
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
    public float getAzimuth() {
        assert (isLocated());
        return azimuth;
    }

    /**
     * 高度(h)の数値表現を取得します.<br/>
     */
    public float getAltitude() {
        assert (isLocated());
        return altitude;
    }

    /**
     * ディスプレイに出力するテキストを取得します.<br/>
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * ディスプレイに出力するテキストを設定します.<br/>
     */
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    /**
     * ディスプレイのX座標を取得します.<br/>
     */
    public float getDisplayX() {
        return displayX;
    }

    /**
     * ディスプレイのX座標を設定します.<br/>
     */
    public void setDisplayX(float displayX) {
        this.displayX = displayX;
    }

    /**
     * ディスプレイのY座標を取得します.<br/>
     */
    public float getDisplayY() {
        return displayY;
    }

    /**
     * ディスプレイのY座標を設定します.<br/>
     */
    public void setDisplayY(float displayY) {
        this.displayY = displayY;
    }

    @Override
    public String toString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 方位(A)=[%s], 高度(h)=[%s], 等級=[%3.1f], 名前=[%s]" //
                , StarLocationUtil.convertAngleFloatToString(getRightAscension())  //
                , StarLocationUtil.convertAngleFloatToString(getDeclination())     //
                , StarLocationUtil.convertAngleFloatToString(getAzimuth())         //
                , StarLocationUtil.convertAngleFloatToString(getAltitude())        //
                , getMagnitude() //
                , getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Float.floatToIntBits(getRightAscension());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Float.floatToIntBits(getDeclination());
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
        if (Float.floatToIntBits(getRightAscension()) != Float.floatToIntBits(other.getRightAscension())) {
            return false;
        }
        if (Float.floatToIntBits(getDeclination()) != Float.floatToIntBits(other.getDeclination())) {
            return false;
        }
        return true;
    }
}
