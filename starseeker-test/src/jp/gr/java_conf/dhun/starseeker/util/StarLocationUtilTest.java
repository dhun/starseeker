/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
    public void test_convertHourDoubleToHourString_18dot69690() {
        double hour = 18.69690;
        String actual = StarLocationUtil.convertHourDoubleToHourString(hour);
        String expect = "18h 41.8m";
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourStringToHourDouble_18dot69690() {
        String hour = "18h 41.8m";
        double actual = StarLocationUtil.convertHourStringToHourDouble(hour);
        double expect = 18.69690;

        double delta = 0.0003; // 誤差の許容範囲は適当
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationUtil.convertHourDoubleToHourString(actual), is(hour));
    }

    @Test
    public void test_convertHourStringToHourDouble_18dot69690_plus() {
        String hour = "+18h 41.8m";
        double actual = StarLocationUtil.convertHourStringToHourDouble(hour);
        double expect = +18.69690;

        double delta = 0.0003; // 誤差の許容範囲は適当
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationUtil.convertHourDoubleToHourString(actual), is("18h 41.8m"));
    }

    @Test
    public void test_convertHourStringToHourDouble_18dot69690_minus() {
        String hour = "-18h 41.8m";
        double actual = StarLocationUtil.convertHourStringToHourDouble(hour);
        double expect = -18.69690;

        double delta = 0.0003; // 誤差の許容範囲は適当
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationUtil.convertHourDoubleToHourString(actual), is(hour));
    }

}
