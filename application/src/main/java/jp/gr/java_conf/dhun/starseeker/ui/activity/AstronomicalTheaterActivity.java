/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.util.Calendar;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.ChooseObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseObservationSiteLocationDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseObservationSiteTimeDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.listener.OnChooseDataListener;
import jp.gr.java_conf.dhun.starseeker.ui.view.AstronomicalTheaterView;
import jp.gr.java_conf.dhun.starseeker.util.DateTimeUtils;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 天体シアターのアクティビティ.<br/>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterActivity extends Activity //
        implements View.OnClickListener {

    // ダイアログID
    private static final int DIALOG_CHOOSE_OBSERVATION_SITE_TIME = 1;       // 観測地点の時刻選択ダイアログ
    private static final int DIALOG_CHOOSE_OBSERVATION_SITE_LOCATION = 2;   // 観測地点の位置選択ダイアログ

    private AstronomicalTheaterView masterTheaterView;
    private AstronomicalTheaterView secondTheaterView;
    private ImageButton switchShowSecondTheaterButton;
    private ImageButton chooseSecondObservationSiteLocationButton;

    private ObservationSiteLocation masterObservationSiteLocation;
    private ObservationSiteLocation secondObservationSiteLocation;

    private Date baseDate;
    private Calendar masterCurrentCalendar;
    private Calendar secondCurrentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.v(getClass(), "onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.content_astronomical_theater);

        // ビューの取得
        masterTheaterView = (AstronomicalTheaterView) findViewById(R.id.masterTheaterView);
        secondTheaterView = (AstronomicalTheaterView) findViewById(R.id.secondTheaterView);
        switchShowSecondTheaterButton = (ImageButton) findViewById(R.id.switchShowSecondTheaterButton);
        chooseSecondObservationSiteLocationButton = (ImageButton) findViewById(R.id.chooseSecondObservationSiteLocationButton);

        // 天体シアターの設定
        masterObservationSiteLocation = ChooseObservationSiteLocationResolver.getObservationSiteLocations().get(0);
        secondObservationSiteLocation = ChooseObservationSiteLocationResolver.getObservationSiteLocations().get(8);

        baseDate = new Date();
        refreshBaseCalendar(baseDate);    // 基準日時はシステム日時

        masterTheaterView.initialize();
        secondTheaterView.initialize();

        setSecondTheaterViewVisible(false); // 最初はセカンドパネルは非表示

        refreshMasterTheaterView();
        refreshSecondTheaterView();

        // クリックイベントの設定
        findViewById(R.id.zoomControls).setOnClickListener(this);
        findViewById(R.id.chooseMagnitudeButton).setOnClickListener(this);
        findViewById(R.id.chooseObservationSiteTimeButton).setOnClickListener(this);
        findViewById(R.id.chooseMasterObservationSiteLocationButton).setOnClickListener(this);
        findViewById(R.id.settingsButton).setOnClickListener(this);
        findViewById(R.id.switchStarLocateIndicatorButton).setOnClickListener(this);
        findViewById(R.id.switchLockDisplayRotateButton).setOnClickListener(this);
        findViewById(R.id.photographButton).setOnClickListener(this);
        findViewById(R.id.imageButton2).setOnClickListener(this);
        switchShowSecondTheaterButton.setOnClickListener(this);
        chooseSecondObservationSiteLocationButton.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.v(getClass(), "onConfigurationChanged. newOrientation=" + newConfig.orientation);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        LogUtils.v(getClass(), "onResume");
        super.onResume();

        masterTheaterView.resume();
        secondTheaterView.resume();
    }

    @Override
    protected void onPause() {
        LogUtils.v(getClass(), "onPause");
        super.onPause();

        masterTheaterView.pause();
        secondTheaterView.pause();
    }

    @Override
    public void onClick(View view) {
        // Intent intent;
        String name;
        switch (view.getId()) {
        case R.id.zoomControls:
            name = "zoomControls";
            break;
        case R.id.chooseMagnitudeButton:
            name = "chooseMagnitudeButton";
            break;

        case R.id.chooseObservationSiteTimeButton:
            showDialog(DIALOG_CHOOSE_OBSERVATION_SITE_TIME);
            return;

        case R.id.chooseMasterObservationSiteLocationButton:
        case R.id.chooseSecondObservationSiteLocationButton:
            Bundle bundle = new Bundle();
            bundle.putBoolean("isMaster", view.getId() == R.id.chooseMasterObservationSiteLocationButton);
            showDialog(DIALOG_CHOOSE_OBSERVATION_SITE_LOCATION, bundle);
            return;

        case R.id.settingsButton:
            name = "settingsButton";
            break;
        case R.id.switchStarLocateIndicatorButton:
            name = "switchStarLocateIndicatorButton";
            break;

        case R.id.switchLockDisplayRotateButton:
            changeScreenOrientation();
            return;

        case R.id.photographButton:
            name = "photographButton";
            break;

        case R.id.switchShowSecondTheaterButton:
            setSecondTheaterViewVisible(secondTheaterView.getVisibility() != View.VISIBLE);
            return;

        case R.id.imageButton2:
            name = "imageButton2";
            break;
        default:
            throw new IllegalStateException("unknown view.id. value=[" + view.getId() + "]");
        }

        Toast.makeText(this, name + "が押されました", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(final int id, Bundle bundle) {
        if (id == DIALOG_CHOOSE_OBSERVATION_SITE_TIME) {
            // 観測地点の時刻選択ダイアログ
            ChooseObservationSiteTimeDialogBuilder builder = new ChooseObservationSiteTimeDialogBuilder(this);
            builder.setDialogId(id);
            builder.setInitialDateTime(baseDate);
            builder.setOnChooseDataListener(new OnChooseDataListener<Date>() {
                @Override
                public void onChooseData(Date data) {
                    refreshBaseCalendar(data);

                    refreshMasterTheaterView();
                    refreshSecondTheaterView();
                }
            });
            return builder.create();
        }

        if (id == DIALOG_CHOOSE_OBSERVATION_SITE_LOCATION) {
            // 観測地点の位置選択ダイアログ
            final boolean isMaster = bundle.getBoolean("isMaster");
            String title = null;
            switch (getDisplayRotation()) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                title = isMaster ? "上側" : "下側"; // XXX strings.xml
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                title = isMaster ? "左側" : "右側"; // XXX strings.xml
                break;
            }

            ChooseObservationSiteLocationDialogBuilder builder = new ChooseObservationSiteLocationDialogBuilder(this);
            builder.setDialogId(id);
            builder.setDialogTitle(title + "のシアターの場所"); // XXX strings.xml
            builder.setOnChooseDataListener(new OnChooseDataListener<ObservationSiteLocation>() {
                @Override
                public void onChooseData(ObservationSiteLocation data) {
                    if (isMaster) {
                        masterObservationSiteLocation = data;
                        refreshMasterTheaterView();
                    } else {
                        secondObservationSiteLocation = data;
                        refreshSecondTheaterView();
                    }
                }
            });
            return builder.create();
        }
        return super.onCreateDialog(id, bundle);
    }

    private void refreshBaseCalendar(Date baseDate) {
        this.baseDate = baseDate;

        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.setTime(baseDate);

        masterCurrentCalendar = DateTimeUtils.toSameDateTime(baseCalendar, masterObservationSiteLocation.getTimeZone());
        secondCurrentCalendar = DateTimeUtils.toSameDateTime(baseCalendar, secondObservationSiteLocation.getTimeZone());
    }

    private void refreshMasterTheaterView() {
        masterTheaterView.configureObservationSiteLocation(masterObservationSiteLocation.getLongitude(), masterObservationSiteLocation.getLatitude(), masterCurrentCalendar);
    }

    private void refreshSecondTheaterView() {
        secondTheaterView.configureObservationSiteLocation(secondObservationSiteLocation.getLongitude(), secondObservationSiteLocation.getLatitude(), secondCurrentCalendar);
    }

    private void changeScreenOrientation() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            lockScreenOrientation();
            Toast.makeText(getApplicationContext(), "画面回転をロックしました", Toast.LENGTH_SHORT).show(); // XXX strings.xml
        } else {
            unlockScreenOrientation();
            Toast.makeText(getApplicationContext(), "画面回転のロックを解除しました", Toast.LENGTH_SHORT).show(); // XXX strings.xml
        }
    }

    private void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    private void lockScreenOrientation() {
        switch (getDisplayRotation()) {
        case Surface.ROTATION_0:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            break;
        case Surface.ROTATION_180:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            break;
        case Surface.ROTATION_90:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            break;
        case Surface.ROTATION_270:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            break;
        }
    }

    private int getDisplayRotation() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getRotation();
    }

    private void setSecondTheaterViewVisible(boolean visible) {
        if (visible) {
            secondTheaterView.setVisibility(View.VISIBLE);
            switchShowSecondTheaterButton.setImageLevel(1);
            chooseSecondObservationSiteLocationButton.setEnabled(true);
        } else {
            secondTheaterView.setVisibility(View.GONE);
            switchShowSecondTheaterButton.setImageLevel(0);
            chooseSecondObservationSiteLocationButton.setEnabled(false);
        }
    }
}
