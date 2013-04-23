/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model;

import java.util.Date;

/**
 * スターシーカーエンジン設定のエンティティ
 * 
 * @author jun
 * 
 */
public class StarSeekerEngineConfig {

    private Date coordinatesCalculateBaseDate;                  // 星の座標計算の基準日時
    private Integer observationSiteLocationId;                  // 観測地点
    private float extractUpperStarMagnitude;                    // 抽出する星等級の上限値

    /**
     * 星の座標計算の基準日時を取得します.<br/>
     */
    public Date getCoordinatesCalculateBaseDate() {
        return coordinatesCalculateBaseDate;
    }

    /**
     * 星の座標計算の基準日時を設定します.<br/>
     */
    public void setCoordinatesCalculateBaseDate(Date date) {
        this.coordinatesCalculateBaseDate = date;
    }

    /**
     * 観測地点のIDを取得します.<br/>
     */
    public Integer getObservationSiteLocationId() {
        return observationSiteLocationId;
    }

    /**
     * 観測地点のIDを設定します.<br/>
     */
    public void setObservationSiteLocationId(Integer id) {
        this.observationSiteLocationId = id;
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
