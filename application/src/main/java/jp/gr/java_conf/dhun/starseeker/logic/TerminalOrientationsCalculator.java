/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;

/**
 * 端末方位計算機の実装.<br/>
 * 
 * @author jun
 * 
 */
public class TerminalOrientationsCalculator implements SensorEventListener, ITerminalOrientationsCalculator {

    private static final int MATRIX_SIZE = 9; // 16だと正しい値が取得できなかった

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor; // 加速度センサー
    private final Sensor magneticFieldSensor; // 地磁気センサー

    private final float[] accelerometerValues = new float[3]; // 加速度センサーの値. x, y, z
    private final float[] magneticFieldValues = new float[3]; // 地磁気センサーの値. x, y, z
    private final float[] orientationValues = new float[3];   // ２つのセンサーから算出した方位. x, y, z

    private int axisX; // 端末の回転状態に応じたX軸
    private int axisY; // 端末の回転状態に応じたY軸

    private boolean readyAccelerometerValues = false; // accelerometerValuesに値が格納されたかどうか
    private boolean readyMagneticFieldValues = false; // magneticFieldValuesに値が格納されたかどうか

    private OnChangeTerminalOrientationsListener onChangeSiteLocationListener;
    private final Orientations siteLocation = new Orientations(); // リスナが通知する変数をキャッシュ

    // 以下はonSensorChangedでのみ利用するローカル変数. キャッシュ
    private final float[] R = new float[MATRIX_SIZE];
    private final float[] I = new float[MATRIX_SIZE];
    private final float[] outR = new float[MATRIX_SIZE];

    /**
     * コンストラクタ.<br/>
     * 
     * @param context コンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値
     */
    public TerminalOrientationsCalculator(Context context, int displayRotation) {
        // センサーマネージャからセンサーを取得
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> list;
        list = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!list.isEmpty()) {
            accelerometerSensor = list.get(0);
        } else {
            accelerometerSensor = null;
        }
        list = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (!list.isEmpty()) {
            magneticFieldSensor = list.get(0);
        } else {
            magneticFieldSensor = null;
        }

        // 端末の回転状態に応じたX軸とY軸を算出
        setTerminalRotation(displayRotation);
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
            // -- Y, Z

            // axisX = SensorManager.AXIS_Y;
            // axisY = SensorManager.AXIS_Z;

            // axisX = SensorManager.AXIS_Y;
            // axisY = SensorManager.AXIS_MINUS_Z;

            // axisX = SensorManager.AXIS_MINUS_Y;
            // axisY = SensorManager.AXIS_Z;

            axisX = SensorManager.AXIS_MINUS_Y;
            axisY = SensorManager.AXIS_MINUS_Z;

            // -- Y, X

            // axisX = SensorManager.AXIS_Y;
            // axisY = SensorManager.AXIS_X;

            // axisX = SensorManager.AXIS_Y;
            // axisY = SensorManager.AXIS_MINUS_X;

            // axisX = SensorManager.AXIS_MINUS_Y;
            // axisY = SensorManager.AXIS_X;

            // axisX = SensorManager.AXIS_MINUS_Y;
            // axisY = SensorManager.AXIS_MINUS_X;

            // -- Z, X

            // axisX = SensorManager.AXIS_Z;
            // axisY = SensorManager.AXIS_X;

            // axisX = SensorManager.AXIS_Z;
            // axisY = SensorManager.AXIS_MINUS_X;

            // axisX = SensorManager.AXIS_MINUS_Z;
            // axisY = SensorManager.AXIS_X;

            // axisX = SensorManager.AXIS_MINUS_Z;
            // axisY = SensorManager.AXIS_MINUS_X;

            // -- Z, Y

            // axisX = SensorManager.AXIS_Z; // oops
            // axisY = SensorManager.AXIS_Y;
            //
            // axisX = SensorManager.AXIS_Z; // ops
            // axisY = SensorManager.AXIS_MINUS_Y;

            // axisX = SensorManager.AXIS_MINUS_Z;
            // axisY = SensorManager.AXIS_Y;

            // axisX = SensorManager.AXIS_MINUS_Z;
            // axisY = SensorManager.AXIS_MINUS_Y;

            // X, Z

            // axisX = SensorManager.AXIS_X;
            // axisY = SensorManager.AXIS_Z;

            // axisX = SensorManager.AXIS_X;
            // axisY = SensorManager.AXIS_MINUS_Z;

            // axisX = SensorManager.AXIS_MINUS_X;
            // axisY = SensorManager.AXIS_Z;

            // axisX = SensorManager.AXIS_MINUS_X;
            // axisY = SensorManager.AXIS_MINUS_Z;

            // X, Y

            // axisX = SensorManager.AXIS_X;
            // axisY = SensorManager.AXIS_Y;

            // axisX = SensorManager.AXIS_X;
            // axisY = SensorManager.AXIS_MINUS_Y;

            // axisX = SensorManager.AXIS_MINUS_X;
            // axisY = SensorManager.AXIS_Y;

            // axisX = SensorManager.AXIS_MINUS_X;
            // axisY = SensorManager.AXIS_MINUS_Y;

