/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import java.util.Date;

/**
 * スターシーカーシステム設定のエンティティ
 * 
 * @author jun
 * 
 */
public class StarSeekerConfig {

    private Date coordinatesCalculateBaseDate;          // 星の座標計算の基準日時

    private boolean secondAstronomicalTheaterVisible;   // ２つめの天体シアターを表示するかどうか

    private float displayLowerMagnitude;                // 天体シアターに表示するか等星の下限値

    private long masterObservationSiteLocationId;       // １つ目の天体シアターに対する観測地点のID
    private long secondObservationSiteLocationId;       // ２つ目の天体シアターに対する観測地点のID

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
     * ２つめの天体シアターを表示するかどうかを取得します.<br/>
     */
    public boolean isSecondAstronomicalTheaterVisible() {
        return secondAstronomicalTheaterVisible;
    }

    /**
     * ２つめの天体シアターを表示するかどうかを設定します.<br/>
     */
    public void setSecondAstronomicalTheaterVisible(boolean visible) {
        this.secondAstronomicalTheaterVisible = visible;
    }

    /**
     * 天体シアターに表示するか等星の下限値を取得します.<br/>
     */
    public float getDisplayLowerMagnitude() {
        return displayLowerMagnitude;
    }

    /**
     * 天体シアターに表示するか等星の下限値を設定します.<br/>
     */
    public void setDisplayLowerMagnitude(float displayLowerMagnitude) {
        this.displayLowerMagnitude = displayLowerMagnitude;
    }

    /**
     * １つ目の天体シアターに対する観測地点のIDを取得します.<br/>
     */
    public long getMasterObservationSiteLocationId() {
        return masterObservationSiteLocationId;
    }

    /**
     * １つ目の天体シアターに対する観測地点のIDを設定します.<br/>
     */
    public void setMasterObservationSiteLocationId(long masterObservationSiteLocationId) {
        this.masterObservationSiteLocationId = masterObservationSiteLocationId;
    }

    /**
     * ２つ目の天体シアターに対する観測地点のIDを取得します.<br/>
     */
    public long getSecondObservationSiteLocationId() {
        return secondObservationSiteLocationId;
    }

    /**
     * ２つ目の天体シアターに対する観測地点のIDを設定します.<br/>
     */
    public void setSecondObservationSiteLocationId(long secondObservationSiteLocationId) {
        this.secondObservationSiteLocationId = secondObservationSiteLocationId;
    }
}
