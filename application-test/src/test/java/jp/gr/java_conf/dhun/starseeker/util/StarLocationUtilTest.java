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
    public void test_convertAngleStringToFloat_70dot00() {
        String angle = "70°0'";
        String expect = "70°00'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertAngleStringToFloat_70dot00_with_space() {
        String angle = "70°   0'";
        String expect = "70°00'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertAngleStringToFloat_70dot5() {
        String angle = "70°5'";
        String expect = "70°05'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertAngleStringToFloat_70dot35() {
        String angle = "70°35'";
        String expect = "70°35'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertAngleStringToFloat_70dot35dot123() {
        String angle = "70°35.123'";
        String expect = "70°35'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertAngleStringToFloat_35dot01() {
        String angle = "35°01'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        float expect = +35.017f;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);

        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(angle));
    }

    @Test
    public void test_convertAngleStringToFloat_135dot44() {
        String angle = "135°44'";
        float actual = StarLocationUtil.convertAngleStringToFloat(angle);
        float expect = +135.7333333f;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);

        assertThat(StarLocationUtil.convertAngleFloatToString(actual), is(angle));
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
    public void test_convertHourStringToFloat_18dot00() {
        String hour = "18h00m";
        String expect = "18h 00.0m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertHourStringToFloat_18dot00_with_space() {
        String hour = "18h   00m";
        String expect = "18h 00.0m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertHourStringToFloat_18dot12() {
        String hour = "18h12m";
        String expect = "18h 12.0m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertHourStringToFloat_18dot12dot34() {
        String hour = "18h12.34m";
        String expect = "18h 12.3m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertHourStringToFloat_18dot12dot34_56() {
        String hour = "18h12.34m56s";
        String expect = "18h 13.3m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(expect));
    }

    @Test
    public void test_convertHourStringToFloat_18dot12dot34_56dot23() {
        String hour = "18h12.34m56.23s";
        String expect = "18h 13.3m";
        float actual = StarLocationUtil.convertHourStringToFloat(hour);
        assertThat(StarLocationUtil.convertHourFloatToString(actual), is(expect));
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
