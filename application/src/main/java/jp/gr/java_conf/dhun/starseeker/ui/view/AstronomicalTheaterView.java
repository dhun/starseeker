/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.view;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.gr.java_conf.dhun.starseeker.system.StarSeekerEngine;
import jp.gr.java_conf.dhun.starseeker.system.listener.IStarSeekerListener;
import jp.gr.java_conf.dhun.starseeker.system.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.system.logic.terminal.orientations.TerminalOrientationsCalculatorFactory;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 天体シアターのビュー.<br/>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterView extends SurfaceView implements SurfaceHolder.Callback2 {

    private static final int EXPECTED_FPS = 60;                            // FPSの期待値
    private static final int EXPECTED_FPS_OF_MILLIS = 1000 / EXPECTED_FPS; // FPSの期待値に対するミリ秒

    private StarSeekerEngine starSeekerEngine;        // スターシーカーシステムのエンジン
    private ScheduledExecutorService executorService; // スターシーカーシステムのスレッドエクスキュータ

    private ITerminalOrientationsCalculator terminalStateResolver;

    public AstronomicalTheaterView(Context context) {
        super(context);
    }

    public AstronomicalTheaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initialize() {
        // SurfaceViewを設定
        getHolder().addCallback(this);

        // スターシーカーシステムのエンジンを設定
        starSeekerEngine = new StarSeekerEngine(getContext());

        // 端末ステートリゾルバを設定
        int displayRotation = getDisplayRotation();
        terminalStateResolver = TerminalOrientationsCalculatorFactory.create(getContext(), displayRotation);
        terminalStateResolver.setOnChangeTerminalOrientationsListener(starSeekerEngine);
    }

    public void configureObservationSiteLocation(double longitude, double latitude, Calendar baseCalendar) {
        starSeekerEngine.configureObservationCondition(longitude, latitude, baseCalendar);
    }

    public void configureExtractLowerstarMagnitude(float magnitude) {
        starSeekerEngine.configureExtractUpperStarMagnitude(magnitude);
    }

    private int getDisplayRotation() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        LogUtils.d(getClass(), "displayRotation=" + display.getRotation());
        return display.getRotation();
    }

    public void resume() {
        // 再開
        if (getVisibility() == View.VISIBLE) {
            starSeekerEngine.resume();          // エンジン
            terminalStateResolver.prepare();    // 端末ステートリゾルバ
        }
    }

    public void pause() {
        // 中断
        starSeekerEngine.pause();           // エンジン
        terminalStateResolver.pause();      // 端末ステートリゾルバ
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        resume();
    }

    // ================================================================================
    // SurfaceHolder.Callback2
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceCreated.");

        // スターシーカーシステムのスレッドエクスキュータを設定
        executorService = null;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.d(getClass(), "surfaceChanged. " + String.format("format=%d, width=%d, height=%d", format, width, height));

        if (null != executorService) {
            // surfaceChangedの呼び出しは「called at least once」と記載されているため、
            // ２回以上呼ばれたらひとまずエラーにしてみる
            // throw new RuntimeException("starSeekerEngine is not null."); // FIXME 非表示にしてから再表示すると２回呼ばれるかんじ...
        }

        // エンジンの設定
        starSeekerEngine.prepare(width, height);
        starSeekerEngine.setStarSeekerListener(new IStarSeekerListener() {
            @Override
            public void onException(Exception e) {
                executorService.shutdown();

                // 頻繁に発生する処理ではないため、毎回インスタンスを作ることでインスタンスフィールドを減らしてる
                new Handler(getContext().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String message = "システムエラーが発生しました. 処理を中断します.";
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // スレッドエクスキュータの設定
        Runnable command = new Runnable() {
            @Override
            public void run() {
                starSeekerEngine.calculate();

                Canvas canvas = getHolder().lockCanvas();
                starSeekerEngine.draw(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        };
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(command, EXPECTED_FPS_OF_MILLIS, EXPECTED_FPS_OF_MILLIS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceDestroyed.");

        executorService.shutdown();
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceRedrawNeeded.");
    }
}
