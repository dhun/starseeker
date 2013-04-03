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
     * 角度の文字列表現「-16°43'」を角度を示すfloat「-16.717」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 恐らく<strong>超遅い</strong>です.
     * 
     * @param angle 角度の文字列表現
     * @return 角度
     */
    public static final float convertAngleStringToFloat(String angle) {
        Pattern pattern = Pattern.compile("^([+-])?(\\d+)° *(\\d+)(\\.\\d+)?'$");
        Matcher matcher = pattern.matcher(angle);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("角度の文字列表現が不正. value=[%s]", angle));
        }

        String sign = matcher.group(1);
        String d = matcher.group(2);
        String mInt = matcher.group(3);
        String mDec = (null != matcher.group(4)) ? matcher.group(4) : ".0";

        float result = 0;
        result += Float.valueOf(d);
        result += Float.valueOf(mInt + mDec) / 60;
        result *= (null != sign && sign.equals("-")) ? -1 : +1;
        return result;
    }

    /**
     * 角度を示すfloat「-16.717」を角度の文字列表現「-16°43'」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 
     * @param angle 角度
     * @return 角度の文字列表現
     */
    @SuppressLint("DefaultLocale")
    public static final String convertAngleFloatToString(float angle) {
        int d = (int) (MathUtils.floor(angle));
        int m = (int) (MathUtils.round(Math.abs(angle - d) * 60));
        return String.format("%d°%02d'", d, m);
    }

    /**
     * 時間を示すfloat「18.69690」を時間の文字列表現「18h 41.8m」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 
     * @param hour 時間
     * @return 時間の文字列表現
     */
    @SuppressLint("DefaultLocale")
    public static final String convertHourFloatToString(float hour) {
        int h = (int) MathUtils.floor(hour);
        float ms = Math.abs(hour - h) * 60;
        int m = (int) MathUtils.floor(ms);
        int s = (int) MathUtils.round((ms - m) * 10); // 小数第一位までの概数
        return String.format("%dh %02d.%dm", h, m, s);
    }

    /**
     * 時間の文字列表現「18h 41.8m」を時間を示すfloat「18.69690」に変換します.<br/>
     * デバッグ用途で利用するつもり.<br/>
     * 恐らく<strong>超遅い</strong>です.
     * 
     * @param hour 時間の文字列表現
     * @return 時間
     */
    public static final float convertHourStringToFloat(String hour) {
        Pattern pattern = Pattern.compile("^([+-])?(\\d+)h( *(\\d+)(\\.\\d+)?m)( *(\\d+)(\\.\\d+)?s)?$");
        Matcher matcher = pattern.matcher(hour);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("時間の文字列表現が不正. value=[%s]", hour));
        }

        String sign = matcher.group(1);
        String h = matcher.group(2);
        String mInt = matcher.group(4);
        String mDec = (null != matcher.group(5)) ? matcher.group(5) : ".0";
        String sInt = (null != matcher.group(7)) ? matcher.group(7) : "0";
        String sDec = (null != matcher.group(8)) ? matcher.group(8) : ".0";

        float result = 0;
        result += Float.valueOf(h);
        result += Float.valueOf(mInt + mDec) / 60;
        result += Float.valueOf(sInt + sDec) / 60 / 60;
        result *= (null != sign && sign.equals("-")) ? -1 : +1;
        return result;
    }

}
