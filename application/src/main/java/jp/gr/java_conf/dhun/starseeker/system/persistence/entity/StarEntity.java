/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;
import android.annotation.SuppressLint;

/**
 * 星のエンティティ.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author jun
 * 
 */
public class StarEntity {

    public static final float MAGGNITUDE_UNKNOWN_VALUE = 7.0f;

    private Integer hipNumber;      // HIP番号
    private float rightAscension;   // 赤経(α)の数値表現
    private float declination;      // 赤緯(δ)の数値表現
    private float magnitude;        // 等級
    private String name;            // 名前(省略可)
    private String memo;            // 備考(省略可)

    /**
     * デフォルト・コンストラクタ
     */
    public StarEntity() {
    }

    /**
     * IDを取得します.<br/>
     */
    public Integer getHipNumber() {
        return hipNumber;
    }

    /**
     * IDを設定します.<br/>
     */
    public void setHipNumber(Integer hipNumber) {
        this.hipNumber = hipNumber;
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     */
    public float getRightAscension() {
        return rightAscension;
    }

    /**
     * 赤経(α)の数値表現を設定します.<br/>
     */
    public void setRightAscension(float rightAscension) {
        this.rightAscension = rightAscension;
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     */
    public float getDeclination() {
        return declination;
    }

    /**
     * 赤緯(δ)の数値表現を設定します.<br/>
     */
    public void setDeclination(float declination) {
        this.declination = declination;
    }

    /**
     * 等級を取得します.<br/>
     */
    public float getMagnitude() {
        return magnitude;
    }

    /**
     * 等級を設定します.<br/>
     */
    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
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

    /**
     * メモを取得します.<br/>
     */
    public String getMemo() {
        return memo;
    }

    /**
     * メモを設定します.<br/>
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    @SuppressLint("DefaultLocale")
    public String toString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 等級=[%6.2f], 名前=[%s]" //
                , StarLocationUtil.convertAngleFloatToString(getRightAscension()) //
                , StarLocationUtil.convertAngleFloatToString(getDeclination())    //
                , magnitude, getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Float.floatToIntBits(rightAscension);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Float.floatToIntBits(declination);
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
        if (!(obj instanceof StarEntity)) {
            return false;
        }
        StarEntity other = (StarEntity) obj;
        if (Float.floatToIntBits(rightAscension) != Float.floatToIntBits(other.getRightAscension())) {
            return false;
        }
        if (Float.floatToIntBits(declination) != Float.floatToIntBits(other.getDeclination())) {
            return false;
        }
        return true;
    }

    public static final String TABLE_NAME = "star_data";

    public static class FieldNames {
        public static final String HIP_NUMBER = "hip_num";
        public static final String RIGHT_ASCENSION = "right_ascension";
        public static final String DECLINATION = "declination";
        public static final String MAGNITUDE = "magnitude";
        public static final String NAME = "japanese_name";
        public static final String MEMO = "memo";

        public static final String[] ALL_COLUMNS = { HIP_NUMBER, RIGHT_ASCENSION, DECLINATION, MAGNITUDE, NAME, MEMO };
    }
}
