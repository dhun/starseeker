/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 星.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author jun
 * 
 */
public class Star {

    private final StarData starData;    // 星データ

    private boolean hasRelatedConstellationCode;
    private Set<String> relatedConstellationCodeSet;   // 関連する星座コードの集合

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
        this(new StarData());
        this.starData.setRightAscension(rightAscension);
        this.starData.setDeclination(declination);
        this.starData.setMagnitude(StarData.MAGGNITUDE_UNKNOWN_VALUE);
        this.starData.setName(null);
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param starData 星データ
     */
    public Star(StarData starData) {
        this.starData = starData;
        this.relatedConstellationCodeSet = Collections.emptySet();
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
     * 関連する星座コードを追加します.<br/>
     * 
     * @param code 星座コード(略符)
     */
    public void addRelatedConstellationCode(String code) {
        if (hasRelatedConstellationCode == false) {
            hasRelatedConstellationCode = true;
            relatedConstellationCodeSet = new HashSet<String>();
        }
        relatedConstellationCodeSet.add(code);
    }

    /**
     * 関連する星座コードの集合を取得します.<br/>
     * 
     * @return 星座コード(略符)の集合
     */
    public Set<String> getRelatedConstellationCodeSet() {
        return Collections.unmodifiableSet(relatedConstellationCodeSet);
    }

    /**
     * HIP番号を取得します.<br/>
     */
    public Integer getHipNumber() {
        return starData.getHipNumber();
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     */
    public float getRightAscension() {
        return starData.getRightAscension();
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     */
    public float getDeclination() {
        return starData.getDeclination();
    }

    /**
     * 名前を取得します.<br/>
     */
    public String getName() {
        return starData.getName();
    }

    /**
     * 名前を設定します.<br/>
     */
    @Deprecated
    public void setName(String name) {
        starData.setName(name);
    }

    /**
     * 備考を取得します.<br/>
     */
    public String getMemo() {
        return starData.getMemo();
    }

    /**
     * 備考を設定します.<br/>
     */
    @Deprecated
    public void setMemo(String memo) {
        starData.setName(memo);
    }

    /**
     * 等級を取得します.<br/>
     */
    public float getMagnitude() {
        return starData.getMagnitude();
    }

    /**
     * 等級を設定します.<br/>
     */
    @Deprecated
    public void setMagnitude(float magnitude) {
        starData.setMagnitude(magnitude);
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
