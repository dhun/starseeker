/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import jp.gr.java_conf.dhun.starseeker.model.AstronomicalTheater.Rect;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jun
 * 
 */
public class AstronomicalTheaterTest {

    private AstronomicalTheater target;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        int displayWidth = 600;
        int displayHeight = 1024;
        float theaterWidth = 70;
        float theaterHeight = 30;
        target = new AstronomicalTheater(displayWidth, displayHeight);
        target.setTheaterSize(theaterWidth, theaterHeight);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_calculateTheaterRect_x80_y50_東側だけ() {
        float terminalAzimuth = +80;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertThat(actualTheaterRect.xL, is(+45f));
        assertThat(actualTheaterRect.xR, is(+115f));
        assertThat(actualTheaterRect.yT, is(-65f));
        assertThat(actualTheaterRect.yB, is(-35f));

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(+45f));
        assertThat(actualPanel.theaterRect.xR, is(+115f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(600f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));
    }

    @Test
    public void test_calculateTheaterRect_xMINUS80_y50_西側だけ() {
        float terminalAzimuth = -80;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertThat(actualTheaterRect.xL, is(-115f));
        assertThat(actualTheaterRect.xR, is(-45f));
        assertThat(actualTheaterRect.yT, is(-65f));
        assertThat(actualTheaterRect.yB, is(-35f));

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(-115f));
        assertThat(actualPanel.theaterRect.xR, is(-45f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(600f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));
    }

    @Test
    public void test_calculateTheaterRect_x170_y50_180度線をまたぐ() {
        float terminalAzimuth = +170;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertThat(actualTheaterRect.xL, is(+135f));
        assertThat(actualTheaterRect.xR, is(-155f));
        assertThat(actualTheaterRect.yT, is(-65f));
        assertThat(actualTheaterRect.yB, is(-35f));

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(+135f));
        assertThat(actualPanel.theaterRect.xR, is(+180f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(-180f));
        assertThat(actualPanel.theaterRect.xR, is(-155f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(385.7142857f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(385.7142857f));
        assertThat(actualPanel.displayRect.xR, is(600f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));
    }

    @Test
    public void test_calculateTheaterRect_xMINUS170_y50_180度線をまたぐ() {
        float terminalAzimuth = -170;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertThat(actualTheaterRect.xL, is(+155f));
        assertThat(actualTheaterRect.xR, is(-135f));
        assertThat(actualTheaterRect.yT, is(-65f));
        assertThat(actualTheaterRect.yB, is(-35f));

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(+155f));
        assertThat(actualPanel.theaterRect.xR, is(+180f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(-180f));
        assertThat(actualPanel.theaterRect.xR, is(-135f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(214.28572f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(214.28572f));
        assertThat(actualPanel.displayRect.xR, is(600f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));
    }

    @Test
    public void test_calculateTheaterRect_x10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = +10;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertThat(actualTheaterRect.xL, is(-25f));
        assertThat(actualTheaterRect.xR, is(+45f));
        assertThat(actualTheaterRect.yT, is(-65f));
        assertThat(actualTheaterRect.yB, is(-35f));

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(+45f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(-25f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(214.2857143f));
        assertThat(actualPanel.displayRect.xR, is(600f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(214.2857143f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));
    }

    @Test
    public void test_calculateTheaterRect_xMINUS10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = -10;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertThat(actualTheaterRect.xL, is(-45f));
        assertThat(actualTheaterRect.xR, is(+25f));
        assertThat(actualTheaterRect.yT, is(-65f));
        assertThat(actualTheaterRect.yB, is(-35f));

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(+25f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(-45f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(-65f));
        assertThat(actualPanel.theaterRect.yB, is(-35f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.theaterRect.xL, is(0f));
        assertThat(actualPanel.theaterRect.xR, is(0f));
        assertThat(actualPanel.theaterRect.yT, is(0f));
        assertThat(actualPanel.theaterRect.yB, is(0f));

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(385.7142857f));
        assertThat(actualPanel.displayRect.xR, is(600f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(385.7142857f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(1024f));

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertThat(actualPanel.displayRect.xL, is(0f));
        assertThat(actualPanel.displayRect.xR, is(0f));
        assertThat(actualPanel.displayRect.yT, is(0f));
        assertThat(actualPanel.displayRect.yB, is(0f));
    }

    private Rect extractTheaterRect(AstronomicalTheater theater) {
        try {
            Field field = AstronomicalTheater.class.getDeclaredField("theaterRect");
            field.setAccessible(true);
            return (Rect) field.get(theater);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AstronomicalTheaterPanel extractTheaterPanel_FACE_EAST_PANEL(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "FACE_EAST_PANEL");
    }

    private AstronomicalTheaterPanel extractTheaterPanel_FACE_WEST_PANEL(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "FACE_WEST_PANEL");
    }

    private AstronomicalTheaterPanel extractTheaterPanel_BACK_EAST_PANEL(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "BACK_EAST_PANEL");
    }

    private AstronomicalTheaterPanel extractTheaterPanel_BACK_WEST_PANEL(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "BACK_WEST_PANEL");
    }

    private AstronomicalTheaterPanel extractTheaterPanel(AstronomicalTheater theater, String indexName) {
        try {
            Field indexField = AstronomicalTheater.class.getDeclaredField(indexName);
            indexField.setAccessible(true);
            int index = (Integer) indexField.get(theater);

            Field field = AstronomicalTheater.class.getDeclaredField("panels");
            field.setAccessible(true);
            return ((AstronomicalTheaterPanel[]) field.get(theater))[index];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
