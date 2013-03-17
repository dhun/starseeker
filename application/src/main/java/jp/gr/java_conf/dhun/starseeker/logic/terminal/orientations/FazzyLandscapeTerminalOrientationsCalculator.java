/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;

/**
 * 端末が横向きの場合の方位計算機.<br/>
 * {@link SensorManager#remapCoordinateSystem(float[], int, int, float[])}が２回必要な理由は理解できなかった.<br/>
 * X軸とY軸のパラメータは、根性で捜した
 * 
 * @author jun
 * 
 */
public class FazzyLandscapeTerminalOrientationsCalculator extends AbstractFazzyTerminalOrientationsCalculator {

    private int axisX1; // １回目のSensorManager.remapCoordinateSystemのX軸
    private int axisY1; // １回目のSensorManager.remapCoordinateSystemのY軸

    private int axisX2; // ２回目のSensorManager.remapCoordinateSystemのX軸
    private int axisY2; // ２回目のSensorManager.remapCoordinateSystemのY軸

    // 以下はonSensorChangedでのみ利用するローカル変数. キャッシュ
    float tmpR[] = new float[MATRIX_SIZE];

    /**
     * コンストラクタ.<br/>
     * 
     * @param context コンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値
     */
    public FazzyLandscapeTerminalOrientationsCalculator(Context context, int displayRotation) {
        super(context, displayRotation);
    }

    @Override
    public void setTerminalRotation(int displayRotation) {
        switch (displayRotation) {
        case Surface.ROTATION_90:
            axisX1 = SensorManager.AXIS_MINUS_Y;
            axisY1 = SensorManager.AXIS_X;
            axisX2 = SensorManager.AXIS_MINUS_X;
            axisY2 = SensorManager.AXIS_MINUS_Z;
            break;

        case Surface.ROTATION_270:
            axisX1 = SensorManager.AXIS_Y;
            axisY1 = SensorManager.AXIS_MINUS_X;
            axisX2 = SensorManager.AXIS_MINUS_X;
            axisY2 = SensorManager.AXIS_MINUS_Z;
            break;

        case Surface.ROTATION_0:
        case Surface.ROTATION_180:
        default:
            throw new IllegalStateException("displayRotationの値が不正です. value=" + displayRotation);
        }
    }

    @Override
    protected boolean remapCoordinateSystem(float[] inR, float[] outR) {
        if (!SensorManager.remapCoordinateSystem(inR, axisX1, axisY1, tmpR)) {
            return false;
        }
        if (!SensorManager.remapCoordinateSystem(tmpR, axisX2, axisY2, outR)) {
            return false;
        }
        return true;
    }
}
