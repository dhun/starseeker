package jp.gr.java_conf.dhun.starseeker.system.model.panel;

import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerCoordinatesException;

/**
 * 天体シアターの西側背面のパネル.<br/>
 * 
 * <pre>
 * +30      0        -30
 * +--------+--------+
 * |        |        |
 * |        |  this  |
 * |        |        |
 * +--------+--------+ +90
 * |        |        |
 * |  FACE  |  FACE  |
 * |        |        |
 * +--------+--------+
 * -30      0        +30
 * 
 * or
 * 
 * -150 -180 +180    +150
 * +--------+--------+
 * |        |        |
 * |  this  |        |
 * |        |        |
 * +--------+--------+ + 90
 * |        |        |
 * |  FACE  |  FACE  |
 * |        |        |
 * +--------+--------+
 * +150 +180 -180    -150
 * 
 * or
 * 
 * +150 +180 -180    -150
 * +--------+--------+
 * |        |        |
 * |  FACE  |  FACE  |
 * |        |        |
 * +--------+--------+ - 90
 * |        |        |
 * |        |  this  |
 * |        |        |
 * +--------+--------+
 * +30      0        -30
 * 
 * or
 * 
 * +30      0        -30
 * +--------+--------+
 * |        |        |
 * |  FACE  |  FACE  |
 * |        |        |
 * +--------+--------+ - 90
 * |        |        |
 * |  this  |        |
 * |        |        |
 * +--------+--------+
 * -150 -180 +180    +150
 * </pre>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterWestBackPanel extends AstronomicalTheaterPanel {

    public AstronomicalTheaterWestBackPanel(int displayWidth, int displayHeight) {
        super(AstronomicalTheaterPanelType.WEST_BACK, displayWidth, displayHeight);
    }

    @Override
    protected void verifyCoordinatesRectX() {
        // 地平座標の検証
        if (horizontalCoordinatesRect.hasWidth()) {
            if (!(0 >= horizontalCoordinatesRect.xL)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(0 >= horizontalCoordinatesRect.xL)");
            }
            if (!(0 >= horizontalCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(0 >= horizontalCoordinatesRect.xR)");
            }
            if (!(horizontalCoordinatesRect.xL > horizontalCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(horizontalCoordinatesRect.xL > horizontalCoordinatesRect.xR)");
            }
        }

        // ディスプレイ座標の検証
        if (displayCoordinatesRect.hasWidth()) {
            if (!(0 <= displayCoordinatesRect.xL)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.xL)");
            }
            if (!(0 <= displayCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.xR)");
            }
            if (!(displayCoordinatesRect.xL < displayCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(displayCoordinatesRect.xL < displayCoordinatesRect.xR)");
            }
        }
    }

    @Override
    protected void verifyCoordinatesRectY() {
        // 地平座標の検証
        if (horizontalCoordinatesRect.hasHeight()) {
            if (!(horizontalCoordinatesRect.yB > horizontalCoordinatesRect.yT)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(horizontalCoordinatesRect.yB > horizontalCoordinatesRect.yT)");
            }
        }

        // ディスプレイ座標の検証
        if (displayCoordinatesRect.hasHeight()) {
            if (!(0 <= displayCoordinatesRect.yT)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.yT)");
            }
            if (!(0 < displayCoordinatesRect.yB)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 < displayCoordinatesRect.yB)");
            }
            if (!(displayCoordinatesRect.yT < displayCoordinatesRect.yB)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(displayCoordinatesRect.yT < displayCoordinatesRect.yB)");
            }
        }
    }
}
