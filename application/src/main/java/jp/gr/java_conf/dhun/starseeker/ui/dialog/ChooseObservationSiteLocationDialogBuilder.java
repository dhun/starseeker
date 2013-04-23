/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver.ObservationSiteLocationResolverType;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.ObservationSiteLocationChooseResolver;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.ObservationSiteLocationGpsResolver;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.ObservationSiteLocationDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;

/**
 * 観測地点の位置を解決するリゾルバを選択するダイアログのビルダ
 * 
 * @author j_hosoya
 * 
 */
public class ChooseObservationSiteLocationDialogBuilder extends AbstractChooseDataDialogBuilder<IObservationSiteLocationResolver> {

    private final Context context;

    private final List<ObservationSiteLocation> locations;
    private Integer initialLocationId;

    /**
     * コンストラクタ
     */
    public ChooseObservationSiteLocationDialogBuilder(Activity activity) {
        super(activity);

        this.context = activity.getApplicationContext();
        this.locations = new ArrayList<ObservationSiteLocation>();
        setupLocations();
    }

    private void setupLocations() {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            ObservationSiteLocationDao dao = new ObservationSiteLocationDao(db);
            locations.addAll(dao.listUseLocationManagerObservationSiteLocations());
            locations.addAll(dao.listChooseObservationSiteLocation());
            // FIXME マニュアルリゾルバも追加する？

        } finally {
            db.close();
        }
    }

    /**
     * 初期表示する観測地点の位置のIDを設定します
     */
    public void setInitialLocationId(Integer id) {
        this.initialLocationId = id;
    }

    @Override
    protected void setupBuilder() {
        super.setupBuilder();

        int checkedItem = -1;
        List<String> locationNames = new ArrayList<String>();
        for (int i = 0; i < locations.size(); i++) {
            ObservationSiteLocation location = locations.get(i);
            locationNames.add(location.getName());
            if (location.getId().equals(initialLocationId)) {
                checkedItem = i;
            }
        }

        builder.setSingleChoiceItems(locationNames.toArray(new String[0]), checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ObservationSiteLocation location = locations.get(which);
                final IObservationSiteLocationResolver resolver;

                switch (location.getId()) {
                case ObservationSiteLocation.ID_GPS:
                    resolver = new ObservationSiteLocationGpsResolver(context, ObservationSiteLocationResolverType.GPS);
                    break;

                case ObservationSiteLocation.ID_NETWORK:
                    resolver = new ObservationSiteLocationGpsResolver(context, ObservationSiteLocationResolverType.NETWORK);
                    break;

                default:
                    ObservationSiteLocationChooseResolver chooseResolver = new ObservationSiteLocationChooseResolver(context);
                    chooseResolver.setObservationSiteLocation(location);
                    resolver = chooseResolver;
                }

                onChooseDataListener.onChooseData(resolver);
                dialog.dismiss();
            }
        });
    }
}
