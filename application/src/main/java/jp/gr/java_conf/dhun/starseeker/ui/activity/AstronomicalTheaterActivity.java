/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.util.Calendar;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.AbstractObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.StarSeekerConfigDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarSeekerConfig;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseExtractStarMagnitudeDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseObservationSiteLocationDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseObservationSiteTimeDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseSeekTargetDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.listener.OnChooseDataListener;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget;
import jp.gr.java_conf.dhun.starseeker.ui.view.AstronomicalTheaterView;
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
    private static final int DIALOG_CHOOSE_OBSERVATION_SITE_TIME = 1;           // 観測地点の時刻選択ダイアログ
    private static final int DIALOG_CHOOSE_OBSERVATION_SITE_LOCATION = 2;       // 観測地点の位置選択ダイアログ
    private static final int DIALOG_CHOOSE_EXTRACT_LOWER_STAR_MAGNITUDE = 3;    // 抽出する等級の選択ダイアログ
    private static final int DIALOG_CHOOSE_SEEK_TARGET_ = 4;                    // 探索する星の選択ダイアログ

    // ビュー
    private AstronomicalTheaterView masterTheaterView;
    private AstronomicalTheaterView secondTheaterView;
    private ImageButton switchShowSecondTheaterButton;
    private ImageButton chooseSecondObservationSiteLocationButton;
    private ImageButton switchLockDisplayRotateButton;

    // 設定
    private StarSeekerConfigDao configDao;
    private StarSeekerConfig config;

    private Date baseDate;
    private IObservationSiteLocationResolver masterObservationSiteLocationResolver;
    private IObservationSiteLocationResolver secondObservationSiteLocationResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.v(getClass(), "onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_astronomical_theater);

        // ビューの取得
        masterTheaterView = (AstronomicalTheaterView) findViewById(R.id.masterTheaterView);
        secondTheaterView = (AstronomicalTheaterView) findViewById(R.id.secondTheaterView);
        switchShowSecondTheaterButton = (ImageButton) findViewById(R.id.switchShowSecondTheaterButton);
        chooseSecondObservationSiteLocationButton = (ImageButton) findViewById(R.id.chooseSecondObservationSiteLocationButton);
        switchLockDisplayRotateButton = (ImageButton) findViewById(R.id.switchLockDisplayRotateButton);

        // 設定の復元
        configDao = new StarSeekerConfigDao(getApplicationContext());
        config = configDao.findOrDefault();
        config.setCoordinatesCalculateBaseDate(new Date()); // 画面起動時はシステム日時
        // config.setCoordinatesCalculateBaseDate(new Date(2013 - 1900, 3, 17, 1, 29)); // FIXME 時刻を固定するときに解放
        // config.setCoordinatesCalculateBaseDate(new Date(2000 - 1900, 0, 1, 21, 00)); // FIXME 時刻を固定するときに解放

        config.setSecondAstronomicalTheaterVisible(false);  // 初期表示ではセカンドビューは非表示

        masterObservationSiteLocationResolver = AbstractObservationSiteLocationResolver.newResolver(this, config.getMasterObservationSiteLocationId());
        secondObservationSiteLocationResolver = AbstractObservationSiteLocationResolver.newResolver(this, config.getSecondObservationSiteLocationId());

        Object nonConfigObject = getLastNonConfigurationInstance();
        if (null != nonConfigObject) {
            NonConfigurationInstance nonConfig = (NonConfigurationInstance) nonConfigObject;
            config.setCoordinatesCalculateBaseDate(nonConfig.baseDate); // 画面回転時は直近値を復元
        }

        // 天体シアターの設定
        baseDate = new Date();
        refreshBaseCalendar(config.getCoordinatesCalculateBaseDate());    // 基準日時はシステム日時

        masterTheaterView.initialize();
        secondTheaterView.initialize();

        setSecondTheaterViewVisible(config.isSecondAstronomicalTheaterVisible()); // セカンドパネルの表示／非表示
        changeLockScreenRotate(config.isLockScreenRotate());                      // 画面の回転ロック

        masterTheaterView.configureExtractLowerstarMagnitude(config.getExtractLowerStarMagnitude());
        secondTheaterView.configureExtractLowerstarMagnitude(config.getExtractLowerStarMagnitude());

        setMasterObservationCondition();
        setSecondObservationCondition();

        // クリックイベントの設定
        // findViewById(R.id.zoomControls).setOnClickListener(this);
        findViewById(R.id.chooseMagnitudeButton).setOnClickListener(this);
        findViewById(R.id.chooseObservationSiteTimeButton).setOnClickListener(this);
        findViewById(R.id.chooseMasterObservationSiteLocationButton).setOnClickListener(this);
        // findViewById(R.id.settingsButton).setOnClickListener(this);
        findViewById(R.id.switchStarLocateIndicatorButton).setOnClickListener(this);
        // findViewById(R.id.photographButton).setOnClickListener(this);
        // findViewById(R.id.imageButton2).setOnClickListener(this);
        switchShowSecondTheaterButton.setOnClickListener(this);
        chooseSecondObservationSiteLocationButton.setOnClickListener(this);
        switchLockDisplayRotateButton.setOnClickListener(this);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        NonConfigurationInstance result = new NonConfigurationInstance();
        result.baseDate = this.baseDate;
        return result;
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

        IObservationSiteLocationResolver resolver;

        resolver = masterObservationSiteLocationResolver;
        masterObservationSiteLocationResolver = null;
        masterTheaterView.refresh(resolver);
        masterTheaterView.resume();

        resolver = secondObservationSiteLocationResolver;
        secondObservationSiteLocationResolver = null;
        secondTheaterView.refresh(resolver);
        secondTheaterView.resume();
    }

    @Override
    protected void onPause() {
        LogUtils.v(getClass(), "onPause");
        super.onPause();

        masterTheaterView.pause();
        secondTheaterView.pause();

        // 設定の退避
        configDao.store(config);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.chooseMagnitudeButton:
            showDialog(DIALOG_CHOOSE_EXTRACT_LOWER_STAR_MAGNITUDE);
            return;

        case R.id.chooseObservationSiteTimeButton:
            showDialog(DIALOG_CHOOSE_OBSERVATION_SITE_TIME);
            return;

        case R.id.chooseMasterObservationSiteLocationButton:
        case R.id.chooseSecondObservationSiteLocationButton:
            Bundle bundle = new Bundle();
            bundle.putBoolean("isMaster", view.getId() == R.id.chooseMasterObservationSiteLocationButton);
            showDialog(DIALOG_CHOOSE_OBSERVATION_SITE_LOCATION, bundle);
            return;

            // case R.id.settingsButton:
            // name = "settingsButton";
            // break;

        case R.id.switchStarLocateIndicatorButton:
            showDialog(DIALOG_CHOOSE_SEEK_TARGET_);
            return;

        case R.id.switchLockDisplayRotateButton:
            changeLockScreenRotateWithToast(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            return;

            // case R.id.photographButton:
            // name = "photographButton";
            // break;

        case R.id.switchShowSecondTheaterButton:
            setSecondTheaterViewVisibleWithRefresh(secondTheaterView.getVisibility() != View.VISIBLE);
            return;

            // case R.id.imageButton2:
            // name = "imageButton2";
            // break;

        default:
            throw new IllegalStateException("unknown view.id. value=[" + view.getId() + "]");
        }
    }

    @Override
    protected Dialog onCreateDialog(final int id, Bundle bundle) {
        if (id == DIALOG_CHOOSE_SEEK_TARGET_) {
            ChooseSeekTargetDialogBuilder builder = new ChooseSeekTargetDialogBuilder(this);
            builder.setDialogId(id);
            builder.setOnChooseDataListener(new OnChooseDataListener<SeekTarget<?>>() {
                @Override
                public void onChooseData(SeekTarget<?> data) {
                    config.setSeekTargetType(data.getSeekTargetType());
                    config.setSeekTargetId(data.getSeekTargetId());

                    masterTheaterView.configureSeektarget(data).refresh();
                    secondTheaterView.configureSeektarget(data).refresh();
                }
            });
            return builder.create();
        }

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
            if (isMaster) {
                builder.setInitialLocationId(config.getMasterObservationSiteLocationId());
            } else {
                builder.setInitialLocationId(config.getSecondObservationSiteLocationId());
            }
            builder.setOnChooseDataListener(new OnChooseDataListener<IObservationSiteLocationResolver>() {
                @Override
                public void onChooseData(IObservationSiteLocationResolver data) {
                    if (isMaster) {
                        config.setMasterObservationSiteLocationId(data.getObservationSiteLocationId());
                        masterTheaterView.refresh(data);
                    } else {
                        config.setSecondObservationSiteLocationId(data.getObservationSiteLocationId());
                        secondTheaterView.refresh(data);
                    }
                }
            });
            return builder.create();
        }

        if (id == DIALOG_CHOOSE_EXTRACT_LOWER_STAR_MAGNITUDE) {
            ChooseExtractStarMagnitudeDialogBuilder builder = new ChooseExtractStarMagnitudeDialogBuilder(this);
            builder.setDialogId(id);
            builder.setInitialLowerValue(config.getExtractLowerStarMagnitude());
            builder.setOnChooseDataListener(new OnChooseDataListener<Float>() {
                @Override
                public void onChooseData(Float data) {
                    config.setExtractLowerStarMagnitude(data);
                    masterTheaterView.configureExtractLowerstarMagnitude(data).refresh();
                    secondTheaterView.configureExtractLowerstarMagnitude(data).refresh();
                }
            });
            return builder.create();
        }
        return super.onCreateDialog(id, bundle);
    }

    private void refreshBaseCalendar(Date baseDate) {
        this.baseDate = baseDate;
        this.config.setCoordinatesCalculateBaseDate(baseDate);

        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.setTime(baseDate);
    }

    private void setMasterObservationCondition() {
        masterTheaterView.configureObservationSiteLocation(config.getMasterObservationSiteLocationId(), baseDate);
    }

    private void refreshMasterTheaterView() {
        setMasterObservationCondition();
        masterTheaterView.refresh();
    }

    private void setSecondObservationCondition() {
        secondTheaterView.configureObservationSiteLocation(config.getSecondObservationSiteLocationId(), baseDate);
    }

    private void refreshSecondTheaterView() {
        setSecondObservationCondition();
        secondTheaterView.refresh();
    }

    private void changeLockScreenRotateWithToast(boolean lock) {
        changeLockScreenRotate(lock);
        if (lock) {
            Toast.makeText(getApplicationContext(), "画面を回転ロックしました", Toast.LENGTH_SHORT).show(); // XXX strings.xml
        } else {
            Toast.makeText(getApplicationContext(), "画面の回転ロックを解除しました", Toast.LENGTH_SHORT).show(); // XXX strings.xml
        }
    }

    private void changeLockScreenRotate(boolean lock) {
        if (lock) {
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
            switchLockDisplayRotateButton.setImageLevel(1);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            switchLockDisplayRotateButton.setImageLevel(0);
        }
        config.setLockScreenRotate(lock);
    }

    private int getDisplayRotation() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getRotation();
    }

    private void setSecondTheaterViewVisibleWithRefresh(boolean visible) {
        setSecondTheaterViewVisible(visible);
        if (visible) {
            secondTheaterView.refresh();
            secondTheaterView.resume();
        } else {
            secondTheaterView.pause();
        }
        config.setSecondAstronomicalTheaterVisible(visible);
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
        config.setSecondAstronomicalTheaterVisible(visible);
    }

    private class NonConfigurationInstance {
        public Date baseDate;
    }
}
