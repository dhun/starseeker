/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 日時のユーティリティ.<br/>
 * 
 * @author jun
 * 
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * timezoneで指定されたタイムゾーンにおける、指定されたカレンダーのローカル時間と同じ時間に変換します.<br/>
     * 
     * @param calendar 変換元のカレンダー
     * @param timezone 変換後のタイムゾーン
     */
    public static Calendar toSameDateTime(Calendar calendar, TimeZone timezone) {
        Calendar secondarycCalendar = Calendar.getInstance(timezone);
        secondarycCalendar.clear();
        secondarycCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        secondarycCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        secondarycCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        secondarycCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        secondarycCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        secondarycCalendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
        secondarycCalendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));

        return secondarycCalendar;
    }

    /**
     * 指定されたカレンダーのローカル時間を「yyyy/MM/dd HH:mm:ss」形式の文字列表現に変換します.<br/>
     */
    public static String toLocalYYYYMMDDHHMMSSWithSegment(Calendar calendar) {
        return calendar.get(Calendar.YEAR)
                + "/" + to2DigitString(calendar.get(Calendar.MONTH) + 1)
                + "/" + to2DigitString(calendar.get(Calendar.DAY_OF_MONTH))
                + " " + to2DigitString(calendar.get(Calendar.HOUR_OF_DAY))
                + ":" + to2DigitString(calendar.get(Calendar.MINUTE))
                + ":" + to2DigitString(calendar.get(Calendar.SECOND));
    }

    /**
     * 指定されたカレンダーのUTC時間を「yyyy/MM/dd HH:mm:ss」形式の文字列表現に変換します.<br/>
     */
    public static String toUtcYYYYMMDDHHMMSSWithSegment(Calendar calendar) {
        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utc.clear();
        utc.setTime(calendar.getTime());
        return toLocalYYYYMMDDHHMMSSWithSegment(utc);
    }

    private static String to2DigitString(int value) {
        assert (0 < value);

        if (value < 10) {
            return "0" + value;
        } else {
            return String.valueOf(value);
        }
    }
}
