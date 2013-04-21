/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model;

import java.util.Calendar;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;

/**
 * スターシーカーエンジン設定のエンティティ
 * 
 * @author jun
 * 
 */
public class StarSeekerEngineConfig {

    private Calendar coordinatesCalculateBaseCalendar;          // 星の座標計算の基準日時
    private ObservationSiteLocation observationSiteLocation;    // 観測地点
    private float extractUpperStarMagnitude;                    // 抽出する星等級の上限値

    /**
     * 星の座標計算の基準日時を取得します.<br/>
     */
    public Calendar getCoordinatesCalculateBaseCalendar() {
        return coordinatesCalculateBaseCalendar;
    }

    /**
     * 星の座標計算の基準日時を設定します.<br/>
     */
    public void setCoordinatesCalculateBaseCalendar(Calendar calendar) {
        this.coordinatesCalculateBaseCalendar = calendar;
    }

    /**
     * 観測地点を取得します.<br/>
     */
    public ObservationSiteLocation getObservationSiteLocation() {
        return observationSiteLocation;
    }

    /**
     * 観測地点を設定します.<br/>
     */
    public void setObservationSiteLocation(ObservationSiteLocation observationSiteLocation) {
        this.observationSiteLocation = observationSiteLocation;
    }

    /**
     * 抽出する星等級の上限値を取得します.<br/>
     */
    public float getExtractUpperStarMagnitude() {
        return extractUpperStarMagnitude;
    }

    /**
     * 抽出する星等級の上限値を設定します.<br/>
     */
    public void setExtractUpperStarMagnitude(float magnitude) {
        this.extractUpperStarMagnitude = magnitude;
    }
}
