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
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @author jun
 * 
 */
@RunWith(RobolectricTestRunner.class)
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
        assertInDelta(actualTheaterRect.xL, +45f);
        assertInDelta(actualTheaterRect.xR, +115f);
        assertInDelta(actualTheaterRect.yT, -65f);
        assertInDelta(actualTheaterRect.yB, -35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, +45f);
        assertInDelta(actualPanel.theaterRect.xR, +115f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);
    }

    @Test
    public void test_calculateTheaterRect_xMINUS80_y50_西側だけ() {
        float terminalAzimuth = -80;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -115f);
        assertInDelta(actualTheaterRect.xR, -45f);
        assertInDelta(actualTheaterRect.yT, -65f);
        assertInDelta(actualTheaterRect.yB, -35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -115f);
        assertInDelta(actualPanel.theaterRect.xR, -45f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);
    }

    @Test
    public void test_calculateTheaterRect_x170_y50_180度線をまたぐ() {
        float terminalAzimuth = +170;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +135f);
        assertInDelta(actualTheaterRect.xR, -155f);
        assertInDelta(actualTheaterRect.yT, -65f);
        assertInDelta(actualTheaterRect.yB, -35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, +135f);
        assertInDelta(actualPanel.theaterRect.xR, +180f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -180f);
        assertInDelta(actualPanel.theaterRect.xR, -155f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);
    }

    @Test
    public void test_calculateTheaterRect_xMINUS170_y50_180度線をまたぐ() {
        float terminalAzimuth = -170;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +155f);
        assertInDelta(actualTheaterRect.xR, -135f);
        assertInDelta(actualTheaterRect.yT, -65f);
        assertInDelta(actualTheaterRect.yB, -35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, +155f);
        assertInDelta(actualPanel.theaterRect.xR, +180f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -180f);
        assertInDelta(actualPanel.theaterRect.xR, -135f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 214.28572f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 214.28572f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);
    }

    @Test
    public void test_calculateTheaterRect_x10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = +10;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -25f);
        assertInDelta(actualTheaterRect.xR, +45f);
        assertInDelta(actualTheaterRect.yT, -65f);
        assertInDelta(actualTheaterRect.yB, -35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, +45f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -25f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 214.2857143f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 214.2857143f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);
    }

    @Test
    public void test_calculateTheaterRect_xMINUS10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = -10;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -45f);
        assertInDelta(actualTheaterRect.xR, +25f);
        assertInDelta(actualTheaterRect.yT, -65f);
        assertInDelta(actualTheaterRect.yB, -35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, +25f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -45f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, -65f);
        assertInDelta(actualPanel.theaterRect.yB, -35f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, 0f);
        assertInDelta(actualPanel.theaterRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 0f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 0f);
    }

    @Test
    public void test_calculateTheaterRect_xMINUS10_y50_ゼロ度線をまたいで北極点もまたぐ() {
        float terminalAzimuth = -10;
        float terminalPitch = -80;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);

        Rect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -45f);
        assertInDelta(actualTheaterRect.xR, +25f);
        assertInDelta(actualTheaterRect.yT, -95f);
        assertInDelta(actualTheaterRect.yB, -65f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, +25f);
        assertInDelta(actualPanel.theaterRect.yT, -90f);
        assertInDelta(actualPanel.theaterRect.yB, -65f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -45f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, -90f);
        assertInDelta(actualPanel.theaterRect.yB, -65f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, 0f);
        assertInDelta(actualPanel.theaterRect.xR, +25f);
        assertInDelta(actualPanel.theaterRect.yT, -95f);
        assertInDelta(actualPanel.theaterRect.yB, -90f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.theaterRect.xL, -45f);
        assertInDelta(actualPanel.theaterRect.xR, 0f);
        assertInDelta(actualPanel.theaterRect.yT, -95f);
        assertInDelta(actualPanel.theaterRect.yB, -90f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_FACE_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_FACE_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayRect.yB, 1024f);

        actualPanel = extractTheaterPanel_BACK_EAST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayRect.xR, 600f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 170.6666667f);

        actualPanel = extractTheaterPanel_BACK_WEST_PANEL(target);
        assertInDelta(actualPanel.displayRect.xL, 0f);
        assertInDelta(actualPanel.displayRect.xR, 385.7142857f);
        // assertInDelta(actualPanel.displayRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayRect.yT, 0f);
        assertInDelta(actualPanel.displayRect.yB, 170.6666667f);
    }

    private void assertInDelta(float actual, float expect) {
        final float delta = 0.000001f;
        assertTrue(
                String.format("差分が許容範囲を超えました. expect=%f, actual=%f, diff=%f, delta=%f", //
                        expect, actual, Math.abs(expect - actual), delta), //
                Math.abs(expect - actual) < delta);
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
