/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import android.util.Log;

/**
 * ログユーティリティ.<br>
 * 
 * @author jun
 * 
 */
public final class LogUtils {

    private static final String APP_NAME = "starseeker";

    private LogUtils() {
    }

    public static final void i(Class<?> clazz, String message) {
        Log.i(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void d(Class<?> clazz, String message) {
        Log.d(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void v(Class<?> clazz, String message) {
        Log.v(APP_NAME + ":" + clazz.getSimpleName(), message);
    }
}
