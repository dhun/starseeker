/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model;

import android.annotation.SuppressLint;

/**
 * Frame/Secondのカウンタ
 * 
 * @author jun
 * 
 */
public class FpsCounter {

    private long startTime;
    private long finishTime;

    private float curFps;
    private float minFps = 60f;
    private float maxFps = 0f;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void finish() {
        finishTime = System.currentTimeMillis();

        curFps = 1000f / (finishTime - startTime);
        minFps = Math.min(curFps, minFps);
        maxFps = Math.max(curFps, maxFps);
    }

    @SuppressLint("DefaultLocale")
    public String getDisplayText() {
        return String.format("fps=[%5.1f], minFps=[%5.1f], maxFps=[%5.1f]" //
                , curFps
                , minFps
                , maxFps);
    }
}
