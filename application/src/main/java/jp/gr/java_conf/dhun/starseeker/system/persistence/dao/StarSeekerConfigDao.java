/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao;

import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarSeekerConfig;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget.SeekTargetType;
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
    private static final String KEY_LOCK_SCREEN_ROTATE = "lockScreenRotate";                                  // 画面を回転ロックするかどうか
    private static final String KEY_EXTRACT_LOWER_STAR_MAGNITUDE = "extractLowerStarMagnitude";               // シアターに抽出するか等星の下限値
    private static final String KEY_MASTER_OBSERVATION_SITE_LOCATION_ID = "masterObservationSiteLocationId";  // １つ目の天体シアターに対する観測地点のID
    private static final String KEY_SECOND_OBSERVATION_SITE_LOCATION_ID = "secondObservationSiteLocationId";  // ２つ目の天体シアターに対する観測地点のID
    private static final String KEY_SEEK_TARGET_TYPE = "seekTargetType";                                      // 探索対象の種別
    private static final String KEY_SEEK_TARGET_ID = "seekTargetId";                                          // 探索対象の識別子

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
        config.setCoordinatesCalculateBaseDate(new Date(preferences.getLong(KEY_COORDINATES_CALCULATE_BASE_DATE, new Date().getTime())));
        config.setSecondAstronomicalTheaterVisible(preferences.getBoolean(KEY_SECOND_ASTRONOMICAL_THEATER_VISIBLE, false));
        config.setLockScreenRotate(preferences.getBoolean(KEY_LOCK_SCREEN_ROTATE, true));
        config.setExtractLowerStarMagnitude(preferences.getFloat(KEY_EXTRACT_LOWER_STAR_MAGNITUDE, 2.0f));
        config.setMasterObservationSiteLocationId(preferences.getInt(KEY_MASTER_OBSERVATION_SITE_LOCATION_ID, 0));
        config.setSecondObservationSiteLocationId(preferences.getInt(KEY_SECOND_OBSERVATION_SITE_LOCATION_ID, 0));
        if (preferences.contains(KEY_SEEK_TARGET_TYPE)) {
            config.setSeekTargetType(SeekTargetType.valueOf(preferences.getString(KEY_SEEK_TARGET_TYPE, null)));
            config.setSeekTargetId(preferences.getString(KEY_SEEK_TARGET_ID, null));
        }

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
        editor.putBoolean(KEY_LOCK_SCREEN_ROTATE, config.isLockScreenRotate());
        editor.putFloat(KEY_EXTRACT_LOWER_STAR_MAGNITUDE, config.getExtractLowerStarMagnitude());
        editor.putInt(KEY_MASTER_OBSERVATION_SITE_LOCATION_ID, config.getMasterObservationSiteLocationId());
        editor.putInt(KEY_SECOND_OBSERVATION_SITE_LOCATION_ID, config.getSecondObservationSiteLocationId());
        editor.putString(KEY_SEEK_TARGET_TYPE, config.getSeekTargetType().name());
        editor.putString(KEY_SEEK_TARGET_ID, config.getSeekTargetId().toString());

        editor.commit();
    }
}
