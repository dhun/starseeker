package jp.gr.java_conf.dhun.starseeker;

import java.text.DecimalFormat;

import jp.gr.java_conf.dhun.starseeker.logic.IObservationSiteLocator;
import jp.gr.java_conf.dhun.starseeker.logic.ObservationSiteLocator1;
import jp.gr.java_conf.dhun.starseeker.logic.ObservationSiteLocator3;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

public class HelloAndroidActivity extends Activity {

    private static String TAG = "starseeker";

    private IObservationSiteLocator observationSiteLocator1;
    private IObservationSiteLocator observationSiteLocator2;

    private TextView tv1, tv2;

    private DecimalFormat nf;

    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *        previously being shut down then this Bundle contains the data it most
     *        recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.main);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        nf = new DecimalFormat("000.0");
        nf.setPositivePrefix("+");
        nf.setNegativePrefix("-");

        observationSiteLocator1 = new ObservationSiteLocator1(this);
        observationSiteLocator2 = new ObservationSiteLocator3(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        observationSiteLocator1 = null;
        observationSiteLocator2 = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // センサーイベントの登録
        observationSiteLocator1.registerSensorListeners();
        observationSiteLocator1.setOnChangeSiteLocationListener(new IObservationSiteLocator.OnChangeSiteLocationListener() {
            @Override
            public void onChangeSiteLocation(IObservationSiteLocator.SiteLocation siteLocation) {
                String text = "Xaccel：" + siteLocation.accelX + "\nYaccel：" + siteLocation.accelY + "\nZaccel：" + siteLocation.accelZ +
                        "\n方位：" + nf.format(siteLocation.azimuth) + "\nピッチ：" + nf.format(siteLocation.pitch) + "\nロール：" + nf.format(siteLocation.roll);
                tv1.setText(text);        // 描画
            }
        });

        observationSiteLocator2.registerSensorListeners();
        observationSiteLocator2.setOnChangeSiteLocationListener(new IObservationSiteLocator.OnChangeSiteLocationListener() {
            @Override
            public void onChangeSiteLocation(IObservationSiteLocator.SiteLocation siteLocation) {
                String text = "Xaccel：" + siteLocation.accelX + "\nYaccel：" + siteLocation.accelY + "\nZaccel：" + siteLocation.accelZ +
                        "\n方位：" + nf.format(siteLocation.azimuth) + "\nピッチ：" + nf.format(siteLocation.pitch) + "\nロール：" + nf.format(siteLocation.roll);
                tv2.setText(text);        // 描画
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // センサーイベントの削除
        observationSiteLocator1.unregisterSensorListeners();
        observationSiteLocator2.unregisterSensorListeners();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Display display = getWindowManager().getDefaultDisplay();
        Log.d("onConfigurationChanged", "rotation=" + display.getRotation());
    }

}
