/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import java.util.List;

import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.ChooseObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
 */
@Deprecated
public class ChooseLocationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        setContentView(listView);

        listView.setAdapter(new CustomArrayAdapter(this, ChooseObservationSiteLocationResolver.getObservationSiteLocations()));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(getClass(), "fuga");
                finish();
            }
        });
    }

    private class CustomArrayAdapter extends ArrayAdapter<ObservationSiteLocation> {
        private final LayoutInflater layoutInflater;

        public CustomArrayAdapter(Context context, List<ObservationSiteLocation> list) {
            super(context, -1/* textViewResourceId. 不要 */, list);
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckedTextView checkedTextView;
            if (convertView == null) {
                checkedTextView = (CheckedTextView) layoutInflater.inflate(android.R.layout.simple_list_item_single_choice, null);
            } else {
                checkedTextView = (CheckedTextView) convertView;
            }
            checkedTextView.setText(getItem(position).getName());
            return checkedTextView;
        }
    }
}