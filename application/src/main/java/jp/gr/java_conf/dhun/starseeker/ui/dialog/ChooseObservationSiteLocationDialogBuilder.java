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
    private ObservationSiteLocation initialLocation;

    /**
     * コンストラクタ
     */
    public ChooseObservationSiteLocationDialogBuilder(Activity activity) {
        super(activity);

        locations = new ArrayList<ObservationSiteLocation>();
        setupLocations();
    }

    private void setupLocations() {
        // FIXME ここでGPSリゾルバを追加
        // FIXME マニュアルリゾルバも追加する？
        this.locations.addAll(ChooseObservationSiteLocationResolver.getObservationSiteLocations());
    }

    @Override
    protected void setupBuilder() {
        super.setupBuilder();

        int checkedItem = -1;
        List<String> locationNames = new ArrayList<String>();
        for (int i = 0; i < locations.size(); i++) {
            ObservationSiteLocation location = locations.get(i);
            locationNames.add(location.getName());
            if (null != initialLocation && initialLocation.getId() == location.getId()) {
                checkedItem = i;
            }
        }

        builder.setSingleChoiceItems(locationNames.toArray(new String[0]), checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onChooseDataListener.onChooseData(locations.get(which));
                dialog.dismiss();
            }
        });
    }

    /**
     * 初期表示する観測地点の位置を設定します
     */
    public void setInitialLocation(ObservationSiteLocation initialLocation) {
        this.initialLocation = initialLocation;
    }
}
