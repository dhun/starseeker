/**
 *
 */
package jp.gr.java_conf.dhun.starseeker;

import static org.junit.Assert.*;

/**
 * テストのユーティリティ.<br/>
 * 
 * @author jun
 * 
 */
public final class TestUtils {

    // 誤差の許容値
    public static final double DELTA_HOUR = 1.0 / 24 / 60 / 60 / 1000 * 1000; // 時間. 1ミリ秒未満
    public static final double DELTA_ANGLE = 0.01; // 0.005; // 0.0004; // 0.000001; // 角度
    public static final double DELTA_TRIG_FUNC = 0.0001; // 三角関数
    public static final double DELTA_CONVERT_EQUATORIAL_COORDINATE_TO_HORIZONTAL_COORDINATE = 0.0001; // 赤道座標→地平座標の変換式

    private TestUtils() {
    }

    public static void asserAllowingError(double actual, double expect, double delta) {
        double diff = Math.abs(actual - expect);
        assertTrue(String.format("誤差が許容値より大きい, 誤差=[%f], 許容値=[%f]", diff, delta), diff < delta);
    }
}
