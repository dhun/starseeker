package jp.gr.java_conf.dhun.starseeker.system.model.panel;

/**
 * 天体シアターの東側背面のパネル.<br/>
 * 
 * <pre>
 * +30      0        -30
 * +--------+--------+
 * |        |        |
 * |  this  |        |
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
 * |        |  this  |
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
 * |  this  |        |
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
 * |        |  this  |
 * |        |        |
 * +--------+--------+
 * -150 -180 +180    +150
 * </pre>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterEastBackPanel extends AstronomicalTheaterPanel {

    public AstronomicalTheaterEastBackPanel(int displayWidth, int displayHeight) {
        super(AstronomicalTheaterPanelType.EAST_BACK, displayWidth, displayHeight);
    }
}