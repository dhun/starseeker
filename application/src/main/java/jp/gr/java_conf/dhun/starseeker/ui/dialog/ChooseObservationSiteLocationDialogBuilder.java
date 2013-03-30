/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.logic.observationsite.location.ChooseObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import android.app.Activity;
import android.content.DialogInterface;

/**
 * 観測地点の位置を選択するダイアログのビルダ
 * 
 * @author j_hosoya
 * 
 */
public class ChooseObservationSiteLocationDialogBuilder extends AbstractChooseDataDialogBuilder<ObservationSiteLocation> {

    private final List<ObservationSiteLocation> locations;
    private final List<String> locationNames;

    /**
     * コンストラクタ
     */
    public ChooseObservationSiteLocationDialogBuilder(Activity activity) {
        super(activity);
        dialogTitle = "左側のシアターの場所"; // XXX strings.xml

        locations = new ArrayList<ObservationSiteLocation>();
        setupLocations();

        locationNames = new ArrayList<String>();
        for (ObservationSiteLocation location : locations) {
            locationNames.add(location.getName());
        }
    }

    private void setupLocations() {
        // FIXME ここでGPSリゾルバを追加
        // FIXME マニュアルリゾルバも追加する？
        this.locations.addAll(ChooseObservationSiteLocationResolver.getObservationSiteLocations());
    }

    @Override
    protected void setupBuilder() {
        super.setupBuilder();

        builder.setItems(locationNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onChooseDataListener.onChooseData(locations.get(which));
            }
        });
    }
}
