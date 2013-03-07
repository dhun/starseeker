package jp.gr.java_conf.dhun.starseeker;

import java.text.DecimalFormat;

import jp.gr.java_conf.dhun.starseeker.logic.TerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.TerminalOrientationsCalculatorFactory;
import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

public class HelloAndroidActivity extends Activity {

    private static String TAG = "starseeker";

    private ITerminalOrientationsCalculator terminalOrientationsCalculator1;
    private ITerminalOrientationsCalculator terminalOrientationsCalculator2;

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

        int displayRotation = getDisplayRotation();
        terminalOrientationsCalculator1 = new TerminalOrientationsCalculator(this, displayRotation);
        terminalOrientationsCalculator2 = TerminalOrientationsCalculatorFactory.create(this, displayRotation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        terminalOrientationsCalculator1 = null;
        terminalOrientationsCalculator2 = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // センサーイベントの登録
        terminalOrientationsCalculator1.registerSensorListeners();
        terminalOrientationsCalculator1.setOnChangeTerminalOrientationsListener(new ITerminalOrientationsCalculator.OnChangeTerminalOrientationsListener() {
            @Override
            public void onChangeTerminalOrientations(Orientations siteLocation) {
                String text = "方位：" + nf.format(to360Degrees(siteLocation.azimuth)) + "\nピッチ：" + nf.format(siteLocation.pitch) + "\nロール：" + nf.format(siteLocation.roll);
                tv1.setText(text); // 描画
            }
        });

        terminalOrientationsCalculator2.registerSensorListeners();
        terminalOrientationsCalculator2.setOnChangeTerminalOrientationsListener(new ITerminalOrientationsCalculator.OnChangeTerminalOrientationsListener() {
            @Override
            public void onChangeTerminalOrientations(Orientations siteLocation) {
                String text = "方位：" + nf.format(to360Degrees(siteLocation.azimuth)) + "\nピッチ：" + nf.format(siteLocation.pitch) + "\nロール：" + nf.format(siteLocation.roll);
                tv2.setText(text); // 描画
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // センサーイベントの削除
        terminalOrientationsCalculator1.unregisterSensorListeners();
        terminalOrientationsCalculator2.unregisterSensorListeners();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d("onConfigurationChanged", "rotation=" + getDisplayRotation());
    }

    private int getDisplayRotation() {
        Display display = getWindowManager().getDefaultDisplay();
        return display.getRotation();
    }

    // 方位の範囲を-180～180度から0～359度に変換
    private double to360Degrees(double degrees) {
        if (degrees >= 0) {
            return degrees;
        } else {
            return 360 + degrees;
        }
    }
}
