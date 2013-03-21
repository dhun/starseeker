/**
 *
 */
package jp.gr.java_conf.dhun.starseeker;

import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.app.Application;
import android.content.res.Configuration;

/**
 * スターシーカーのアプリケーション
 * 
 * @author jun
 * 
 */
public final class StarSeekerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.v(getClass(), "onCreate");

        LogUtils.init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.v(getClass(), "onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtils.v(getClass(), "onLowMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtils.v(getClass(), "onTerminate");
    }

}
