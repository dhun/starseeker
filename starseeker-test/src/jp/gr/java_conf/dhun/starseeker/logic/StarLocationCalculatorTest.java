package jp.gr.java_conf.dhun.starseeker.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.TestUtils;
import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link StarLocationCalculator}のテスト
 * 
 * @author jun
 * 
 */
public class StarLocationCalculatorTest {

    // 観測地点の座標
    private double longitude; // 経度(λ). 東経を - 西経を + とする. -180から+180
    private double latitude;  // 緯度(ψ). 北緯を + 南緯を - とする.. +90から -90

    private StarLocationCalculator target;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // ロケーションは京都
        longitude = StarLocationUtil.convertAngleStringToDouble("135°44'"); // 東経135°44'
        latitude = StarLocationUtil.convertAngleStringToDouble("35°01'");   // 北緯 35°01'

        target = new StarLocationCalculator(longitude, latitude);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_starLocationCalculator() {
        TestUtils.asserAllowingError(target.getBaseDateTime().getTime(), System.currentTimeMillis(), 1000); // 誤差は１秒未満
    }

    @Test
    public void test_starLocationCalculatorDate() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd").parse("1977/08/05");
        StarLocationCalculator target = new StarLocationCalculator(longitude, latitude, baseDate);
        assertTrue(baseDate.getTime() - target.getBaseDateTime().getTime() == 0);
    }

    /**
     * ２０００年１月１日２１時の京都（経度：東経135°44'、緯度：北緯35°01'）における<br/>
     * シリウス（赤経：06h45.1m、赤緯：-16°43'）の位置。
     * 
     * @throws ParseException
     */
    @Test
    public void test_locate() throws ParseException {
        String rightAscension = "06h45.1m";
        String declination = "-16°43'";
        Star star = new Star(rightAscension, declination);

        double longitude = StarLocationUtil.convertAngleStringToDouble("135°44'");
        double latitude = StarLocationUtil.convertAngleStringToDouble("35°01'");
        Date baseDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/01/01 21:00");
        target = new StarLocationCalculator(longitude, latitude, baseDateTime);
        target.locate(star);
        TestUtils.asserAllowingError(star.getAltitude(), 22.87, TestUtils.DELTA_ANGLE);
        TestUtils.asserAllowingError(star.getAzimuth(), 132.58, 0.02); // XXX 誤差が大きすぎ？ TestUtils.DELTA_ANGLE);
    }

    @Test
    public void test_calculateMJD_１月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/01/01 21:00");
        double expect = 51544.5;
        runCalculateMJD(baseDate, expect);
    }

    @Test
    public void test_calculateMJD_２月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/02/01 21:00");
        double expect = 51575.5;
        runCalculateMJD(baseDate, expect);
    }

    @Test
    public void test_calculateMJD_３月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2000/03/01 21:00");
        double expect = 51604.5;
        runCalculateMJD(baseDate, expect);
    }

    @Test
    public void test_calculateMJD_Ｎ月() throws ParseException {
        Date baseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2013/04/05 06:07:08");
        double expect = 56386.87995370384;
        runCalculateMJD(baseDate, expect);
    }

    public void runCalculateMJD(Date baseDate, double expect) throws ParseException {
        double actual = target.calculateMJD(baseDate);
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_HOUR);
    }

    @Test
    public void test_calculateGreenwichSiderealTime() {
        double mjd = 51544.50;
        double actual = target.calculateGreenwichSiderealTime(mjd);
        double expect = 18.69690;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_HOUR);

        assertThat(StarLocationUtil.convertHourDoubleToString(actual), is("18h 41.8m"));
    }

    @Test
    public void test_calculateLocalSiderealTime_135dot44() {
        double greenwichSiderealTime = 18.69690;
        double longitude = StarLocationUtil.convertAngleStringToDouble("135°44'");
        try {
            Field field = StarLocationCalculator.class.getDeclaredField("longitude");
            field.setAccessible(true);
            field.set(target, longitude);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double actual = target.calculateLocalSiderealTime(greenwichSiderealTime);
        double expect = 3.745788888888889;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_HOUR);

        assertThat(StarLocationUtil.convertHourDoubleToString(actual), is("3h 44.7m"));
    }

    @Test
    public void test_calculateHourAngle() {
        double localSiderealTime = StarLocationUtil.convertHourStringToDouble("3h 44.7m");
        double rightAscension = StarLocationUtil.convertHourStringToDouble("6h 45.1m");
        double actual = target.calculateHourAngle(localSiderealTime, rightAscension);
        double expect = -45.1;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);
    }

    @Test
    public void test_convertEquatorialCoordinateToHorizontalCoordinate1() {
        double declination = StarLocationUtil.convertAngleStringToDouble("-16°43'");
        double hourAngle = -45.1;
        double actual = target.convertEquatorialCoordinateToHorizontalCoordinate1(declination, hourAngle);
        double expect = 0.67841;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_CONVERT_EQUATORIAL_COORDINATE_TO_HORIZONTAL_COORDINATE);
    }

    @Test
    public void test_convertEquatorialCoordinateToHorizontalCoordinate2() {
        double latitude = StarLocationUtil.convertAngleStringToDouble("35°01'");
        try {
            Field field = StarLocationCalculator.class.getDeclaredField("latitude");
            field.setAccessible(true);
            field.set(target, latitude);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double declination = StarLocationUtil.convertAngleStringToDouble("-16°43'");
        double hourAngle = -45.1;
        double actual = target.convertEquatorialCoordinateToHorizontalCoordinate2(declination, hourAngle);
        double expect = -0.62350;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_CONVERT_EQUATORIAL_COORDINATE_TO_HORIZONTAL_COORDINATE);
    }

    @Test
    public void test_convertEquatorialCoordinateToHorizontalCoordinate3() {
        double latitude = StarLocationUtil.convertAngleStringToDouble("35°01'");
        try {
            Field field = StarLocationCalculator.class.getDeclaredField("latitude");
            field.setAccessible(true);
            field.set(target, latitude);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double declination = StarLocationUtil.convertAngleStringToDouble("-16°43'");
        double hourAngle = -45.1;
        double actual = target.convertEquatorialCoordinateToHorizontalCoordinate3(declination, hourAngle);
        double expect = 0.38860;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_CONVERT_EQUATORIAL_COORDINATE_TO_HORIZONTAL_COORDINATE);
    }

    @Test
    public void test_calculateAzimuth() {
        double convertValue1 = 0.67841;
        double convertValue2 = -0.62350;
        double actual = target.calculateAzimuth(convertValue1, convertValue2);
        double expect = 132.58;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);
    }

    @Test
    public void test_calculateAltitude() {
        double convertValue2 = -0.62350;
        double convertValue3 = +0.38860;
        double azimuth = 132.58;
        double actual = target.calculateAltitude(convertValue2, convertValue3, azimuth);
        double expect = 22.87;
        TestUtils.asserAllowingError(actual, expect, TestUtils.DELTA_ANGLE);
    }

    @Test
    public void test_sin() {
        TestUtils.asserAllowingError(target.sin(0), 0.0, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.sin(30), 0.50000, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.sin(60), 0.8660, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.sin(89), 0.9998, TestUtils.DELTA_TRIG_FUNC);
    }

    @Test
    public void test_cos() {
        TestUtils.asserAllowingError(target.cos(0), 1.0, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.cos(30), 0.86600, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.cos(60), 0.50000, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.cos(89), 0.0175, TestUtils.DELTA_TRIG_FUNC);
    }

    @Test
    public void test_tan() {
        TestUtils.asserAllowingError(target.tan(0), 0.0, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.tan(30), 0.57740, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.tan(60), 1.73210, TestUtils.DELTA_TRIG_FUNC);
        TestUtils.asserAllowingError(target.tan(89), 57.2900, TestUtils.DELTA_TRIG_FUNC);
    }

    @Test
    public void test_atan() {
        TestUtils.asserAllowingError(target.atan(0.0), 0.0, TestUtils.DELTA_ANGLE);
        TestUtils.asserAllowingError(target.atan(0.57740), 30, TestUtils.DELTA_ANGLE);
        TestUtils.asserAllowingError(target.atan(1.73210), 60, TestUtils.DELTA_ANGLE);
        TestUtils.asserAllowingError(target.atan(57.2900), 89, TestUtils.DELTA_ANGLE);
    }
}
