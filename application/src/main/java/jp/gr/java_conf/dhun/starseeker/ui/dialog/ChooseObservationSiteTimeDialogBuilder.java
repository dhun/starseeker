/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.Calendar;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * 観測地点の日時を選択するダイアログのビルダ
 * 
 * @author jun
 * 
 */
public class ChooseObservationSiteTimeDialogBuilder extends AbstractChooseDataDialogBuilder<Date> {

    private Date initialDateTime = new Date();

    private DatePicker datePicker;
    private TimePicker timePicker;

    /**
     * コンストラクタ
     */
    public ChooseObservationSiteTimeDialogBuilder(Activity activity) {
        super(activity);
    }

    @Override
    protected void setupBuilder() {
        super.setupBuilder();

        ViewGroup contentView = (ViewGroup) View.inflate(activity, R.layout.dialog_choose_observation_site_time, null);

        datePicker = (DatePicker) contentView.findViewById(R.id.datePicker);

        timePicker = (TimePicker) contentView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        Button setNowButton = (Button) contentView.findViewById(R.id.setNowButton);
        setNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentDateTime(new Date());
            }
        });

        setCurrentDateTime(initialDateTime);

        builder.setView(contentView);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onChooseDataListener.onChooseData(getCurrentDateTime());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
    }

    private void setCurrentDateTime(Date currentDateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDateTime);

        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
    }

    private Date getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.clear();

        cal.set(Calendar.YEAR, datePicker.getYear());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

        cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());

        return cal.getTime();
    }

    /**
     * 初期表示する日時を設定します
     */
    public void setInitialDateTime(Date initialDateTime) {
        this.initialDateTime = initialDateTime;
    }
}
