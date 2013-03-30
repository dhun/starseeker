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
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
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

    private ObservationSiteLocation masterObservationSiteLocation;
    private ObservationSiteLocation secondObservationSiteLocation;

    private Calendar masterCurrentCalendar;
    private Calendar secondCurrentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.content_astronomical_theater);

        // ビューの取得
        masterTheaterView = (AstronomicalTheaterView) findViewById(R.id.masterTheaterView);
        secondTheaterView = (AstronomicalTheaterView) findViewById(R.id.secondTheaterView);
        switchShowSecondTheaterButton = (ImageButton) findViewById(R.id.switchShowSecondTheaterButton);

        // 天体シアターの設定
        masterObservationSiteLocation = ChooseObservationSiteLocationResolver.getObservationSiteLocations().get(0);
        secondObservationSiteLocation = ChooseObservationSiteLocationResolver.getObservationSiteLocations().get(8);

        masterCurrentCalendar = Calendar.getInstance();
        secondCurrentCalendar = Calendar.getInstance();

        masterTheaterView.setup();
        secondTheaterView.setup();

        setSecondaryTheaterVisible(false); // 最初はセカンドパネルは非表示

        // クリックイベントの設定
        findViewById(R.id.zoomControls).setOnClickListener(this);
        findViewById(R.id.chooseMagnitudeButton).setOnClickListener(this);
        findViewById(R.id.chooseObservationSiteTimeButton).setOnClickListener(this);
        findViewById(R.id.chooseMasterObservationSiteLocationButton).setOnClickListener(this);
        findViewById(R.id.chooseSecondObservationSiteLocationButton).setOnClickListener(this);
        findViewById(R.id.settingsButton).setOnClickListener(this);
        findViewById(R.id.switchStarLocateIndicatorButton).setOnClickListener(this);
        findViewById(R.id.switchLockDisplayRotateButton).setOnClickListener(this);
        findViewById(R.id.photographButton).setOnClickListener(this);
        findViewById(R.id.imageButton2).setOnClickListener(this);
        switchShowSecondTheaterButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        masterTheaterView.resume();
        secondTheaterView.resume();
    }

    @Override
    protected void onPause() {
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
            name = "switchLockDisplayRotateButton";
            break;
        case R.id.photographButton:
            name = "photographButton";
            break;

        case R.id.switchShowSecondTheaterButton:
            setSecondaryTheaterVisible(secondTheaterView.getVisibility() != View.VISIBLE);
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
            builder.setOnChooseDataListener(new OnChooseDataListener<Date>() {
                @Override
                public void onChooseData(Date data) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data);

                    masterCurrentCalendar = DateTimeUtils.toSameDateTime(cal, masterObservationSiteLocation.getTimeZone());
                    secondCurrentCalendar = DateTimeUtils.toSameDateTime(cal, secondObservationSiteLocation.getTimeZone());

                    masterTheaterView.configureObservationSiteLocation(masterObservationSiteLocation.getLongitude(), masterObservationSiteLocation.getLatitude(), masterCurrentCalendar);
                    secondTheaterView.configureObservationSiteLocation(secondObservationSiteLocation.getLongitude(), secondObservationSiteLocation.getLatitude(), secondCurrentCalendar);
                }
            });
            return builder.create();
        }

        if (id == DIALOG_CHOOSE_OBSERVATION_SITE_LOCATION) {
            // 観測地点の位置選択ダイアログ
            final boolean isMaster = bundle.getBoolean("isMaster");
            ChooseObservationSiteLocationDialogBuilder builder = new ChooseObservationSiteLocationDialogBuilder(this);
            builder.setDialogId(id);
            builder.setOnChooseDataListener(new OnChooseDataListener<ObservationSiteLocation>() {
                @Override
                public void onChooseData(ObservationSiteLocation data) {
                    if (isMaster) {
                        masterObservationSiteLocation = data;
                        masterTheaterView.configureObservationSiteLocation(masterObservationSiteLocation.getLongitude(), masterObservationSiteLocation.getLatitude(), masterCurrentCalendar);
                    } else {
                        secondObservationSiteLocation = data;
                        secondTheaterView.configureObservationSiteLocation(secondObservationSiteLocation.getLongitude(), secondObservationSiteLocation.getLatitude(), secondCurrentCalendar);
                    }
                }
            });
            return builder.create();
        }
        return super.onCreateDialog(id, bundle);
    }

    private void setSecondaryTheaterVisible(boolean visible) {
        if (visible) {
            secondTheaterView.setVisibility(View.VISIBLE);
            switchShowSecondTheaterButton.setImageLevel(1);
        } else {
            secondTheaterView.setVisibility(View.GONE);
            switchShowSecondTheaterButton.setImageLevel(0);
        }
    }
}
