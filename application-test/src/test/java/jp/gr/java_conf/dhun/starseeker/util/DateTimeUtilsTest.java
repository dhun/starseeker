/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jun
 * 
 */
public class DateTimeUtilsTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // for (String id : TimeZone.getAvailableIDs()) {
        // System.out.println(id);
        // }
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * {@link jp.gr.java_conf.dhun.starseeker.util.DateTimeUtils#toSameDateTime(java.util.Calendar, java.util.TimeZone)} のためのテスト・メソッド。
     */
    @Test
    public void test_toSameDateTime_20130329_070000_to_ハワイ() {
        System.out.println("--------------------------------------------------------------------------------");

        Calendar primaryCalendar = Calendar.getInstance();
        primaryCalendar.setTimeZone(TimeZone.getTimeZone("GMT+9:00"));
        primaryCalendar.clear();
        primaryCalendar.set(2013, 3 - 1, 29, 7, 0, 0);  // 2013/3/29 07:00:00(JST)

        TimeZone secondaryTimeZone = TimeZone.getTimeZone("US/Hawaii");
        Calendar secondaryCalendar = DateTimeUtils.toSameDateTime(primaryCalendar, secondaryTimeZone);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss(Z)");

        System.out.println("-- primary --");
        System.out.println(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(primaryCalendar) + ", timezone=" + primaryCalendar.getTimeZone().getDisplayName());
        System.out.println(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(primaryCalendar) + ", timezone=utc");

        System.out.println("-- secondary --");
        System.out.println(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(secondaryCalendar) + ", timezone=" + secondaryCalendar.getTimeZone().getDisplayName());
        System.out.println(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(secondaryCalendar) + ", timezone=utc");

        System.out.println("-- 日本標準時で比較 --");
        System.out.println(df.format(primaryCalendar.getTime()));
        System.out.println(df.format(secondaryCalendar.getTime()));

        assertThat(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(primaryCalendar), is("2013/03/29 07:00:00"));
        assertThat(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(secondaryCalendar), is("2013/03/29 07:00:00"));
        assertThat(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(primaryCalendar), is("2013/03/28 22:00:00"));
        assertThat(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(secondaryCalendar), is("2013/03/29 17:00:00"));
        assertThat(df.format(primaryCalendar.getTime()), is("2013/03/29 07:00:00(+0900)"));
        assertThat(df.format(secondaryCalendar.getTime()), is("2013/03/30 02:00:00(+0900)"));
    }

    @Test
    public void test_toSameDateTime_ハワイ_to_20130329_070000() {
        System.out.println("--------------------------------------------------------------------------------");

        Calendar primaryCalendar = Calendar.getInstance();
        primaryCalendar.setTimeZone(TimeZone.getTimeZone("US/Hawaii"));
        primaryCalendar.clear();
        primaryCalendar.set(2013, 3 - 1, 29, 7, 0, 0);  // 2013/3/29 07:00:00(JST)

        TimeZone secondaryTimeZone = TimeZone.getTimeZone("GMT+9:00");
        Calendar secondaryCalendar = DateTimeUtils.toSameDateTime(primaryCalendar, secondaryTimeZone);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss(Z)");

        System.out.println("-- primary --");
        System.out.println(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(primaryCalendar) + ", timezone=" + primaryCalendar.getTimeZone().getDisplayName());
        System.out.println(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(primaryCalendar) + ", timezone=utc");

        System.out.println("-- secondary --");
        System.out.println(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(secondaryCalendar) + ", timezone=" + secondaryCalendar.getTimeZone().getDisplayName());
        System.out.println(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(secondaryCalendar) + ", timezone=utc");

        System.out.println("-- 日本標準時で比較 --");
        System.out.println(df.format(primaryCalendar.getTime()));
        System.out.println(df.format(secondaryCalendar.getTime()));

        assertThat(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(primaryCalendar), is("2013/03/29 07:00:00"));
        assertThat(DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(secondaryCalendar), is("2013/03/29 07:00:00"));
        assertThat(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(primaryCalendar), is("2013/03/29 17:00:00"));
        assertThat(DateTimeUtils.toUtcYYYYMMDDHHMMSSWithSegment(secondaryCalendar), is("2013/03/28 22:00:00"));
        assertThat(df.format(primaryCalendar.getTime()), is("2013/03/30 02:00:00(+0900)"));
        assertThat(df.format(secondaryCalendar.getTime()), is("2013/03/29 07:00:00(+0900)"));
    }
}
