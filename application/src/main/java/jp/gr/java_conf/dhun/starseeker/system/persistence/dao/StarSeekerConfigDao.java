/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao;

import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarSeekerConfig;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * スターシーカーシステム設定のDAO.<br/>
 * 
 * @author jun
 * 
 */
public class StarSeekerConfigDao {

    private static final String PREFERENCE_NAME = "";

    private static final String KEY_EXISTS = "exists";                                                        // 存在フラグ
    private static final String KEY_COORDINATES_CALCULATE_BASE_DATE = "coordinatesCalculateBaseDate";         // 星の座標計算の基準日時
    private static final String KEY_SECOND_ASTRONOMICAL_THEATER_VISIBLE = "secondAstronomicalTheaterVisible"; // ２つめの天体シアターを表示するかどうか
    private static final String KEY_DISPLAY_LOWER_MAGNITUDE = "displayLowerMagnitude";                        // 天体シアターに表示するか等星の下限値
    private static final String KEY_MASTER_OBSERVATION_SITE_LOCATION_ID = "masterObservationSiteLocationId";  // １つ目の天体シアターに対する観測地点のID
    private static final String KEY_SECOND_OBSERVATION_SITE_LOCATION_ID = "secondObservationSiteLocationId";  // ２つ目の天体シアターに対する観測地点のID

    private final Context context;

    /**
     * コンストラクタ
     * 
     * @param context Androidコンテキスト
     */
    public StarSeekerConfigDao(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreference() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private Editor getEditor() {
        return getSharedPreference().edit();
    }

    /**
     * 設定を検索します.<br/>
     * 
     * @return 設定. 該当データがなければ初期値.
     */
    public StarSeekerConfig findOrDefault() {
        SharedPreferences preferences = getSharedPreference();

        StarSeekerConfig config = new StarSeekerConfig();
        config.setCoordinatesCalculateBaseDate(new Date(preferences.getLong(KEY_COORDINATES_CALCULATE_BASE_DATE, 0)));
        config.setSecondAstronomicalTheaterVisible(preferences.getBoolean(KEY_SECOND_ASTRONOMICAL_THEATER_VISIBLE, false));
        config.setDisplayLowerMagnitude(preferences.getFloat(KEY_DISPLAY_LOWER_MAGNITUDE, 5.0f));
        config.setMasterObservationSiteLocationId(preferences.getLong(KEY_MASTER_OBSERVATION_SITE_LOCATION_ID, 0));
        config.setSecondObservationSiteLocationId(preferences.getLong(KEY_SECOND_OBSERVATION_SITE_LOCATION_ID, 0));

        return config;
    }

    /**
     * 設定を検索します.<br/>
     * 
     * @return 設定. 該当データがなければnull.
     */
    public StarSeekerConfig find() {
        SharedPreferences preferences = getSharedPreference();

        if (preferences.getBoolean(KEY_EXISTS, false)) {
            return findOrDefault();
        } else {
            return null;
        }
    }

    /**
     * 設定を保存します.<br/>
     * 
     * @param config 設定
     */
    public void store(StarSeekerConfig config) {
        Editor editor = getEditor();

        editor.putBoolean(KEY_EXISTS, true);
        editor.putLong(KEY_COORDINATES_CALCULATE_BASE_DATE, config.getCoordinatesCalculateBaseDate().getTime());
        editor.putBoolean(KEY_SECOND_ASTRONOMICAL_THEATER_VISIBLE, config.isSecondAstronomicalTheaterVisible());
        editor.putFloat(KEY_DISPLAY_LOWER_MAGNITUDE, config.getDisplayLowerMagnitude());
        editor.putLong(KEY_MASTER_OBSERVATION_SITE_LOCATION_ID, config.getMasterObservationSiteLocationId());
        editor.putLong(KEY_SECOND_OBSERVATION_SITE_LOCATION_ID, config.getSecondObservationSiteLocationId());

        editor.commit();
    }
}
