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
        Rect actual = extractTheaterRect(target);

        assertThat(actual, notNullValue());
        assertThat(actual.xL, is(+45f));
        assertThat(actual.xR, is(+115f));
        assertThat(actual.yT, is(-65f));
        assertThat(actual.yB, is(-35f));
    }

    @Test
    public void test_calculateTheaterRect_xMINUS80_y50_西側だけ() {
        float terminalAzimuth = -80;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);
        Rect actual = extractTheaterRect(target);

        assertThat(actual, notNullValue());
        assertThat(actual.xL, is(-115f));
        assertThat(actual.xR, is(-45f));
        assertThat(actual.yT, is(-65f));
        assertThat(actual.yB, is(-35f));
    }

    @Test
    public void test_calculateTheaterRect_x170_y50_180度線をまたぐ() {
        float terminalAzimuth = +170;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);
        Rect actual = extractTheaterRect(target);

        assertThat(actual, notNullValue());
        assertThat(actual.xL, is(+135f));
        assertThat(actual.xR, is(-155f));
        assertThat(actual.yT, is(-65f));
        assertThat(actual.yB, is(-35f));
    }

    @Test
    public void test_calculateTheaterRect_xMINUS170_y50_180度線をまたぐ() {
        float terminalAzimuth = -170;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);
        Rect actual = extractTheaterRect(target);

        assertThat(actual, notNullValue());
        assertThat(actual.xL, is(+155f));
        assertThat(actual.xR, is(-135f));
        assertThat(actual.yT, is(-65f));
        assertThat(actual.yB, is(-35f));
    }

    @Test
    public void test_calculateTheaterRect_x10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = +10;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);
        Rect actual = extractTheaterRect(target);

        assertThat(actual, notNullValue());
        assertThat(actual.xL, is(-25f));
        assertThat(actual.xR, is(+45f));
        assertThat(actual.yT, is(-65f));
        assertThat(actual.yB, is(-35f));
    }

    @Test
    public void test_calculateTheaterRect_xMINUS10_y50_ゼロ度線をまたぐ() {
        float terminalAzimuth = -10;
        float terminalPitch = -50;
        target.calculateTheaterRect(terminalAzimuth, terminalPitch);
        Rect actual = extractTheaterRect(target);

        assertThat(actual, notNullValue());
        assertThat(actual.xL, is(-45f));
        assertThat(actual.xR, is(+25f));
        assertThat(actual.yT, is(-65f));
        assertThat(actual.yB, is(-35f));
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
}
