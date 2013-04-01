/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;

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

    private ObservationSiteLocation masterObservationSiteLocation;  // １つ目の天体シアターに対する観測地点のID
    private ObservationSiteLocation secondObservationSiteLocation;  // ２つ目の天体シアターに対する観測地点のID

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
     * １つ目の天体シアターに対する観測地点を取得します.<br/>
     */
    public ObservationSiteLocation getMasterObservationSiteLocation() {
        return masterObservationSiteLocation;
    }

    /**
     * １つ目の天体シアターに対する観測地点を設定します.<br/>
     */
    public void setMasterObservationSiteLocation(ObservationSiteLocation masterObservationSiteLocation) {
        this.masterObservationSiteLocation = masterObservationSiteLocation;
    }

    /**
     * ２つ目の天体シアターに対する観測地点を取得します.<br/>
     */
    public ObservationSiteLocation getSecondObservationSiteLocation() {
        return secondObservationSiteLocation;
    }

    /**
     * ２つ目の天体シアターに対する観測地点を設定します.<br/>
     */
    public void setSecondObservationSiteLocation(ObservationSiteLocation secondObservationSiteLocation) {
        this.secondObservationSiteLocation = secondObservationSiteLocation;
    }
}
