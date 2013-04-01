/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import jp.gr.java_conf.dhun.starseeker.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

/**
 * 抽出する星の等級を選択するダイアログのビルダ
 * 
 * @author jun
 * 
 */
public class ChooseExtractStarMagnitudeDialogBuilder extends AbstractChooseDataDialogBuilder<Float> {

    private Float initialLowerValue;

    private RatingBar ratingBar;

    /**
     * コンストラクタ
     */
    public ChooseExtractStarMagnitudeDialogBuilder(Activity activity) {
        super(activity);
        setDialogTitle("何等星まで表示するか"); // XXX strings.xml
    }

    @Override
    protected void setupBuilder() {
        super.setupBuilder();

        ViewGroup contentView = (ViewGroup) View.inflate(activity, R.layout.dialog_choose_extract_star_nagnitude, null);
        ratingBar = (RatingBar) contentView.findViewById(R.id.ratingBar);
        ratingBar.setRating(initialLowerValue);

        builder.setView(contentView);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onChooseDataListener.onChooseData(ratingBar.getRating());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
    }

    /**
     * 初期表示する下限値の値を設定します
     */
    public void setInitialLowerValue(Float initialLowerValue) {
        this.initialLowerValue = initialLowerValue;
    }
}
