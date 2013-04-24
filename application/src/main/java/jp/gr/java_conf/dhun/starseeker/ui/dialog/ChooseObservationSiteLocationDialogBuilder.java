/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.AbstractObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.ObservationSiteLocationDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 観測地点の位置を解決するリゾルバを選択するダイアログのビルダ
 * 
 * @author j_hosoya
 */
public class ChooseObservationSiteLocationDialogBuilder extends AbstractChooseDataDialogBuilder<IObservationSiteLocationResolver> {

    private final Context context;

    private final List<ObservationSiteLocation> locations;
    private Integer initialLocationId;

    private Toast warningToast;

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
            locations.addAll(dao.listLocationProviderObservationSiteLocations());
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
                // LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                final ObservationSiteLocation location = locations.get(which);
                final IObservationSiteLocationResolver resolver;

                // switch (location.getId()) {
                // case ObservationSiteLocation.ID_GPS:
                // if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // showWarningToast("GPSは現在利用できません.\nシステム設定を変更してください.");
                // return;
                // }
                // resolver = new ObservationSiteLocationGpsResolver(context, ObservationSiteLocationResolverType.GPS);
                // break;
                //
                // case ObservationSiteLocation.ID_NETWORK:
                // if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // showWarningToast("簡易GPS(ネットワーク位置プロバイダ)は現在利用できません.\nシステム設定を変更してください.");
                // return;
                // }
                // resolver = new ObservationSiteLocationGpsResolver(context, ObservationSiteLocationResolverType.NETWORK);
                // break;
                //
                // default:
                // ObservationSiteLocationChooseResolver chooseResolver = new ObservationSiteLocationChooseResolver(context);
                // chooseResolver.setObservationSiteLocation(location);
                // resolver = chooseResolver;
                // }

                try {
                    resolver = AbstractObservationSiteLocationResolver.newResolver(context, location);
                    onChooseDataListener.onChooseData(resolver);
                } catch (IllegalStateException e) {
                    showWarningToast(e.getMessage());
                }

                dialog.dismiss();
            }
        });
    }

    private void showWarningToast(String message) {
        if (warningToast != null) {
            warningToast.cancel();
        }
        warningToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        warningToast.setGravity(Gravity.CENTER, 0, 0);
        warningToast.show();
    }
}
