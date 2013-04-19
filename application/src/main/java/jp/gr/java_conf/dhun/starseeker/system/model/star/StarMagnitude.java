/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model.star;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.util.MathUtils;

/**
 * 星の等級/<br/>
 * 
 * @author jun
 * 
 */
public class StarMagnitude {

    // private static final float MAX_MAGNITUDE = +5.0f;// システムで扱う等級の上限値
    private static final float MIN_MAGNITUDE = -1.5f;   // システムで扱う等級の下限値
    private static final float MAGNITUDE_UNIT = 0.5f;   // システムで扱う等級の刻み幅

    private final float exactMagnitude;     // 正確な等級
    private final float roughMagnitude;     // おおよその等級. 0.5等級単位に切り捨てています

    private DecimalFormat magnitudeFormat;  // 等級のフォーマッタ

    /**
     * {@link StarMagnitude#MIN_MAGNITUDE}を下限値、指定された等級を上限値とした、おおよその等級の配列を返却します
     * 
     * @param upperMagnitude 等級の上限値
     * @return {@link StarMagnitude#MAGNITUDE_UNIT} 刻みのおおよその等級の配列
     */
    public static StarMagnitude[] listRoughMagnitudes(float upperMagnitude) {
        List<StarMagnitude> results = new ArrayList<StarMagnitude>();
        for (float magnitude = MIN_MAGNITUDE; magnitude <= upperMagnitude; magnitude += MAGNITUDE_UNIT) {
            results.add(new StarMagnitude(magnitude));
        }
        return results.toArray(new StarMagnitude[0]);
    }

    /**
     * コンストラクタ
     * 
     * @param exactMagnitude 正確な等級
     */
    public StarMagnitude(float exactMagnitude) {
        this.exactMagnitude = exactMagnitude;
        this.roughMagnitude = (float) (MathUtils.floor(exactMagnitude / MAGNITUDE_UNIT) * MAGNITUDE_UNIT);
    }

    /**
     * 正確な等級を取得します
     */
    public float getExactMagnitude() {
        return exactMagnitude;
    }

    /**
     * おおよその等級を取得します
     */
    public float getRoughMagnitude() {
        return roughMagnitude;
    }

    /**
     * このインスタンスで扱う等級の下限値
     * 
     * @return
     */
    public float getLowerMagnitude() {
        return roughMagnitude;
    }

    /**
     * このインスタンスで扱う等級の上限値
     * 
     * @return
     */
    public float getUpperMagnitude() {
        return roughMagnitude + MAGNITUDE_UNIT;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(roughMagnitude);
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
        if (!(obj instanceof StarMagnitude)) {
            return false;
        }
        StarMagnitude other = (StarMagnitude) obj;
        if (Float.floatToIntBits(roughMagnitude) != Float.floatToIntBits(other.roughMagnitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (magnitudeFormat == null) {
            magnitudeFormat = new DecimalFormat("0.00");
            magnitudeFormat.setPositivePrefix("+");
            magnitudeFormat.setNegativePrefix("-");
        }

        return String.format("[roughMagnitude=%s, range=[%s ... %s], exactMagnitude=%s" //
                , magnitudeFormat.format(roughMagnitude)        //
                , magnitudeFormat.format(getLowerMagnitude())   //
                , magnitudeFormat.format(getUpperMagnitude())   //
                , magnitudeFormat.format(exactMagnitude));      //
    }

}
