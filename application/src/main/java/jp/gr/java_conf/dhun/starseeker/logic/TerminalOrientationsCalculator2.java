/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;

/**
 * 観測地点のロケータ.<br/>
 * 
 * @author jun
 * 
 */
public class TerminalOrientationsCalculator2 implements SensorEventListener, ITerminalOrientationsCalculator {

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor;
    private final Sensor magneticFieldSensor;

    private final Activity context;

    public TerminalOrientationsCalculator2(Activity context) {
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

        this.context = context;
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
            float[] R = new float[9];
            float[] I = new float[9];

            SensorManager.getRotationMatrix(R, I,
                    accelerometerValues, magneticValues);

            // 画面の回転状態を取得する
            int dr = getDispRotation(context);

            if (dr == Surface.ROTATION_0) {
                // 回転無し
                SensorManager.getOrientation(R, orientationValues);

            } else {
                // 回転あり
                float[] outR = new float[9];

                if (dr == Surface.ROTATION_90) {
                    SensorManager.remapCoordinateSystem(R,
                            SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, outR);

                } else if (dr == Surface.ROTATION_180) {
                    float[] outR2 = new float[9];
                    SensorManager.remapCoordinateSystem(R,
                            SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, outR2);
                    SensorManager.remapCoordinateSystem(outR2,
                            SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, outR);

                } else if (dr == Surface.ROTATION_270) {
                    SensorManager.remapCoordinateSystem(R,
                            SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_MINUS_X, outR);

                }
                SensorManager.getOrientation(outR, orientationValues);
            }

            // 求まった方位角．ラジアンなので度に変換する
            orientationValues[0] = (float) Math.toDegrees(orientationValues[0]);
            orientationValues[1] = (float) Math.toDegrees(orientationValues[1]);
            orientationValues[2] = (float) Math.toDegrees(orientationValues[2]);
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

    private static int getDispRotation(Activity act) {
        Display d = act.getWindowManager().getDefaultDisplay();
        return d.getRotation();
    }

    @Override
    public void setOnChangeTerminalOrientationsListener(OnChangeTerminalOrientationsListener listener) {
        this.onChangeSiteLocationListener = listener;
    }
}
