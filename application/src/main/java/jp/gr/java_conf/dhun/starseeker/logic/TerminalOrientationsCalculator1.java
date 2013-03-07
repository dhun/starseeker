/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 観測地点のロケータ.<br/>
 * 
 * @author jun
 * 
 */
public class TerminalOrientationsCalculator1 implements SensorEventListener, ITerminalOrientationsCalculator {

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor;
    private final Sensor magneticFieldSensor;

    public TerminalOrientationsCalculator1(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> list;
        list = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (list.size() > 0) {
            accelerometerSensor = list.get(0);
        } else {
            accelerometerSensor = null;
        }
        list = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (list.size() > 0) {
            magneticFieldSensor = list.get(0);
        } else {
            magneticFieldSensor = null;
        }
    }

    @Override
    public void setTerminalRotation(int displayRotation) {
    }

    /*
     * (非 Javadoc)
     * 
     * @see jp.gr.java_conf.dhun.starseeker.logic.IObservationSiteLocator#registerSensorListeners()
     */
    @Override
    public void registerSensorListeners() {
        if (null != accelerometerSensor) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        if (null != magneticFieldSensor) {
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /*
     * (非 Javadoc)
     * 
     * @see jp.gr.java_conf.dhun.starseeker.logic.IObservationSiteLocator#unregisterSensorListeners()
     */
    @Override
    public void unregisterSensorListeners() {
        sensorManager.unregisterListener(this);
    }

    /** SensorEventListener */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private final float[] magneticValues = new float[3];
    private final float[] accelerometerValues = new float[3];
    private final float[] orientationValues = new float[3];

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 加速度の取得
        if (event.sensor == accelerometerSensor) {
            accelerometerValues[0] = event.values[0];
            accelerometerValues[1] = event.values[1];
            accelerometerValues[2] = event.values[2];
        }
        // 地磁気の取得
        if (event.sensor == magneticFieldSensor) {
            magneticValues[0] = event.values[0];
            magneticValues[1] = event.values[1];
            magneticValues[2] = event.values[2];
        }

        // 傾きの算出
        if (magneticValues != null && accelerometerValues != null) {
            float[] inR = new float[9];
            float[] outR = new float[9];
            float[] I = new float[9];

            SensorManager.getRotationMatrix(inR, I, accelerometerValues, magneticValues);

            // 画面の向きによって軸の変更可
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR);
            SensorManager.getOrientation(outR, orientationValues);

            // ラジアンから度への変換 及び方位の範囲を-180～180度から0～359度に変換
            orientationValues[0] = radianToDegree(orientationValues[0]);
            orientationValues[1] = radianToDegree(orientationValues[1]);
            orientationValues[2] = radianToDegree(orientationValues[2]);
        }

        // 出力するための配列に格納
        siteLocation.azimuth = orientationValues[0];
        siteLocation.pitch = orientationValues[1];
        siteLocation.roll = orientationValues[2];

        if (null != onChangeSiteLocationListener) {
            onChangeSiteLocationListener.onChangeTerminalOrientations(siteLocation);
        }
    }

    private final Orientations siteLocation = new Orientations();
    private OnChangeTerminalOrientationsListener onChangeSiteLocationListener;

    /* ***** ラジアンから度への変換 ***** */
    int radianToDegree(float rad) {
        return (int) Math.floor(Math.toDegrees(rad));
    }

    @Override
    public void setOnChangeTerminalOrientationsListener(OnChangeTerminalOrientationsListener listener) {
        this.onChangeSiteLocationListener = listener;
    }
}
