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

    private AstronomicalTheaterView theaterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        this.setContentView(rootLayout);

        theaterView = new AstronomicalTheaterView(this);
        theaterView.initialize();

        rootLayout.addView(theaterView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        theaterView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        theaterView.pause();
    }
}
