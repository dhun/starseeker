package jp.gr.java_conf.dhun.starseeker.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jun
 * 
 */
public class StarLocationCalculatorTest {

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
    public void test_starLocationCalculator() {
        StarLocationCalculator target = new StarLocationCalculator();
        assertTrue(System.currentTimeMillis() - target.getBaseDate().getTime() < 1000); // 誤差は１秒未満
    }

    @Test
    public void test_starLocationCalculatorDate() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd").parse("1977/08/05");
        StarLocationCalculator target = new StarLocationCalculator(baseDate);
        assertTrue(baseDate.getTime() - target.getBaseDate().getTime() == 0);
    }

    @Test
    public void test_calculateMJD_１月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/01/01 21:00");
        double expect = 51544.875;
        runCalculateMJD(baseDate, expect);
    }

    @Test
    public void test_calculateMJD_２月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/02/01 21:00");
        double expect = 51575.875;
        runCalculateMJD(baseDate, expect);
    }

    @Test
    public void test_calculateMJD_３月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/03/01 21:00");
        double expect = 51604.875;
        runCalculateMJD(baseDate, expect);
    }

    @Test
    public void test_calculateMJD_Ｎ月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2013/04/05 06:07:08");
        double expect = 56387.254953703705;
        runCalculateMJD(baseDate, expect);
    }

    public void runCalculateMJD(Date baseDate, double expect) throws ParseException {
        StarLocationCalculator target = new StarLocationCalculator(baseDate);
        double actual = target.calculateMJD(baseDate);
        double delta = 1d / 24 / 60 / 60 / 1000 * 1000; // 1ミリ秒以内の誤差が許容範囲
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);
    }

    @Test
    public void test_calculateGreenwichSiderealTime() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        double mjd = 51544.50;
        StarLocationCalculator target = new StarLocationCalculator();
        Field field = StarLocationCalculator.class.getDeclaredField("mjd");
        field.setAccessible(true);
        field.set(target, mjd);

        double actual = target.calculateGreenwichSiderealTime();
        double expect = 18.69690;
        double delta = 1d / 24 / 60 / 60 / 1000 * 1000; // 1ミリ秒以内の誤差が許容範囲
        assertTrue(Math.abs(actual - expect) < (delta));

        assertThat(StarLocationCalculator.convertHourDoubleToHourString(actual), is("18h 41.8m"));
    }

    @Test
    public void test_calculateLocalSiderealTime_135dot44() {
        StarLocationCalculator target = new StarLocationCalculator();
        double greenwichSiderealTime = 18.69690;
        double longitude = 135.44;
        double actual = target.calculateLocalSiderealTime(greenwichSiderealTime, longitude);

        double expect = 3.745788888888889;
        double delta = 1d / 24 / 60 / 60 / 1000 * 1000; // 1ミリ秒以内の誤差が許容範囲
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationCalculator.convertHourDoubleToHourString(actual), is("3h 44.7m"));
    }

    @Test
    public void test_calculateHourAngle() {
        StarLocationCalculator target = new StarLocationCalculator();
        double localSiderealTime = StarLocationCalculator.convertHourStringToHourDouble("3h 44.7m");
        double rightAscension = StarLocationCalculator.convertHourStringToHourDouble("6h 45.1m");
        double actual = target.calculateHourAngle(localSiderealTime, rightAscension);

        double expect = StarLocationCalculator.convertHourStringToHourDouble("-3h 0.4m");
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourDoubleToHourString_18dot69690() {
        double hour = 18.69690;
        String actual = StarLocationCalculator.convertHourDoubleToHourString(hour);
        String expect = "18h 41.8m";
        assertThat(actual, is(expect));
    }

    @Test
    public void test_convertHourStringToHourDouble_18dot69690() {
        String hour = "18h 41.8m";
        double actual = StarLocationCalculator.convertHourStringToHourDouble(hour);
        double expect = 18.69690;

        double delta = 0.0003; // 誤差の許容範囲は適当
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationCalculator.convertHourDoubleToHourString(actual), is(hour));
    }

    // @Test
    // public void test_convertHourStringToHourDouble_18dot69690_plus() {
    // String hour = "+18h 41.8m";
    // double actual = StarLocationCalculator.convertHourStringToHourDouble(hour);
    // double expect = +18.69690;
    //
    // double delta = 0.0003; // 誤差の許容範囲は適当
    // double diff = Math.abs(actual - expect);
    // assertTrue("must be " + diff + " < " + delta, diff < delta);
    //
    // assertThat(StarLocationCalculator.convertHourDoubleToHourString(actual), is(hour));
    // }

    @Test
    public void test_convertHourStringToHourDouble_18dot69690_minus() {
        String hour = "-18h 41.8m";
        double actual = StarLocationCalculator.convertHourStringToHourDouble(hour);
        double expect = -18.69690;

        double delta = 0.0003; // 誤差の許容範囲は適当
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationCalculator.convertHourDoubleToHourString(actual), is(hour));
    }
}
