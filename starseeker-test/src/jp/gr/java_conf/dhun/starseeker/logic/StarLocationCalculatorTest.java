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
 *
 */

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

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#StarLocationCalculator()} のためのテスト・メソッド。
     */
    @Test
    public void testStarLocationCalculator() {
        StarLocationCalculator target = new StarLocationCalculator();
        assertTrue(System.currentTimeMillis() - target.getBaseDate().getTime() < 50);
    }

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#StarLocationCalculator(java.util.Date)} のためのテスト・メソッド。
     * 
     * @throws ParseException
     */
    @Test
    public void testStarLocationCalculatorDate() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd").parse("1977/08/05");
        StarLocationCalculator target = new StarLocationCalculator(baseDate);
        assertTrue(baseDate.getTime() - target.getBaseDate().getTime() == 0);
    }

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#calculateMJD(java.util.Date)} のためのテスト・メソッド。
     * 
     * @throws ParseException
     */
    @Test
    public void testCalculateMJD_１月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/01/01 21:00");
        double expect = 51544.875;
        runCalculateMJD(baseDate, expect);
    }

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#calculateMJD(java.util.Date)} のためのテスト・メソッド。
     * 
     * @throws ParseException
     */
    @Test
    public void testCalculateMJD_２月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/02/01 21:00");
        double expect = 51575.875;
        runCalculateMJD(baseDate, expect);
    }

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#calculateMJD(java.util.Date)} のためのテスト・メソッド。
     * 
     * @throws ParseException
     */
    @Test
    public void testCalculateMJD_３月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/03/01 21:00");
        double expect = 51604.875;
        runCalculateMJD(baseDate, expect);
    }

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#calculateMJD(java.util.Date)} のためのテスト・メソッド。
     * 
     * @throws ParseException
     */
    @Test
    public void testCalculateMJD_Ｎ月() throws ParseException {
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

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.logic.StarLocationCalculator#calculateGreenwichSiderealTime()} のためのテスト・メソッド。
     * 
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Test
    public void testCalculateGreenwichSiderealTime() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        double mjd = 51544.50;
        StarLocationCalculator target = new StarLocationCalculator();
        Field field = StarLocationCalculator.class.getDeclaredField("mjd");
        field.setAccessible(true);
        field.set(target, mjd);

        double actual = target.calculateGreenwichSiderealTime();
        double expect = 18.69690;
        double delta = 1d / 24 / 60 / 60 / 1000 * 1000; // 1ミリ秒以内の誤差が許容範囲
        assertTrue(Math.abs(actual - expect) < (delta));

        assertThat(StarLocationCalculator.convertHourToHourString(actual), is("18h 41.8m"));
    }

    @Test
    public void testCalculateLocalSiderealTime_135dot44() {
        StarLocationCalculator target = new StarLocationCalculator();
        double greenwichSiderealTime = 18.69690;
        double longitude = 135.44;
        double actual = target.calculateLocalSiderealTime(greenwichSiderealTime, longitude);

        double expect = 3.745788888888889;
        double delta = 1d / 24 / 60 / 60 / 1000 * 1000; // 1ミリ秒以内の誤差が許容範囲
        double diff = Math.abs(actual - expect);
        assertTrue("must be " + diff + " < " + delta, diff < delta);

        assertThat(StarLocationCalculator.convertHourToHourString(actual), is("3h 44.7m"));
    }

    @Test
    public void testconvertHourToHourString_18dot69690() {
        double hour = 18.69690;
        String actual = StarLocationCalculator.convertHourToHourString(hour);
        String expect = "18h 41.8m";
        assertThat(actual, is(expect));
    }
}
