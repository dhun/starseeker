/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.exception;

import jp.gr.java_conf.dhun.starseeker.model.AstronomicalTheater.Rect;
import jp.gr.java_conf.dhun.starseeker.system.model.panel.IAstronomicalTheaterPanel;

/**
 * スターシーカーシステムの座標系に関する実行時例外.<br/>
 * 
 * @author jun
 * 
 */
public class StarSeekerCoordinatesException extends RuntimeException {

    public StarSeekerCoordinatesException(IAstronomicalTheaterPanel.AstronomicalTheaterPanelType panelType, Rect coordinatesRect, String detailMessage) {
        super(String.format("type=[%s], coordinatesRect=[%s], reason=[%s]", panelType, coordinatesRect, detailMessage));
    }
}
