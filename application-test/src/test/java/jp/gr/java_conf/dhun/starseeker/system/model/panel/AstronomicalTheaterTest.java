/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model.panel;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;

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
    public void test_calculateCoordinatesRect_x80_y50_東側だけ() {
        float terminalAzimuth = +80;
        float terminalPitch = -50;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +45f);
        assertInDelta(actualTheaterRect.xR, +115f);
        assertInDelta(actualTheaterRect.yT, +65f);
        assertInDelta(actualTheaterRect.yB, +35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +115f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_xMINUS80_y50_西側だけ() {
        float terminalAzimuth = -80;
        float terminalPitch = -50;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -115f);
        assertInDelta(actualTheaterRect.xR, -45f);
        assertInDelta(actualTheaterRect.yT, +65f);
        assertInDelta(actualTheaterRect.yB, +35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -115f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_x170_y50_180度線をまたぐ() {
        float terminalAzimuth = +170;
        float terminalPitch = -50;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +135f);
        assertInDelta(actualTheaterRect.xR, -155f);
        assertInDelta(actualTheaterRect.yT, +65f);
        assertInDelta(actualTheaterRect.yB, +35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +135f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -155f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_xMINUS170_y50_180度線をまたぐ() {
        float terminalAzimuth = -170;
        float terminalPitch = -50;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +155f);
        assertInDelta(actualTheaterRect.xR, -135f);
        assertInDelta(actualTheaterRect.yT, +65f);
        assertInDelta(actualTheaterRect.yB, +35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +155f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -135f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 214.28572f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 214.28572f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_x10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = +10;
        float terminalPitch = -50;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -25f);
        assertInDelta(actualTheaterRect.xR, +45f);
        assertInDelta(actualTheaterRect.yT, +65f);
        assertInDelta(actualTheaterRect.yB, +35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -25f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 214.2857143f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 214.2857143f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_xMINUS10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = -10;
        float terminalPitch = -50;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -45f);
        assertInDelta(actualTheaterRect.xR, +25f);
        assertInDelta(actualTheaterRect.yT, +65f);
        assertInDelta(actualTheaterRect.yB, +35f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +25f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +65f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +35f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, 0f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_xMINUS10_y50_ゼロ度線をまたいで北極点もまたぐ() {
        float terminalAzimuth = -10;
        float terminalPitch = -80;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -45f);
        assertInDelta(actualTheaterRect.xR, +25f);
        assertInDelta(actualTheaterRect.yT, +95f);
        assertInDelta(actualTheaterRect.yB, +65f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +25f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +155f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -135f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 170.6666667f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 385.7142857f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 170.6666667f);
    }

    @Test
    public void test_calculateCoordinatesRect_xMINUS10_y50_180度線をまたいで北極点もまたぐ() {
        float terminalAzimuth = -170;
        float terminalPitch = -80;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +155f);
        assertInDelta(actualTheaterRect.xR, -135f);
        assertInDelta(actualTheaterRect.yT, +95f);
        assertInDelta(actualTheaterRect.yB, +65f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +155f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -135f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +25f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -180f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 214.2857143f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 214.2857143f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 214.2857143f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 170.6666667f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 214.2857143f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 170.6666667f);
    }

    @Test
    public void test_calculateCoordinatesRect_x80_yMINUS80_北極点もまたぐ() {
        float terminalAzimuth = +80;
        float terminalPitch = -80;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, +45f);
        assertInDelta(actualTheaterRect.xR, +115f);
        assertInDelta(actualTheaterRect.yT, +95f);
        assertInDelta(actualTheaterRect.yB, +65f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +115f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -115f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 170.6666667f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);
    }

    @Test
    public void test_calculateCoordinatesRect_xMINUS80_yMINUS80_北極点もまたぐ() {
        float terminalAzimuth = -80;
        float terminalPitch = -80;
        target.calculateCoordinatesRect(terminalAzimuth, terminalPitch);

        CoordinatesRect actualTheaterRect = extractTheaterRect(target);
        assertThat(actualTheaterRect, notNullValue());
        assertInDelta(actualTheaterRect.xL, -115f);
        assertInDelta(actualTheaterRect.xR, -45f);
        assertInDelta(actualTheaterRect.yT, +95f);
        assertInDelta(actualTheaterRect.yB, +65f);

        AstronomicalTheaterPanel actualPanel;

        // 天体シアターの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, -115f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, -45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +90f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +65f);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xL, +115f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.xR, +45f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yT, +85f);
        assertInDelta(actualPanel.horizontalCoordinatesRect.yB, +90f);

        // ディスプレイの座標
        actualPanel = extractTheaterPanel_eastFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westFace(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 170.6666667f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 1024);

        actualPanel = extractTheaterPanel_eastBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 0f);

        actualPanel = extractTheaterPanel_westBack(target);
        assertInDelta(actualPanel.displayCoordinatesRect.xL, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.xR, 600f);
        assertInDelta(actualPanel.displayCoordinatesRect.yT, 0f);
        assertInDelta(actualPanel.displayCoordinatesRect.yB, 170.6666667f);
    }

    private void assertInDelta(float actual, float expect) {
        final float delta = 0.000001f;
        assertTrue(
                String.format("差分が許容範囲を超えました. expect=%f, actual=%f, diff=%f, delta=%f", //
                        expect, actual, Math.abs(expect - actual), delta), //
                Math.abs(expect - actual) < delta);
    }

    private CoordinatesRect extractTheaterRect(AstronomicalTheater theater) {
        try {
            Field field = AstronomicalTheater.class.getDeclaredField("horizontalCoordinatesRect");
            field.setAccessible(true);
            return (CoordinatesRect) field.get(theater);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AstronomicalTheaterPanel extractTheaterPanel_eastFace(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "eastFacePanel");
    }

    private AstronomicalTheaterPanel extractTheaterPanel_westFace(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "westFacePanel");
    }

    private AstronomicalTheaterPanel extractTheaterPanel_eastBack(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "eastBackPanel");
    }

    private AstronomicalTheaterPanel extractTheaterPanel_westBack(AstronomicalTheater theater) {
        return extractTheaterPanel(theater, "westBackPanel");
    }

    private AstronomicalTheaterPanel extractTheaterPanel(AstronomicalTheater theater, String indexName) {
        try {
            Field field = AstronomicalTheater.class.getDeclaredField(indexName);
            field.setAccessible(true);
            return (AstronomicalTheaterPanel) field.get(theater);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
