package jp.gr.java_conf.dhun.starseeker.system.model.panel;

/**
 * 天体シアターの西側正面のパネル.<br/>
 * 
 * <pre>
 * +30      0        -30
 * +--------+--------+
 * |        |        |
 * |        |        |
 * |        |        |
 * +--------+--------+ +90
 * |        |        |
 * |  this  |        |
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
 * |        |  this  |
 * |        |        |
 * +--------+--------+
 * +150 +180 -180    -150
 * </pre>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterWestFacePanel extends AbstractAstronomicalTheaterPanel {

    public AstronomicalTheaterWestFacePanel(int displayWidth, int displayHeight) {
        super(AstronomicalTheaterPanelType.WEST_FACE, displayWidth, displayHeight);
    }
}
