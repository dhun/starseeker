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
    public void test_convertAngleStringToDouble_16dot43() {
        String angle = "-16°43'";
        double actual = StarLocationUtil.convertAngleStringToDouble(angle);
        double expect = -16.717;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);

        assertThat(StarLocationUtil.convertAngleDoubleToString(actual), is(angle));
    }

    @Test
    public void test_convertAngleDoubleToString_16dot43() {
        double angle = -16.717;
        String actual = StarLocationUtil.convertAngleDoubleToString(angle);
        String expect = "-16°43'";
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourDoubleToString_18dot69690() {
        double hour = 18.69690;
        String actual = StarLocationUtil.convertHourDoubleToString(hour);
        String expect = "18h 41.8m";
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourStringToDouble_18dot69690() {
        String hour = "18h 41.8m";
        double actual = StarLocationUtil.convertHourStringToDouble(hour);
        double expect = 18.69690;
        TestUtils.asserAllowingError(actual, expect, 0.0003); // 誤差の許容範囲は適当

        assertThat(StarLocationUtil.convertHourDoubleToString(actual), is(hour));
    }

    @Test
    public void test_convertHourStringToDouble_18dot69690_plus() {
        String hour = "+18h 41.8m";
        double actual = StarLocationUtil.convertHourStringToDouble(hour);
        double expect = +18.69690;
        TestUtils.asserAllowingError(actual, expect, 0.0003); // 誤差の許容範囲は適当

        assertThat(StarLocationUtil.convertHourDoubleToString(actual), is("18h 41.8m"));
    }

    @Test
    public void test_convertHourStringToDouble_18dot69690_minus() {
        String hour = "-18h 41.8m";
        double actual = StarLocationUtil.convertHourStringToDouble(hour);
        double expect = -18.69690;
        TestUtils.asserAllowingError(actual, expect, 0.0003); // 誤差の許容範囲は適当

        assertThat(StarLocationUtil.convertHourDoubleToString(actual), is(hour));
    }

}
