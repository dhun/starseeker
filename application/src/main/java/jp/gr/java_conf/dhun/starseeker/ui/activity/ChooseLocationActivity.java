/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.ChooseObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

/**
 * 観測地点の選択アクティビティ
 * 
 * @author jun
 * 
 */
public class ChooseLocationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.listview_sample);

        // ListView listView = (ListView) findViewById(R.id.listview);
        ListView listView = new ListView(this);
        setContentView(listView);

        listView.setAdapter(new ArrayAdapter<ObservationSiteLocation>(
                this,
                android.R.layout.simple_list_item_single_choice,
                ChooseObservationSiteLocationResolver.getObservationSiteLocations()
                ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        CheckedTextView checkedTextView = (CheckedTextView) super.getView(position, convertView, parent);
                        checkedTextView.setText(getItem(position).getName());
                        return checkedTextView;
                    }
                });
        // listView.setAdapter(new CustomArrayAdapter(this, ChooseObservationSiteLocationResolver.getObservationSiteLocations()));

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LogUtils.d(getClass(), "fuga");
                finish();
            }
        });
    }

    // private class CustomArrayAdapter extends ArrayAdapter<ObservationSiteLocation> {
    // public CustomArrayAdapter(Context context, List<ObservationSiteLocation> list) {
    // super(context, android.R.layout.simple_list_item_single_choice, list);
    // }
    //
    // @Override
    // public View getView(int position, View convertView, ViewGroup parent) {
    // CheckedTextView checkedTextView;
    // if (convertView == null) {
    // checkedTextView = (CheckedTextView) super.getView(position, convertView, null);
    // } else {
    // checkedTextView = (CheckedTextView) convertView;
    // }
    // checkedTextView.setText(getItem(position).getName());
    // return convertView;
    // }
    // }
}