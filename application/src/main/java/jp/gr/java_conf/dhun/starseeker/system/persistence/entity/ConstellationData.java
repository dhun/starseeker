/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;
import android.annotation.SuppressLint;

/**
 * 星座データのエンティティ.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author jun
 * 
 */
public class ConstellationData {

    private String constellationCode;   // 星座コード(略符)
    private String constellationName;   // 星座名(学名)
    private String japaneseName;        // 星座名(日本語)
    private float rightAscension;       // 赤経(α)の数値表現
    private float declination;          // 赤緯(δ)の数値表現

    /**
     * デフォルト・コンストラクタ
     */
    public ConstellationData() {
    }

    /**
     * 星座コード(略符)を取得します.<br/>
     */
    public String getConstellationCode() {
        return constellationCode;
    }

    /**
     * 星座コード(略符)を設定します.<br/>
     */
    public void setConstellationCode(String constellationCode) {
        this.constellationCode = constellationCode;
    }

    /**
     * 星座名(学名)を取得します.<br/>
     */
    public String getConstellationName() {
        return constellationName;
    }

    /**
     * 星座名(学名)を設定します.<br/>
     */
    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    /**
     * 星座名(日本語)を取得します.<br/>
     */
    public String getJapaneseName() {
        return japaneseName;
    }

    /**
     * 星座名(日本語)を設定します.<br/>
     */
    public void setJapaneseName(String japaneseName) {
        this.japaneseName = japaneseName;
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

    @Override
    @SuppressLint("DefaultLocale")
    public String toString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 名前=[%s]" //
                , StarLocationUtil.convertAngleFloatToString(getRightAscension()) //
                , StarLocationUtil.convertAngleFloatToString(getDeclination())    //
                , getJapaneseName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((constellationCode == null) ? 0 : constellationCode.hashCode());
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
        if (!(obj instanceof ConstellationData)) {
            return false;
        }
        ConstellationData other = (ConstellationData) obj;
        if (constellationCode == null) {
            if (other.constellationCode != null) {
                return false;
            }
        } else if (!constellationCode.equals(other.constellationCode)) {
            return false;
        }
        return true;
    }

    public static final String TABLE_NAME = "constellation_data";

    public static class FieldNames {
        public static final String CONSTELLATION_CODE = "constellation_code";
        public static final String CONSTELLATION_NAME = "constellation_name";
        public static final String JAPANESE_NAME = "japanese_name";
        public static final String RIGHT_ASCENSION = "right_ascension";
        public static final String DECLINATION = "declination";

        public static final String[] ALL_COLUMNS = { CONSTELLATION_CODE, CONSTELLATION_NAME, JAPANESE_NAME, RIGHT_ASCENSION, DECLINATION };
    }
}
