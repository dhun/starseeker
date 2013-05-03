/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget.SeekTargetType;

/**
 * スターシーカーシステム設定のエンティティ
 * 
 * @author jun
 * 
 */
public class StarSeekerConfig {

    private Date coordinatesCalculateBaseDate;          // 星の座標計算の基準日時

    private boolean secondAstronomicalTheaterVisible;   // ２つめの天体シアターを表示するかどうか
    private boolean lockScreenRotate;                   // 画面を回転ロックするかどうか

    private float extractLowerStarMagnitude;            // 天体シアターに抽出するか等星の下限値

    private Integer masterObservationSiteLocationId;    // １つ目の天体シアターに対する観測地点のID
    private Integer secondObservationSiteLocationId;    // ２つ目の天体シアターに対する観測地点のID

    private SeekTargetType seekTargetType;              // 探索対象の種別
    private String seekTargetId;                        // 探索対象の識別子

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
     * 画面を回転ロックするかどうかを取得します.<br/>
     */
    public boolean isLockScreenRotate() {
        return lockScreenRotate;
    }

    /**
     * 画面を回転ロックするかどうかを設定します.<br/>
     */
    public void setLockScreenRotate(boolean lockScreenRotate) {
        this.lockScreenRotate = lockScreenRotate;
    }

    /**
     * 天体シアターに抽出するか等星の下限値を取得します.<br/>
     */
    public float getExtractLowerStarMagnitude() {
        return extractLowerStarMagnitude;
    }

    /**
     * 天体シアターに抽出するか等星の下限値を設定します.<br/>
     */
    public void setExtractLowerStarMagnitude(float extractLowerStarMagnitude) {
        this.extractLowerStarMagnitude = extractLowerStarMagnitude;
    }

    /**
     * １つ目の天体シアターに対する観測地点のIDを取得します.<br/>
     */
    public Integer getMasterObservationSiteLocationId() {
        return masterObservationSiteLocationId;
    }

    /**
     * １つ目の天体シアターに対する観測地点のIDを設定します.<br/>
     */
    public void setMasterObservationSiteLocationId(Integer id) {
        this.masterObservationSiteLocationId = id;
    }

    /**
     * ２つ目の天体シアターに対する観測地点のIDを取得します.<br/>
     */
    public Integer getSecondObservationSiteLocationId() {
        return secondObservationSiteLocationId;
    }

    /**
     * ２つ目の天体シアターに対する観測地点のIDを設定します.<br/>
     */
    public void setSecondObservationSiteLocationId(Integer id) {
        this.secondObservationSiteLocationId = id;
    }

    /**
     * 探索対象の種別を取得します.<br/>
     */
    public SeekTargetType getSeekTargetType() {
        return seekTargetType;
    }

    /**
     * 探索対象の種別を設定します.<br/>
     */
    public void setSeekTargetType(SeekTargetType seekTargetType) {
        this.seekTargetType = seekTargetType;
    }

    /**
     * 探索対象の識別子を取得します.<br>
     */
    public String getSeekTargetId() {
        return seekTargetId;
    }

    /**
     * 探索対象の識別子を設定します.<br>
     */
    public void setSeekTargetId(String seekTargetId) {
        this.seekTargetId = seekTargetId;
    }
}
