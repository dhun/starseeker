/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * 星座標の計算ユーティリティ.<br/>
 * 
 * @author jun
 * 
 */
public final class StarLocationUtil {

    private StarLocationUtil() {
    }

    /**
     * 角度の文字列表現「-16°43'」を角度を示すdouble「-16.717」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 恐らく<strong>超遅い</strong>です.
     * 
     * @param angle 角度の文字列表現
     * @return 角度
     */
    public static final double convertAngleStringToDouble(String angle) {
        Pattern pattern = Pattern.compile("^([+-])?(\\d+)°(\\d+)'$");
        Matcher matcher = pattern.matcher(angle);
        if (!matcher.find() || matcher.groupCount() != 3) {
            throw new IllegalArgumentException(String.format("角度の文字列表現が不正. value=[%s]", angle));
        }

        String sign = matcher.group(1);
        String d = matcher.group(2);
        String m = matcher.group(3);

        double result = 0;
        result += Double.valueOf(d);
        result += Double.valueOf(m) / 60;
        result *= (null != sign && sign.equals("-")) ? -1 : +1;
        return result;
    }

    /**
     * 角度を示すdouble「-16.717」を角度の文字列表現「-16°43'」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 
     * @param angle 角度
     * @return 角度の文字列表現
     */
    @SuppressLint("DefaultLocale")
    public static final String convertAngleDoubleToString(double angle) {
        int d = (int) (MathUtils.floor(angle));
        int m = (int) (MathUtils.round(Math.abs(angle - d) * 60));
        return String.format("%d°%d'", d, m);
    }

    /**
     * 時間を示すdouble「18.69690」を時間の文字列表現「18h 41.8m」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 
     * @param hour 時間
     * @return 時間の文字列表現
     */
    @SuppressLint("DefaultLocale")
    public static final String convertHourDoubleToString(double hour) {
        int h = (int) MathUtils.floor(hour);
        double ms = Math.abs(hour - h) * 60;
        int m = (int) MathUtils.floor(ms);
        int s = (int) MathUtils.round((ms - m) * 10); // 小数第一位までの概数
        return String.format("%dh %d.%dm", h, m, s);
    }

    /**
     * 時間の文字列表現「18h 41.8m」を時間を示すdouble「18.69690」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 恐らく<strong>超遅い</strong>です.
     * 
     * @param hour 時間の文字列表現
     * @return 時間
     */
    @SuppressLint("DefaultLocale")
    public static final double convertHourStringToDouble(String hour) {
        Pattern pattern = Pattern.compile("^([+-])?(\\d+)h *(\\d+)\\.(\\d)m$");
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

}
