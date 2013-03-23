/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.gr.java_conf.dhun.starseeker.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jun
 * 
 */
public class StarLocationUtilTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_convertAngleStringToFloat_16dot43() {
        String angle = "-16°43'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        float expect = -16.717f;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);

        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(angle));
    }

    @Test
    public void test_convertAngleFloatToString_16dot43() {
        float angle = -16.717f;
        String actual = StarLocationUtil.convertAngleFloatToString(angle);
        String expect = "-16°43'";
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourFloatToString_18dot69690() {
        float hour = 18.69690f;
        String actual = StarLocationUtil.convertHourFloatToString(hour);
        String expect = "18h 41.8m";
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourStringToFloat_18dot69690() {
        String hour = "18h 41.8m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        float expect = 18.69690f;
        TestUtils.asserAllowingError(actual, expect, 0.0003); // 誤差の許容範囲は適当

        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(hour));
    }

    @Test
    public void test_convertHourStringToFloat_18dot69690_plus() {
        String hour = "+18h 41.8m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        float expect = +18.69690f;
        TestUtils.asserAllowingError(actual, expect, 0.0003); // 誤差の許容範囲は適当

        assertThat(StarLocationUtil.convertHourFloatToString(actual), is("18h 41.8m"));
    }

    @Test
    public void test_convertHourStringToFloat_18dot69690_minus() {
        String hour = "-18h 41.8m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        float expect = -18.69690f;
        TestUtils.asserAllowingError(actual, expect, 0.0003); // 誤差の許容範囲は適当

        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(hour));
    }

}
