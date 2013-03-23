/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import android.view.Display;
import android.view.Surface;

/**
 * 方位.<br/>
 * 精度はSensorEventListenerから取得できる値と同じfloat.
 * 
 * @author jun
 * 
 */
public class Orientations {
    public float azimuth;   // 方位.
    public float pitch;     // ピッチ.
    public float roll;      // ロール.

    private DisplayRotation displayRotation = DisplayRotation.ROTATION_0;    // 端末の回転状態

    public void copyFrom(Orientations orientations) {
        this.azimuth = orientations.azimuth;
        this.pitch = orientations.pitch;
        this.roll = orientations.roll;
        this.displayRotation = orientations.displayRotation;
    }

    /**
     * 端末の回転状態を取得します.<br/>
     */
    public DisplayRotation getDisplayRotation() {
        return displayRotation;
    }

    /**
     * 端末の回転状態を設定します.<br/>
     * 
     * @param displayRotation {@link DisplayRotation}
     */
    public void setDisplayRotation(DisplayRotation displayRotation) {
        if (null == displayRotation) {
            throw new NullPointerException();
        }
        this.displayRotation = displayRotation;
    }

    /**
     * 端末の回転状態を設定します.<br/>
     * 
     * @param displayRotation {@link Display#getRotation()} の値
     */
    public void setDisplayRotation(int displayRotation) {
        this.displayRotation = DisplayRotation.valueOf(displayRotation);
    }

    /**
     * 端末の回転状態.<br/>
     */
    public enum DisplayRotation {
        ROTATION_0(Surface.ROTATION_0),
        ROTATION_90(Surface.ROTATION_90),
        ROTATION_180(Surface.ROTATION_180),
        ROTATION_270(Surface.ROTATION_270);

        private final int value;

        private DisplayRotation(int value) {
            this.value = value;
        }

        /**
         * 端末の回転状態が縦向きであるかを返却します.<br/>
         * 
         * @return 縦向きならtrue.
         */
        public boolean isPortrait() {
            return (this == ROTATION_0 || this == ROTATION_180);
        }

        /**
         * 端末の回転状態が横向きであるかを返却します.<br/>
         * 
         * @return 横向きならtrue.
         */
        public boolean isLandscape() {
            return !isPortrait();
        }

        /**
         * {@link Display#getRotation()} の値に対するEnumを返却します.<br/>
         * 
         * @param value {@link Display#getRotation()} の値
         */
        public static DisplayRotation valueOf(int value) {
            for (DisplayRotation e : DisplayRotation.values()) {
                if (e.value == value) {
                    return e;
                }
            }
            throw new IllegalArgumentException("valueが不正です. value=" + value);
        }
    }
}
