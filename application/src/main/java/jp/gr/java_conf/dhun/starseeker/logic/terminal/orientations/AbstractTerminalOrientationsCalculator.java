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

/**
 * 端末方位計算機の抽象実装.<br/>
 * センサーリスナーからの呼び出しが極めて頻繁なため、具象クラスからIFやFORを撤廃するために用意した.<br/>
 * 
 * @author jun
 * 
 */
public abstract class AbstractTerminalOrientationsCalculator implements SensorEventListener, ITerminalOrientationsCalculator {

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor; // 加速度センサー
    private final Sensor magneticFieldSensor; // 地磁気センサー

    private final float[] accelerometerValues = new float[3]; // 加速度センサーの値. x, y, z
    private final float[] magneticFieldValues = new float[3]; // 地磁気センサーの値. x, y, z
    private final float[] orientationValues = new float[3];   // ２つのセンサーから算出した方位. x, y, z

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
     * @param context Androidコンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値
     */
    public AbstractTerminalOrientationsCalculator(Context context, int displayRotation) {
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

    /**
     * 端末の回転状況に応じてマトリクスを回転させます.<br/>
     * 
     * @param inR 入力マトリクス
     * @param outR 出力マトリクス
     * @return {@link SensorManager#remapCoordinateSystem(float[], int, int, float[])}の戻り値
     */
    protected abstract boolean remapCoordinateSystem(float[] inR, float[] outR);

    // ********************************************************************************
    // ITerminalOrientationsCalculator

    /**
     * {@inheritDoc}<br/>
     * センサーマネージャにリスナを登録します.
     */
    @Override
    public void prepare() {
        if (null != accelerometerSensor) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        if (null != magneticFieldSensor) {
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * {@inheritDoc}<br/>
     * センサーマネージャをリスナから削除します.
     */
    @Override
    public void pause() {
        if (null != accelerometerSensor) {
            sensorManager.unregisterListener(this, accelerometerSensor);
        }
        if (null != magneticFieldSensor) {
            sensorManager.unregisterListener(this, magneticFieldSensor);
        }
    }

    @Override
    public void setOnChangeTerminalOrientationsListener(OnChangeTerminalOrientationsListener listener) {
        this.onChangeSiteLocationListener = listener;
    }

    // ********************************************************************************
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
        this.remapCoordinateSystem(R, outR);
        SensorManager.getOrientation(outR, orientationValues);

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
