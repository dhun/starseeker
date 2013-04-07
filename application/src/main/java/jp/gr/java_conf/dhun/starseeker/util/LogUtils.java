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

    private static boolean initialized = false;

    private LogUtils() {
    }

    public static final void init() {
        initialized = true;
    }

    public static final void v(Class<?> clazz, String message) {
        if (!isInitialized()) {
            return;
        }
        Log.v(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void d(Class<?> clazz, String message) {
        if (!isInitialized()) {
            return;
        }
        Log.d(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void i(Class<?> clazz, String message) {
        if (!isInitialized()) {
            return;
        }
        Log.i(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void w(Class<?> clazz, String message) {
        if (!isInitialized()) {
            return;
        }
        Log.w(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void w(Class<?> clazz, String message, Throwable throwable) {
        if (!isInitialized()) {
            return;
        }
        Log.w(APP_NAME + ":" + clazz.getSimpleName(), message, throwable);
    }

    public static final void e(Class<?> clazz, String message) {
        if (!isInitialized()) {
            return;
        }
        Log.e(APP_NAME + ":" + clazz.getSimpleName(), message);
    }

    public static final void e(Class<?> clazz, String message, Throwable throwable) {
        if (!isInitialized()) {
            return;
        }
        Log.e(APP_NAME + ":" + clazz.getSimpleName(), message, throwable);
    }

    private static final boolean isInitialized() {
        return initialized;
    }
}
