/**
 * 
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.ChooseObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.ui.dialog.listener.OnChooseDataListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 観測地点を選択するダイアログのビルダ
 * 
 * @author j_hosoya
 * 
 */
public class ChooseLocationDialogBuilder {

    // private final Context context;
    private final AlertDialog.Builder builder;
    private final List<ObservationSiteLocation> locations;
    private final List<String> locationNames;

    private OnChooseDataListener<ObservationSiteLocation> onChooseDataListener;
    private DialogInterface.OnClickListener onCancelListener;

    public ChooseLocationDialogBuilder(Context context) {
        // this.context = context;
        this.builder = new AlertDialog.Builder(context);

        this.locations = new ArrayList<ObservationSiteLocation>();
        setupLocations();

        this.locationNames = new ArrayList<String>();
        setupLocationNames();
    }

    private void setupLocations() {
        this.locations.addAll(ChooseObservationSiteLocationResolver.getObservationSiteLocations());
    }

    private void setupLocationNames() {
        for (ObservationSiteLocation location : locations) {
            locationNames.add(location.getName());
        }
    }

    public AlertDialog create() {
        if (null == onChooseDataListener) {
            throw new IllegalStateException("onChooseDataListener must be null.");
        }

        builder.setTitle("左側のシアターの場所"); // XXX strings.xml
        builder.setCancelable(true);

        builder.setItems(locationNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onChooseDataListener.onChooseData(locations.get(which));
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onChooseDataListener.onChooseData(null);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, onCancelListener);
        return builder.create();
    }

    public void setOnChooseDataListener(OnChooseDataListener<ObservationSiteLocation> listener) {
        this.onChooseDataListener = listener;
    }

    public void setOnCancelListener(DialogInterface.OnClickListener listener) {
        this.onCancelListener = listener;
    }
}
