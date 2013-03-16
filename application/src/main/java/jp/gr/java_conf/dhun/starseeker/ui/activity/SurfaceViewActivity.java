/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import jp.gr.java_conf.dhun.starseeker.ui.view.AstronomicalTheaterView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * @author jun
 * 
 */
public class SurfaceViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        this.setContentView(rootLayout);

        AstronomicalTheaterView theaterView = new AstronomicalTheaterView(this);
        rootLayout.addView(theaterView);
    }
}
