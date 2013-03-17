/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;

/**
 * 端末が縦向きの場合の方位計算機.<br/>
 * 
 * @author jun
 * 
 */
public class FazzyPortraitTerminalOrientationsCalculator extends AbstractFazzyTerminalOrientationsCalculator {

    private int axisX; // １回目のSensorManager.remapCoordinateSystemのX軸
    private int axisY; // １回目のSensorManager.remapCoordinateSystemのY軸

    /**
     * コンストラクタ.<br/>
     * 
     * @param context コンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値
     */
    public FazzyPortraitTerminalOrientationsCalculator(Context context, int displayRotation) {
        super(context, displayRotation);
    }

    @Override
    public void setTerminalRotation(int displayRotation) {
        switch (displayRotation) {
        case Surface.ROTATION_0:
            axisX = SensorManager.AXIS_X;
            axisY = SensorManager.AXIS_Z;
            break;
        case Surface.ROTATION_180:
            axisX = SensorManager.AXIS_MINUS_X;
            axisY = SensorManager.AXIS_MINUS_Z;
            break;
        case Surface.ROTATION_90:
        case Surface.ROTATION_270:
        default:
            throw new IllegalStateException("displayRotationの値が不正です. value=" + displayRotation);
        }
    }

    @Override
    protected boolean remapCoordinateSystem(float[] inR, float[] outR) {
        return SensorManager.remapCoordinateSystem(inR, axisX, axisY, outR);
    }
}
