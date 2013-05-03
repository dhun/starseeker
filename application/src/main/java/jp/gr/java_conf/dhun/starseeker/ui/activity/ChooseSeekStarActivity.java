/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import jp.gr.java_conf.dhun.starseeker.ui.dialog.ChooseSeekTargetDialogBuilder;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.listener.OnChooseDataListener;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

/**
 * @author jun
 * 
 */
public class ChooseSeekStarActivity extends Activity implements OnChooseDataListener<SeekTarget<?>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseSeekTargetDialogBuilder builder = new ChooseSeekTargetDialogBuilder(ChooseSeekStarActivity.this);
                builder.setDialogId(1);
                builder.setDialogTitle("星の選択");
                builder.setOnChooseDataListener(ChooseSeekStarActivity.this);
                builder.create().show();
            }
        });

        this.addContentView(button, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onChooseData(SeekTarget<?> data) {
        // TODO 自動生成されたメソッド・スタブ

    }
}
