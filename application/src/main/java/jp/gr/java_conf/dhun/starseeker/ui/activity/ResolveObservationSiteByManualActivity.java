package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.text.DecimalFormat;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.IObservationSiteLocationResolver.ObservationSiteLocationResolverListener;
import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.ManualObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ResolveObservationSiteByManualActivity extends Activity {

    private IObservationSiteLocationResolver observationSiteLocationResolver;

    private TextView tv1;
    private Button resolveLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_observation_site);

        tv1 = (TextView) findViewById(R.id.textView1);

        observationSiteLocationResolver = new ManualObservationSiteLocationResolver();
        observationSiteLocationResolver.setObservationSiteLocationResolverListener(new ObservationSiteLocationResolverListener() {
            private DecimalFormat nf;
            {
                nf = new DecimalFormat("000.0");
                nf.setPositivePrefix("+");
                nf.setNegativePrefix("-");
            }

            @Override
            public void onResolveObservationSiteLocation(ObservationSiteLocation location) {
                observationSiteLocationResolver.pause();

                String text = String.format("緯度：%s\n経度：%s\n高度：%s", //
                        nf.format(location.getLatitude()),  //
                        nf.format(location.getLongitude()), //
                        nf.format(location.getAltitude()));
                tv1.setText(text);
            }

            @Override
            public void onNotAvailableLocationProvider() {
                Toast.makeText(getApplicationContext(), "位置情報プロバイダを利用できません.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartResolveObservationSiteLocation() {
            }

            @Override
            public void onStopResolveObservationSiteLocation() {
            }
        });

        final List<ObservationSiteLocation> locations = ManualObservationSiteLocationResolver.getLocations();

        resolveLocationButton = (Button) findViewById(R.id.resolveLocationButton);
        resolveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // リゾルバの再開
                observationSiteLocationResolver.resume(); // XXX 不要

                ListAdapter adapter = new ArrayAdapter<ObservationSiteLocation>(view.getContext(), android.R.layout.simple_list_item_1, locations) {
                    private final LayoutInflater layoutInflater = (LayoutInflater) ResolveObservationSiteByManualActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
                        }

                        ObservationSiteLocation location = this.getItem(position);

                        TextView textView = ((TextView) convertView.findViewById(android.R.id.text1));
                        textView.setText(location.getName());
                        textView.setTextColor(Color.BLACK);

                        return convertView;
                    }

                };

                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationSiteLocationResolver.setObservationSiteLocation(locations.get(which));
                    }
                };

                Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("観測地点の選択"); // XXX strings.xml
                builder.setAdapter(adapter, listener);
                builder.setCancelable(true);
                builder.show();
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
