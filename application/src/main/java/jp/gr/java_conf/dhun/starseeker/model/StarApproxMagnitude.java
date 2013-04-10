/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.util.MathUtils;

/**
 * 星のおおよその等級/<br/>
 * 
 * @author jun
 * 
 */
public class StarApproxMagnitude {

    private static final float MIN_MAGNITUDE = -1.5f;   // システムで扱う等級の下限値
    private static final float MAX_MAGNITUDE = +5.0f;   // システムで扱う等級の上限値
    private static final float MAGNITUDE_UNIT = 0.5f;   // システムで扱う等級の刻み幅

    private final float magnitude;          // 等級
    private final float approxMagnitude;    // おおよその等級. 0.5等級単位に切り捨てています

    /**
     * {@link StarApproxMagnitude#MIN_MAGNITUDE}を下限値、指定された等級を上限値とした、おおよその等級の配列を返却します
     * 
     * @param upperMagnitude 等級の上限値
     * @return {@link StarApproxMagnitude#MAGNITUDE_UNIT} 刻みのおおよその等級の配列
     */
    public static StarApproxMagnitude[] listApproxMagnitudes(float upperMagnitude) {
        List<StarApproxMagnitude> results = new ArrayList<StarApproxMagnitude>();
        for (float magnitude = MIN_MAGNITUDE; magnitude <= upperMagnitude; magnitude += MAGNITUDE_UNIT) {
            results.add(new StarApproxMagnitude(magnitude));
        }
        return results.toArray(new StarApproxMagnitude[0]);
    }

    /**
     * コンストラクタ
     * 
     * @param magnitude 等級
     */
    public StarApproxMagnitude(float magnitude) {
        this.magnitude = magnitude;
        this.approxMagnitude = (float) (MathUtils.floor(magnitude / MAGNITUDE_UNIT) * MAGNITUDE_UNIT);

        // float approxMagnitude = (float) (MathUtils.floor(magnitude / MAGNITUDE_UNIT) * MAGNITUDE_UNIT);
        // float fraction = magnitude - approxMagnitude;
        // if (fraction > 0) {
        // this.approxMagnitude = approxMagnitude + MAGNITUDE_UNIT;
        // } else {
        // this.approxMagnitude = approxMagnitude;
        // }
    }

    /**
     * 等級を取得します
     */
    public float getMagnitude() {
        return magnitude;
    }

    /**
     * おおよその等級を取得します
     */
    public float getApproxMagnitude() {
        return approxMagnitude;
    }

    /**
     * このインスタンスで扱う等級の下限値
     * 
     * @return
     */
    public float getLowerMagnitude() {
        return approxMagnitude;
    }

    /**
     * このインスタンスで扱う等級の上限値
     * 
     * @return
     */
    public float getUpperMagnitude() {
        return approxMagnitude + MAGNITUDE_UNIT;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(approxMagnitude);
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
        if (!(obj instanceof StarApproxMagnitude)) {
            return false;
        }
        StarApproxMagnitude other = (StarApproxMagnitude) obj;
        if (Float.floatToIntBits(approxMagnitude) != Float.floatToIntBits(other.approxMagnitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("StarApproxMagnitude [magnitude=%s, approxMagnitude=%s, range=[%s -%s]"//
                , magnitude             //
                , approxMagnitude       //
                , getLowerMagnitude()   //
                , getUpperMagnitude()); //
    }

}
