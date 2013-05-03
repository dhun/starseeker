package jp.gr.java_conf.dhun.starseeker.ui.activity;

import jp.gr.java_conf.dhun.starseeker.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_title);
        this.findViewById(R.id.rootContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AstronomicalTheaterActivity.class));
            }
        });
    }

    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // this.startActivity(new Intent(this, AstronomicalTheaterActivity.class));
    // return true;
    // }
}
