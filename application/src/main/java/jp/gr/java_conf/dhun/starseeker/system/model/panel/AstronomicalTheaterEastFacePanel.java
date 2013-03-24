package jp.gr.java_conf.dhun.starseeker.system.model.panel;

/**
 * 天体シアターの東側正面のパネル.<br/>
 * 
 * <pre>
 * +30      0        -30
 * +--------+--------+
 * |        |        |
 * |        |        |
 * |        |        |
 * +--------+--------+ +90
 * |        |        |
 * |        |  this  |
 * |        |        |
 * +--------+--------+
 * -30      0        +30
 * 
 * or
 * 
 * -150 -180 +180    +150
 * +--------+--------+
 * |        |        |
 * |        |        |
 * |        |        |
 * +--------+--------+ + 90
 * |        |        |
 * |  this  |        |
 * |        |        |
 * +--------+--------+
 * +150 +180 -180    -150
 * </pre>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterEastFacePanel extends AbstractAstronomicalTheaterPanel {

    public AstronomicalTheaterEastFacePanel(int displayWidth, int displayHeight) {
        super(AstronomicalTheaterPanelType.EAST_FACE, displayWidth, displayHeight);
    }
}
