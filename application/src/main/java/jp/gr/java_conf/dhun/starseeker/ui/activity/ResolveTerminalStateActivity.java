package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.text.DecimalFormat;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.FlatTerminalOrientationsCalculator2;
import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.TerminalOrientationsCalculatorFactory;
import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

public class ResolveTerminalStateActivity extends Activity {

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
        LogUtils.i(getClass(), "onCreate:");

        setContentView(R.layout.activity_resolve_terminal_state);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        nf = new DecimalFormat("000.0");
        nf.setPositivePrefix("+");
        nf.setNegativePrefix("-");

        int displayRotation = getDisplayRotation();
        terminalOrientationsCalculator1 = new FlatTerminalOrientationsCalculator2(this, displayRotation);
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
        terminalOrientationsCalculator1.prepare();
        terminalOrientationsCalculator1.setOnChangeTerminalOrientationsListener(new ITerminalOrientationsCalculator.OnChangeTerminalOrientationsListener() {
            @Override
            public void onChangeTerminalOrientations(Orientations siteLocation) {
                String text = "方位：" + nf.format(to360Degrees(siteLocation.azimuth)) + "\nピッチ：" + nf.format(siteLocation.pitch) + "\nロール：" + nf.format(siteLocation.roll);
                tv1.setText(text); // 描画
            }
        });

        terminalOrientationsCalculator2.prepare();
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
        terminalOrientationsCalculator1.pause();
        terminalOrientationsCalculator2.pause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LogUtils.d(getClass(), "onConfigurationChanged:rotation=" + getDisplayRotation());
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
