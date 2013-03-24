package jp.gr.java_conf.dhun.starseeker.system.model.coordinates;

import jp.gr.java_conf.dhun.starseeker.model.Star;
import android.annotation.SuppressLint;

/**
 * 座標の四角形
 * 
 * @author jun
 * 
 */
public class CoordinatesRect {
    public float xL;    // X軸の左端
    public float xR;    // X軸の右端
    public float yT;    // Y軸の上端
    public float yB;    // Y軸の下端

    /**
     * 座標をゼロにします.
     */
    public void setupZero() {
        xL = 0f;
        xR = 0f;
        yT = 0f;
        yB = 0f;
    }

    /**
     * 横幅を返却します.
     */
    public float width() {
        return Math.abs((xL - xR));
    }

    /**
     * 高さを返却します.
     */
    public float height() {
        return Math.abs((yT - yB));
    }

    /**
     * 中心のX座標を返却します.
     */
    public float centerX() {
        return xL + width() / 2;
    }

    /**
     * 中心のY座標を返却します.
     */
    public float centerY() {
        return yT + height() / 2;
    }

    /**
     * 領域(＝高さと幅)がゼロより大きいかどうかを判定します.
     */
    public boolean hasRegion() {
        return hasWidth() && hasHeight();
    }

    /**
     * 横幅がゼロより大きいかどうかを判定します.
     */
    public boolean hasWidth() {
        return xL != 0 || xR != 0;
    }

    /**
     * 高さがゼロより大きいかどうかを判定します.
     */
    public boolean hasHeight() {
        return xL != 0 || xR != 0;
    }

    /**
     * 指定された星の座標が領域に含まれるかどうかを判定します.
     * 
     * @param star 星
     * @return 領域に含まれる場合はtrue.
     */
    public boolean contains(Star star) {
        float starAzimuth = star.getAzimuth();
        float starAltitude = star.getAltitude();
        if (starAzimuth < xL || xR < starAzimuth) {
            return false;
        }
        if (starAltitude < yT || yB < starAltitude) {
            return false;
        }
        return true;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("x[L - R, width]=[%6.2f - %6.2f, %6.2f], y[T - B, height]=[%6.2f - %6.2f, %6.2f]]", xL, xR, width(), yT, yB, height());
    }
}