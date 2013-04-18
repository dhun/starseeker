package jp.gr.java_conf.dhun.starseeker.ui.activity;

import jp.gr.java_conf.dhun.starseeker.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DevelopmentMenuActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development_menu);

        findViewById(R.id.resolveTerminalStateButton).setOnClickListener(this);
        findViewById(R.id.resolveObservationSiteByGpsActivity).setOnClickListener(this);
        findViewById(R.id.resolveObservationSiteByManualActivity).setOnClickListener(this);
        findViewById(R.id.surfaceViewActivity).setOnClickListener(this);
        findViewById(R.id.astronomicalTheaterActivity).setOnClickListener(this);

        // ((Button) findViewById(R.id.astronomicalTheaterActivity)).performClick();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
        case R.id.resolveTerminalStateButton:
            intent = new Intent(this, ResolveTerminalStateActivity.class);
            startActivity(intent);
            break;
        case R.id.resolveObservationSiteByGpsActivity:
            intent = new Intent(this, ResolveObservationSiteByGpsActivity.class);
            startActivity(intent);
            break;
        case R.id.resolveObservationSiteByManualActivity:
            intent = new Intent(this, ResolveObservationSiteByChooseActivity.class);
            startActivity(intent);
            break;
        case R.id.surfaceViewActivity:
            intent = new Intent(this, SurfaceViewActivity.class);
            startActivity(intent);
            break;
        case R.id.astronomicalTheaterActivity:
            intent = new Intent(this, AstronomicalTheaterActivity.class);
            startActivity(intent);
            break;
        default:
            throw new IllegalStateException("unknown view.id. value=[" + view.getId() + "]");
        }
    }
}
