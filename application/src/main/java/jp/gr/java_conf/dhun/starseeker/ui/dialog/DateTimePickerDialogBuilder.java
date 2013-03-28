/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.Calendar;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * 日時を選択するダイアログのビルダ
 * 
 * @author jun
 * 
 */
public class DateTimePickerDialogBuilder {

    private final Context context;

    private Date initialDateTime = new Date();
    private OnClickPositiveButtonListener onClickPositiveButtonListener;
    private OnClickNegativeButtonListener onClickNegativeButtonListener;

    private DatePicker datePicker;
    private TimePicker timePicker;

    public DateTimePickerDialogBuilder(Context context) {
        this.context = context;
    }

    public AlertDialog create() {
        if (null == onClickPositiveButtonListener && null == onClickNegativeButtonListener) {
            throw new IllegalStateException("initialDateTime must be null.");
        }

        ViewGroup contentView = (ViewGroup) View.inflate(context, R.layout.dialog_choose_datetime, null);

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

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("表示する日時の指定"); // XXX strings.xml
        builder.setView(contentView);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != onClickPositiveButtonListener) {
                    onClickPositiveButtonListener.onClickPositiveButtonListener(getCurrentDateTime());
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != onClickNegativeButtonListener) {
                    onClickNegativeButtonListener.onClickNegativeButtonListener();
                }
            }
        });
        return builder.create();
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

    public void setInitialDateTime(Date initialDateTime) {
        this.initialDateTime = initialDateTime;
    }

    public void setOnClickPositiveButtonListener(OnClickPositiveButtonListener listener) {
        this.onClickPositiveButtonListener = listener;
    }

    public void setOnClickNegativeButtonListener(OnClickNegativeButtonListener listener) {
        this.onClickNegativeButtonListener = listener;
    }

    public interface OnClickPositiveButtonListener {
        void onClickPositiveButtonListener(Date date);
    }

    public interface OnClickNegativeButtonListener {
        void onClickNegativeButtonListener();
    }
}
