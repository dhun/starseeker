/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations;

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
 * 端末を水平に置いた場合の方位計算機.<br/>
 * 
 * @author jun
 * 
 */
public class FlatTerminalOrientationsCalculator2 implements SensorEventListener, ITerminalOrientationsCalculator {

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor; // 加速度センサー
    private final Sensor magneticFieldSensor; // 地磁気センサー

    private boolean readyAccelerometerValues = false; // accelerometerValuesに値が格納されたかどうか
    private boolean readyMagneticFieldValues = false; // magneticFieldValuesに値が格納されたかどうか

    private int displayRotation;

    private final float[] accelerometerValues = new float[3]; // 加速度センサーの値. x, y, z
    private final float[] magneticFieldValues = new float[3]; // 地磁気センサーの値. x, y, z
    private final float[] orientationValues = new float[3];   // ２つのセンサーから算出した方位. x, y, z

    private OnChangeTerminalOrientationsListener onChangeSiteLocationListener;
    private final Orientations siteLocation = new Orientations(); // リスナが通知する変数をキャッシュ

    /**
     * コンストラクタ.<br/>
     * 
     * @param context コンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値
     */
    public FlatTerminalOrientationsCalculator2(Context context, int displayRotation) {
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

        // 端末の回転状態を設定
        this.setTerminalRotation(displayRotation);
    }

    @Override
    public void setTerminalRotation(int displayRotation) {
        this.displayRotation = displayRotation;
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

    /** SensorEventListener */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

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
        float[] R = new float[MATRIX_SIZE];
        float[] I = new float[MATRIX_SIZE];

        SensorManager.getRotationMatrix(R, I, accelerometerValues, magneticFieldValues);

        // 画面の回転状態を取得する
        if (displayRotation == Surface.ROTATION_0) {
            // 回転無し
            SensorManager.getOrientation(R, orientationValues);

        } else {
            // 回転あり
            float[] outR = new float[MATRIX_SIZE];

            if (displayRotation == Surface.ROTATION_90) {
                SensorManager.remapCoordinateSystem(R,
                        SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, outR);

            } else if (displayRotation == Surface.ROTATION_180) {
                float[] outR2 = new float[MATRIX_SIZE];
                SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, outR2);
                SensorManager.remapCoordinateSystem(outR2, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, outR);

            } else if (displayRotation == Surface.ROTATION_270) {
                SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_MINUS_X, outR);

            }
            SensorManager.getOrientation(outR, orientationValues);
        }

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
