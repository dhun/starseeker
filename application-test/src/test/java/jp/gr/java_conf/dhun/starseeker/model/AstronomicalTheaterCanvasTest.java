/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jun
 * 
 */
public class AstronomicalTheaterCanvasTest {

    private AstronomicalTheaterCanvas target;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        int width = 720;
        int height = 1280;
        target = new AstronomicalTheaterCanvas(width, height);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_assignTheaterRect() {
        Orientations orientations = new Orientations();
        orientations.x;

        target.assignTheaterRect();
    }
}
