/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.DateTimePickerDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.view.AstronomicalTheaterView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
    private static final int DIALOG_CHOOSE_NOW = 1; // 現在時刻の選択

    private AstronomicalTheaterView theaterMasterView;
    private AstronomicalTheaterView theaterSecondView;

    private ImageButton switchShowSecondTheaterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.content_astronomical_theater);

        theaterMasterView = (AstronomicalTheaterView) findViewById(R.id.astronomicalTheaterMasterView);
        theaterSecondView = (AstronomicalTheaterView) findViewById(R.id.astronomicalTheaterSecondView);

        switchShowSecondTheaterButton = (ImageButton) findViewById(R.id.switchShowSecondTheaterButton);

        theaterMasterView.setup();
        theaterSecondView.setup();

        setSecondaryTheaterVisible(false); // 最初はセカンドパネルは非表示

        findViewById(R.id.zoomControls).setOnClickListener(this);
        findViewById(R.id.chooseMagnitudeButton).setOnClickListener(this);
        findViewById(R.id.chooseTodayButton).setOnClickListener(this);
        findViewById(R.id.chooseLocationButton).setOnClickListener(this);
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

        theaterMasterView.resume();
        theaterSecondView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        theaterMasterView.pause();
        theaterSecondView.pause();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        String name;
        switch (view.getId()) {
        case R.id.zoomControls:
            name = "zoomControls";
            break;
        case R.id.chooseMagnitudeButton:
            name = "chooseMagnitudeButton";
            break;

        case R.id.chooseTodayButton:
            showDialog(DIALOG_CHOOSE_NOW);
            return;

        case R.id.chooseLocationButton:
            intent = new Intent(this, ChooseLocationActivity.class);
            startActivity(intent);
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
            setSecondaryTheaterVisible(theaterSecondView.getVisibility() != View.VISIBLE);
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
        if (id == DIALOG_CHOOSE_NOW) {
            // 現在時刻の選択ダイアログ
            DateTimePickerDialogBuilder builder = new DateTimePickerDialogBuilder(this);
            builder.setOnClickPositiveButtonListener(new DateTimePickerDialogBuilder.OnClickPositiveButtonListener() {
                @Override
                public void onClickPositiveButtonListener(Date date) {
                    Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();
                    removeDialog(id);
                }
            });
            builder.setOnClickNegativeButtonListener(new DateTimePickerDialogBuilder.OnClickNegativeButtonListener() {
                @Override
                public void onClickNegativeButtonListener() {
                    removeDialog(id);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        return super.onCreateDialog(id, bundle);
    }

    private void setSecondaryTheaterVisible(boolean visible) {
        if (visible) {
            theaterSecondView.setVisibility(View.VISIBLE);
            switchShowSecondTheaterButton.setImageLevel(1);
        } else {
            theaterSecondView.setVisibility(View.GONE);
            switchShowSecondTheaterButton.setImageLevel(0);
        }
    }
}
