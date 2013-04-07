/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.util.MathUtils;

/**
 * 星のおおよその等級/<br/>
 * 
 * @author jun
 * 
 */
public class StarApproxMagnitude {

    private final float magnitude;          // 等級
    private final float approxMagnitude;    // おおよその等級. 0.5等級単位に切り捨てています

    /**
     * コンストラクタ
     * 
     * @param magnitude 等級
     */
    public StarApproxMagnitude(float magnitude) {
        this.magnitude = magnitude;

        float approxMagnitude = (float) (MathUtils.floor(magnitude / 0.5f) * 0.5f);
        float fraction = magnitude - approxMagnitude;
        if (fraction > 0) {
            this.approxMagnitude = approxMagnitude + 0.5f;
        } else {
            this.approxMagnitude = approxMagnitude;
        }
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

}
