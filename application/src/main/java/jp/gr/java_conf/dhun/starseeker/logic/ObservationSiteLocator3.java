/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.List;

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
public class ObservationSiteLocator3 implements SensorEventListener, IObservationSiteLocator {

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor;
    private final Sensor magneticFieldSensor;

    private final Activity context;

    public ObservationSiteLocator3(Activity context) {
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

        x = SensorManager.AXIS_MINUS_Z;
        y = SensorManager.AXIS_Y;
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

            // 端末の回転状態からX軸とY軸の要素を算出
            int axisX;
            int axisY;

            // switch (getDispRotation(context)) {
            // case Surface.ROTATION_0:
            // axisX = SensorManager.AXIS_X;
            // axisY = SensorManager.AXIS_MINUS_Z;
            // break;
            // case Surface.ROTATION_90:
            // axisX = SensorManager.AXIS_MINUS_Y;
            // axisY = SensorManager.AXIS_MINUS_Z;
            // break;
            // case Surface.ROTATION_180:
            // axisX = SensorManager.AXIS_MINUS_X;
            // axisY = SensorManager.AXIS_MINUS_Z;
            // break;
            // case Surface.ROTATION_270:
            // axisX = SensorManager.AXIS_Y;
            // axisY = SensorManager.AXIS_MINUS_Z;
            // break;
            // default:
            // throw new IllegalStateException("");
            // }
            switch (getDispRotation(context)) {
            case Surface.ROTATION_0:
                axisX = SensorManager.AXIS_MINUS_Z;
                axisY = SensorManager.AXIS_Y;
                break;
            case Surface.ROTATION_90:
                axisX = SensorManager.AXIS_MINUS_Z;
                axisY = SensorManager.AXIS_MINUS_X;
                break;
            case Surface.ROTATION_180:
                axisX = SensorManager.AXIS_MINUS_Z;
                axisY = SensorManager.AXIS_MINUS_Y;
                break;
            case Surface.ROTATION_270:
                axisX = SensorManager.AXIS_MINUS_Z;
                axisY = SensorManager.AXIS_X;
                break;
            default:
                throw new IllegalStateException("");
            }

            axisX = x;
            axisY = y;

            // 回転状態に応じた方位角を算出
            float[] R = new float[16];
            float[] I = new float[16];
            float[] outR = new float[16];

            SensorManager.getRotationMatrix(R, I, accelerometerValues, magneticValues);
            SensorManager.remapCoordinateSystem(R, axisX, axisY, outR);
            SensorManager.getOrientation(outR, orientationValues);

            // ラジアンを度に変換
            // orientationValues[0] = (float) Math.toDegrees(orientationValues[0]);
            // orientationValues[1] = (float) Math.toDegrees(orientationValues[1]);
            // orientationValues[2] = (float) Math.toDegrees(orientationValues[2]);
            float angle = radianToDegree(orientationValues[0]);
            if (angle >= 0) {
                orientationValues[0] = angle;
            } else if (angle < 0) {
                orientationValues[0] = 360 + angle;
            }
            orientationValues[1] = radianToDegree(orientationValues[1]);
            orientationValues[2] = radianToDegree(orientationValues[2]);
        }

        // 出力するための配列に格納
        siteLocation.accelX = accelerometerValues[0];
        siteLocation.accelX = accelerometerValues[1];
        siteLocation.accelX = accelerometerValues[2];
        siteLocation.azimuth = orientationValues[0];
        siteLocation.pitch = orientationValues[1];
        siteLocation.roll = orientationValues[2];
        if (null != onChangeSiteLocationListener) {
            onChangeSiteLocationListener.onChangeSiteLocation(siteLocation);
        }
    }

    private int x = SensorManager.AXIS_Y;
    private int y = SensorManager.AXIS_Z;

    private final SiteLocation siteLocation = new SiteLocation();
    private OnChangeSiteLocationListener onChangeSiteLocationListener;

    /* ***** ラジアンから度への変換 ***** */
    int radianToDegree(float rad) {
        return (int) Math.floor(Math.toDegrees(rad) / 10) * 10;
    }

    private static int getDispRotation(Activity act) {
        Display d = act.getWindowManager().getDefaultDisplay();
        return d.getRotation();
    }

    @Override
    public void setOnChangeSiteLocationListener(OnChangeSiteLocationListener listener) {
        this.onChangeSiteLocationListener = listener;
    }
}
