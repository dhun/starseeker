package jp.gr.java_conf.dhun.starseeker.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jun
 * 
 */
public class StarLocationCalculatorTest {

    // 誤差の許容値
    private static final double DELTA_HOUR = 1.0 / 24 / 60 / 60 / 1000 * 1000; // 時間. 1ミリ秒未満
    private static final double DELTA_ANGLE = 0.000001; // 角度

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
        double diff = Math.abs(actual - expect);
        assertTrue(String.format("誤差が許容値より大きい, 誤差=[%f], 許容値=[%f]", diff, DELTA_HOUR), diff < DELTA_HOUR);
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
        double diff = Math.abs(actual - expect);
        assertTrue(String.format("誤差が許容値より大きい, 誤差=[%f], 許容値=[%f]", diff, DELTA_HOUR), diff < DELTA_HOUR);

        assertThat(StarLocationUtil.convertHourDoubleToHourString(actual), is("18h 41.8m"));
    }

    @Test
    public void test_calculateLocalSiderealTime_135dot44() {
        StarLocationCalculator target = new StarLocationCalculator();
        double greenwichSiderealTime = 18.69690;
        double longitude = 135.44;
        double actual = target.calculateLocalSiderealTime(greenwichSiderealTime, longitude);

        double expect = 3.745788888888889;
        double diff = Math.abs(actual - expect);
        assertTrue(String.format("誤差が許容値より大きい, 誤差=[%f], 許容値=[%f]", diff, DELTA_HOUR), diff < DELTA_HOUR);

        assertThat(StarLocationUtil.convertHourDoubleToHourString(actual), is("3h 44.7m"));
    }

    @Test
    public void test_calculateHourAngle() {
        StarLocationCalculator target = new StarLocationCalculator();
        double localSiderealTime = StarLocationUtil.convertHourStringToHourDouble("3h 44.7m");
        double rightAscension = StarLocationUtil.convertHourStringToHourDouble("6h 45.1m");
        double actual = target.calculateHourAngle(localSiderealTime, rightAscension);

        double expect = -45.1;
        double diff = Math.abs(actual - expect);
        assertTrue(String.format("誤差が許容値より大きい, 誤差=[%f], 許容値=[%f]", diff, DELTA_ANGLE), diff < DELTA_ANGLE);
    }

}
