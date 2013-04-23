/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system;

import java.util.Calendar;

import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.model.StarSeekerEngineConfig;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.ObservationSiteLocationDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.DateTimeUtils;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * @author jun
 * 
 */
public class StarSeekerEngineRefreshTask extends AsyncTask<StarSeekerEngineConfig, Integer, StarSeekerEngine>
        implements IObservationSiteLocationResolver.ObservationSiteLocationResolverListener {

    private final Context context;

    private StarSeekerEngine starSeekerEngine;
    private StarSeekerEngineConfig starSeekerEngineConfig;

    private IObservationSiteLocationResolver observationSiteLocationResolver;
    private ObservationSiteLocation observationSiteLocation;

    public StarSeekerEngineRefreshTask(Context context) {
        this.context = context.getApplicationContext();
    }

    public void setStarSeekerEngine(StarSeekerEngine engine) {
        starSeekerEngine = engine;
    }

    public void setObservationSiteLocationResolver(IObservationSiteLocationResolver resolver) {
        observationSiteLocationResolver = resolver;
        if (observationSiteLocationResolver != null) {
            observationSiteLocationResolver.setObservationSiteLocationResolverListener(this);
        }
    }

    protected void validateState(StarSeekerEngineConfig... configs) {
        if (1 != configs.length) {
            throw new IllegalArgumentException("configs's length must be one. length=[" + configs.length + "]");
        }

        if (null == starSeekerEngine) {
            throw new IllegalArgumentException("starSeekerEngine must be not null.");
        }
        // if (null == observationSiteLocationResolver) {
        // throw new IllegalArgumentException("observationSiteLocationResolver must be not null.");
        // }
    }

    @Override
    protected StarSeekerEngine doInBackground(StarSeekerEngineConfig... configs) {
        validateState(configs);

        starSeekerEngineConfig = configs[0];

        // 観測地点の解決
        if (null != observationSiteLocationResolver) {
            synchronized (this) {
                Handler uiHandler = new Handler(context.getMainLooper());
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        observationSiteLocationResolver.resume();
                    }
                });

                try {
                    this.wait();
                } catch (InterruptedException e) {

                }
            }

        } else {
            // XXX ここでDBはいやだな
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            try {
                ObservationSiteLocationDao dao = new ObservationSiteLocationDao(db);
                observationSiteLocation = dao.findByPk(starSeekerEngineConfig.getObservationSiteLocationId());
            } finally {
                db.close();
            }
        }

        Calendar basedCalendar = Calendar.getInstance();
        basedCalendar.setTime(starSeekerEngineConfig.getCoordinatesCalculateBaseDate());
        Calendar localCalendar = DateTimeUtils.toSameDateTime(basedCalendar, observationSiteLocation.getTimeZone());

        LogUtils.i(getClass(), String.format("緯度=[%6.2f], 経度=[%6.2f], 日時=[%tT], 等級=[%3.2f]" //
                , observationSiteLocation.getLongitude() //
                , observationSiteLocation.getLatitude() //
                , localCalendar.getTime() //
                , starSeekerEngineConfig.getExtractUpperStarMagnitude() //
                ));

        // エンジンの設定
        starSeekerEngine.setObservationCondition(observationSiteLocation, localCalendar);
        starSeekerEngine.setExtractUpperStarMagnitude(starSeekerEngineConfig.getExtractUpperStarMagnitude());

        // 星の抽出
        starSeekerEngine.extract();

        // 星の配置
        starSeekerEngine.locate();

        starSeekerEngine.prepare();

        return starSeekerEngine;
    }

    public void onCancel() {
        // FIXME 書く
    }

    @Override
    public void onResolveObservationSiteLocation(IObservationSiteLocationResolver resolver, ObservationSiteLocation location) {
        resolver.pause();

        observationSiteLocation = location;

        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void onNotAvailableLocationProvider(IObservationSiteLocationResolver resolver) {
        synchronized (this) {
            this.notify();
        }
    }
}
