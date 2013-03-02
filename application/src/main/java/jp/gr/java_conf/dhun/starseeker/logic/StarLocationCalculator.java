/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * 星の座標計算機.<br/>
 * 
 * @author jun
 * 
 */
public class StarLocationCalculator {

    /** 座標算出の基準日時 */
    private final Date baseDateTime;

    /** 座標算出基準日時に対するＭＪＤ */
    private final double mjd;

    /**
     * コンストラクタ.<br/>
     * システム日時が座標算出の基準日時になります.<br/>
     */
    public StarLocationCalculator() {
        this(new Date(System.currentTimeMillis()));
    }

    /**
     * コンストラクタ.<br/>
     * 指定された日時が座標算出の基準日時になります.<br/>
     * 
     * @param baseDateTime 座標算出の基準日時
     */
    public StarLocationCalculator(Date baseDateTime) {
        this.baseDateTime = baseDateTime;
        this.mjd = calculateMJD(baseDateTime);
    }

    /**
     * 指定された日時に対するMJDを算出します.<br/>
     * 
     * @see http://star.gs/nyumon/koseiji.htm
     * @param baseDateTime MJDを算出対象とする日時
     * @return MJD
     * 
     * @see http://star.gs/nyumon/koseiji.htm
     */
    public double calculateMJD(Date baseDateTime) {
        // グレゴリオ暦（1582年10月15日以降）の西暦年をY、月をM、日をDとする。
        // ただし1月のはM=13、2月はM=14、YはY=Y-1とする。
        // MJD=365.25Y+Y/400-Y/100+30.59(M-2)+D+1721088.5-2400000.5

        // site2.
        // http://t-sakachan.net/omosirozatugaku/yuriusu2.htm
        // MJD＝int[365.25 * y] + int[y / 400] - int[y / 100]
        // + int[30.59 * (m - 2)] + d - 678912

        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDateTime);
        double y = cal.get(Calendar.YEAR);
        double m = cal.get(Calendar.MONTH);
        double d = cal.get(Calendar.DAY_OF_MONTH);

        y += (m <= 1) ? -1 : 0;
        m += (m <= 1) ? 13 : 1;
        d += cal.get(Calendar.HOUR_OF_DAY) / 24d;
        d += cal.get(Calendar.MINUTE) / 24d / 60;
        d += cal.get(Calendar.SECOND) / 24d / 60 / 60;
        // d += cal.get(Calendar.MILLISECOND) / 24d / 60 / 60 / 1000;

        return extractInteger(365.25 * y) + extractInteger(y / 400) - extractInteger(y / 100) + extractInteger(30.59 * (m - 2)) + d + 1721088.5 - 2400000.5;
    }

    private double extractInteger(double value) {
        return Math.floor(value);
    }

    private static/* TODO to util */double floor(double value) {
        return (value >= 0) ? Math.floor(value) : Math.ceil(value);
    }

    private static/* TODO to util */double round(double value) {
        return (value >= 0) ? Math.round(value) : Math.round(value); // -0.5の場合のroundがまずい
    }

    /**
     * グリニッジ恒星時を算出します.<br/>
     * 
     * @return グリニッジ恒星時(h). 経度０°において、南中している星の赤経
     */
    public double calculateGreenwichSiderealTime() {
        // MJD = 51544.50
        // θG = 24hx(0.67239＋1.00273781x(MJD-40000.0))　2000.0分点に準拠
        // θG : グリニッジ恒星時。(0.67239＋1.00273781x(MJD-40000.0))の値から小数点以下のみ
        // 18.69690h = 18h 41.8m

        double tmpAnswer = (0.67239 + 1.00273781 * (mjd - 40000.0));
        double ofDecimal = tmpAnswer % 1;
        double ofHour = 24 * ofDecimal;
        return ofHour;
    }

    /**
     * 地方恒星時を算出します.<br/>
     * 
     * @param greenwichSiderealTime グリニッジ恒星時
     * @param longitude 経度. 経度、東経を - 西経を + とする. -180から+180
     * @return 地方恒星時(h). 経度λにおいて南中している星の赤経
     */
    public double calculateLocalSiderealTime(double greenwichSiderealTime, double longitude) {
        assert (-180 <= longitude && longitude <= +180);

        // θ = θG-λ = 18h 41.8m -(-(135+44/60)/15 ) = 18h 41.8m -(-9h 2.9m ) = 27h 44.7m
        // 27h 44.7m - 24h = 3h 44.7m
        double degree = floor(longitude);
        double minute = round((longitude - degree) * 100);

        final double DAYS_OF_HOUR = 24;
        double result = greenwichSiderealTime - (-(degree + minute / 60) / 15);
        result -= DAYS_OF_HOUR;
        return result;
    }

    /**
     * 時角を算出します.<br/>
     * 
     * @param localSiderealTime 地方恒星時(θ). 単位は(h)
     * @param rightAscension 赤経
     * @return 時角(H)
     */
    public double calculateHourAngle(double localSiderealTime, double rightAscension) {
        // H = θ-α = 3h 44.7m-6h45.1m = -3h 0.4m = -45.1°

        return localSiderealTime - rightAscension;
    }

    /**
     * 時間を示すdouble「18.69690」を時間の文字列表現「18h 41.8m」に変換します.<br/>
     * デバッグ用途で利用するつもり.
     * 
     * @param hour 時間
     * @return 時間の文字列表現
     */
    @SuppressLint("DefaultLocale")
    public static String convertHourDoubleToHourString(double hour) {
        int h = (int) floor(hour);
        double ms = Math.abs(hour - h) * 60;
        int m = (int) floor(ms);
        int s = (int) round((ms - m) * 10); // 小数第一位までの概数
        return String.format("%dh %d.%dm", h, m, s);
    }

    /**
     * 時間の文字列表現「18h 41.8m」を時間を示すdouble「18.69690」に変換します.<br/>
     * デバッグ用途で利用するつもり. 恐らく超遅い
     * 
     * @param hour 時間の文字列表現
     * @return 時間
     */
    @SuppressLint("DefaultLocale")
    public static double convertHourStringToHourDouble(String hour) {
        Pattern pattern = Pattern.compile("^([+-])?(\\d+)h (\\d+)\\.(\\d)m$");
        Matcher matcher = pattern.matcher(hour);
        if (!matcher.find() || matcher.groupCount() != 4) {
            throw new IllegalArgumentException(String.format("時間の文字列表現が不正. value=[%s]", hour));
        }

        String sign = matcher.group(1);
        String h = matcher.group(2);
        String m = matcher.group(3);
        String s = matcher.group(4);

        double result = 0;
        result += Double.valueOf(h);
        result += Double.valueOf(m) / 60;
        result += Double.valueOf(s) / 60 / 10;
        result *= (null != sign && sign.equals("-")) ? -1 : +1;
        return result;
    }

    // ************************************************************************************************************************
    // setter, getter
    // ************************************************************************************************************************
    public Date getBaseDate() {
        return baseDateTime;
    }

    public double getMjd() {
        return mjd;
    }
}
