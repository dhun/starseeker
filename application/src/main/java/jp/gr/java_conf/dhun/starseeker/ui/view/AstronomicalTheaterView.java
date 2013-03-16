/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.gr.java_conf.dhun.starseeker.system.StarSeekerEngine;
import jp.gr.java_conf.dhun.starseeker.system.listener.IStarSeekerListener;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * 天体シアターのビュー.<br/>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterView extends SurfaceView implements SurfaceHolder.Callback2 {

    private static final int EXPECTED_FPS = 60; // FPSの期待値
    private static final int EXPECTED_FPS_OF_MILLIS = 1000 / EXPECTED_FPS; // FPSの期待値に対するミリ秒

    private StarSeekerEngine starSeekerEngine;          // スターシーカーシステムのエンジン
    private ScheduledExecutorService executorService;   // スターシーカーシステムの実行スレッド

    public AstronomicalTheaterView(Context context) {
        super(context);
        this.initialize();
    }

    public AstronomicalTheaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    private void initialize() {
        starSeekerEngine = new StarSeekerEngine();
        getHolder().addCallback(this);
    }

    // ================================================================================
    // SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceCreated.");

        Runnable command = new Runnable() {
            @Override
            public void run() {
                Canvas canvas = getHolder().lockCanvas();
                starSeekerEngine.main(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        };

        starSeekerEngine.setStarSeekerListener(new IStarSeekerListener() {
            @Override
            public void onException(Exception e) {
                executorService.shutdown();

                String message = "システムエラーが発生しました. 処理を中断します.\n" //
                        + e.getMessage();
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(command, EXPECTED_FPS_OF_MILLIS, EXPECTED_FPS_OF_MILLIS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceDestroyed.");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.d(getClass(), "surfaceChanged." + String.format("format=%d, width=%d, height=%d", format, width, height));
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceRedrawNeeded.");
    }
}
