package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.text.DecimalFormat;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver.ObservationSiteLocationResolverListener;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver.ObservationSiteLocationResolverType;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.ObservationSiteLocationGpsResolver;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResolveObservationSiteByGpsActivity extends Activity {

    private IObservationSiteLocationResolver observationSiteLocationResolver;
    private ProgressDialog progressDialog;

    private TextView tv1;
    private Button resolveLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_observation_site);

        tv1 = (TextView) findViewById(R.id.textView1);

        resolveLocationButton = (Button) findViewById(R.id.resolveLocationButton);
        resolveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // リゾルバの再開
                observationSiteLocationResolver.resume();
            }
        });

        observationSiteLocationResolver = new ObservationSiteLocationGpsResolver(this, ObservationSiteLocationResolverType.GPS);
        observationSiteLocationResolver.setObservationSiteLocationResolverListener(new ObservationSiteLocationResolverListener() {
            private DecimalFormat nf;
            {
                nf = new DecimalFormat("000.0");
                nf.setPositivePrefix("+");
                nf.setNegativePrefix("-");
            }

            @Override
            public void onResolveObservationSiteLocation(int index, ObservationSiteLocation location) {
                observationSiteLocationResolver.pause();

                String text = String.format("緯度：%s\n経度：%s\n高度：%s", //
                        nf.format(location.getLatitude()),  //
                        nf.format(location.getLongitude()), //
                        nf.format(location.getAltitude()));
                tv1.setText(text);
            }

            @Override
            public void onNotAvailableLocationProvider(int index) {
                Toast.makeText(getApplicationContext(), "位置情報プロバイダを利用できません.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartResolveObservationSiteLocation(int index) {
                progressDialog = ProgressDialog.show(ResolveObservationSiteByGpsActivity.this, "位置情報の取得", "現在位置を取得中です"); // XXX strings.xml
            }

            @Override
            public void onStopResolveObservationSiteLocation(int index) {
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        observationSiteLocationResolver = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // リゾルバの停止
        observationSiteLocationResolver.pause();
    }
}