            break;
        case Surface.ROTATION_270:
            // -- Y, Z

            // axisX = SensorManager.AXIS_Y;
            // axisY = SensorManager.AXIS_Z;

            axisX = SensorManager.AXIS_Y;
            axisY = SensorManager.AXIS_MINUS_Z;

            // axisX = SensorManager.AXIS_MINUS_Y;
            // axisY = SensorManager.AXIS_Z;

            // axisX = SensorManager.AXIS_MINUS_Y;
            // axisY = SensorManager.AXIS_MINUS_Z;

            // -- Z, Y

            // axisX = SensorManager.AXIS_Z; // oops
            // axisY = SensorManager.AXIS_Y;
            //
            // axisX = SensorManager.AXIS_Z; // ops
            // axisY = SensorManager.AXIS_MINUS_Y;

            // axisX = SensorManager.AXIS_MINUS_Z;
            // axisY = SensorManager.AXIS_Y;

            // axisX = SensorManager.AXIS_MINUS_Z;
            // axisY = SensorManager.AXIS_MINUS_Y;
            break;
        default:
            throw new IllegalStateException("displayRotationの値が不正です. value=" + displayRotation);
        }
    }

    @Override
    public void registerSensorListeners() {
        if (null != accelerometerSensor) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        if (null != magneticFieldSensor) {
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void unregisterSensorListeners() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void setOnChangeTerminalOrientationsListener(OnChangeTerminalOrientationsListener listener) {
        this.onChangeSiteLocationListener = listener;
    }

    // SensorEventListener
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * 加速度センサーと地磁気センサーから方位を算出して、リスナに通知します.<br/>
     * 
     * @see http://developer.android.com/reference/android/hardware/SensorManager.html#getRotationMatrix%28float[],%20float[],%20float[],%20float[]%29
     * @see http://developer.android.com/reference/android/hardware/SensorManager.html#remapCoordinateSystem%28float[],%20int,%20int,%20float[]%29
     * @see http://developer.android.com/reference/android/hardware/SensorManager.html#getOrientation%28float[],%20float[]%29
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 加速度の取得
        if (event.sensor == accelerometerSensor) {
            accelerometerValues[0] = event.values[0];
            accelerometerValues[1] = event.values[1];
            accelerometerValues[2] = event.values[2];
            readyAccelerometerValues = true;
        }
        // 地磁気の取得
        if (event.sensor == magneticFieldSensor) {
            magneticFieldValues[0] = event.values[0];
            magneticFieldValues[1] = event.values[1];
            magneticFieldValues[2] = event.values[2];
            readyMagneticFieldValues = true;
        }

        if (!readyAccelerometerValues || !readyMagneticFieldValues) {
            return;
        }

        // 回転状態に応じた方位角を算出
        SensorManager.getRotationMatrix(R, I, accelerometerValues, magneticFieldValues);
        SensorManager.remapCoordinateSystem(R, axisX, axisY, outR);
        SensorManager.getOrientation(outR, orientationValues);

        // // rotation 90
        // final int SIZE = 16;
        // float[] R = new float[SIZE];
        // float[] I = new float[SIZE];
        // float[] outR1 = new float[SIZE];
        // float[] outR2 = new float[SIZE];
        // int axisX = SensorManager.AXIS_Y;
        // int axisY = SensorManager.AXIS_Z;
        // axisX = SensorManager.AXIS_MINUS_Y;
        // axisY = SensorManager.AXIS_Z;
        // axisX = SensorManager.AXIS_MINUS_Y;
        // axisY = SensorManager.AXIS_MINUS_Z;
        // axisX = SensorManager.AXIS_Y;
        // axisY = SensorManager.AXIS_MINUS_Z;
        // SensorManager.getRotationMatrix(R, I, accelerometerValues, magneticFieldValues);
        // SensorManager.remapCoordinateSystem(R, axisX, axisY, outR1);
        // SensorManager.remapCoordinateSystem(outR1, axisX, axisY, outR2);
        // SensorManager.getOrientation(outR2, orientationValues);

        // // rotation 90
        // final int SIZE = 16;
        // float[] R = new float[SIZE];
        // float[] I = new float[SIZE];
        // float[] outR1 = new float[SIZE];
        // SensorManager.getRotationMatrix(R, I, accelerometerValues, magneticFieldValues);
        // SensorManager.remapCoordinateSystem(R, axisX, axisY, outR1);
        // SensorManager.getOrientation(outR1, orientationValues);

        // ラジアンを度に変換
        orientationValues[0] = (float) Math.toDegrees(orientationValues[0]);
        orientationValues[1] = (float) Math.toDegrees(orientationValues[1]);
        orientationValues[2] = (float) Math.toDegrees(orientationValues[2]);

        // 算出した値を通知
        if (null != onChangeSiteLocationListener) {
            siteLocation.azimuth = orientationValues[0];
            siteLocation.pitch = orientationValues[1];
            siteLocation.roll = orientationValues[2];

            onChangeSiteLocationListener.onChangeTerminalOrientations(siteLocation);
        }
    }
}
