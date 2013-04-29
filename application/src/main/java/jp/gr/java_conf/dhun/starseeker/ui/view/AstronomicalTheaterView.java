/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.view;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.gr.java_conf.dhun.starseeker.R;
import jp.gr.java_conf.dhun.starseeker.system.StarSeekerEngine;
import jp.gr.java_conf.dhun.starseeker.system.StarSeekerEngineRefreshTask;
import jp.gr.java_conf.dhun.starseeker.system.listener.IStarSeekerListener;
import jp.gr.java_conf.dhun.starseeker.system.logic.observationsite.location.IObservationSiteLocationResolver;
import jp.gr.java_conf.dhun.starseeker.system.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.system.logic.terminal.orientations.TerminalOrientationsCalculatorFactory;
import jp.gr.java_conf.dhun.starseeker.system.model.StarSeekerEngineConfig;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask.Status;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 天体シアターのビュー.<br/>
 * 
 * @author jun
 */
public class AstronomicalTheaterView extends RelativeLayout implements SurfaceHolder.Callback2 {

    private static final int EXPECTED_FPS = 60;                             // FPSの期待値
    private static final int EXPECTED_FPS_OF_MILLIS = 1000 / EXPECTED_FPS;  // FPSの期待値に対するミリ秒

    private SurfaceView surfaceView;
    private ProgressBar progressBar;

    private StarSeekerEngine starSeekerEngine;          // スターシーカーエンジン
    private StarSeekerEngineConfig engineConfig;        // スターシーカーエンジンの設定
    private StarSeekerEngineRefreshTask refreshTask;    // スターシーカーエンジンの更新タスク
    private ScheduledExecutorService executorService;   // スターシーカーシステムのスレッドエクスキュータ

    private ObservationSiteLocation observationSiteLocation;

    private ITerminalOrientationsCalculator terminalStateResolver;

    public AstronomicalTheaterView(Context context) {
        super(context);
        init();
    }

    public AstronomicalTheaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_astronomical_theater, this, true);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void initialize() {
        // SurfaceViewを設定
        surfaceView.getHolder().addCallback(this);

        // スターシーカーシステムのエンジンを設定
        starSeekerEngine = new StarSeekerEngine(getContext().getApplicationContext());
        engineConfig = new StarSeekerEngineConfig();

        // 端末ステートリゾルバを設定
        int displayRotation = getDisplayRotation();
        terminalStateResolver = TerminalOrientationsCalculatorFactory.create(getContext().getApplicationContext(), displayRotation);
        terminalStateResolver.setOnChangeTerminalOrientationsListener(starSeekerEngine);
    }

    private int getDisplayRotation() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        LogUtils.d(getClass(), "displayRotation=" + display.getRotation());
        return display.getRotation();
    }

    public void configureObservationSiteLocation(Integer locationId, Date baseDate) {
        engineConfig.setObservationSiteLocationId(locationId);
        engineConfig.setCoordinatesCalculateBaseDate(baseDate);
    }

    public AstronomicalTheaterView configureExtractLowerstarMagnitude(float magnitude) {
        engineConfig.setExtractUpperStarMagnitude(magnitude);
        return this;
    }

    public void refresh() {
        refresh(null);
    }

    public void refresh(IObservationSiteLocationResolver resolver) {
        LogUtils.i(getClass(), "do refresh");

        synchronized (this) {
            cancelThreads();
        }

        refreshTask = new StarSeekerEngineRefreshTask(getContext().getApplicationContext()) {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (!result) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "天体シアターの準備に失敗しました.", Toast.LENGTH_SHORT).show();
                    return;
                }

                observationSiteLocation = getObservationSiteLocation();

                // スレッドエクスキュータの設定
                Runnable command = new Runnable() {
                    @Override
                    public void run() {
                        starSeekerEngine.calculate();

                        SurfaceHolder holder = surfaceView.getHolder();
                        Canvas canvas = holder.lockCanvas();
                        starSeekerEngine.draw(canvas);
                        holder.unlockCanvasAndPost(canvas);
                    }
                };
                executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(command, EXPECTED_FPS_OF_MILLIS, EXPECTED_FPS_OF_MILLIS, TimeUnit.MILLISECONDS);

                progressBar.setVisibility(View.GONE);
                refreshTask = null;
                LogUtils.i(getClass(), "end refresh");
            }
        };
        refreshTask.setStarSeekerEngine(starSeekerEngine);
        refreshTask.setObservationSiteLocationResolver(resolver);
        refreshTask.setObservationSiteLocation(observationSiteLocation);
        refreshTask.execute(engineConfig);
    }

    private synchronized void cancelThreads() {
        if (refreshTask != null && refreshTask.getStatus() == Status.RUNNING) {
            refreshTask.cancel(true);
            refreshTask = null;
        }
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }

    public void resume() {
        // 再開
        if (getVisibility() == View.VISIBLE) {
            // refresh();
            // starSeekerEngine.resume(); // エンジン
            terminalStateResolver.prepare(); // 端末ステートリゾルバ
        }
    }

    public void pause() {
        // 中断
        cancelThreads();
        starSeekerEngine.pause(); // エンジン
        terminalStateResolver.pause(); // 端末ステートリゾルバ
    }

    // @Override
    // public void setVisibility(int visibility) {
    // super.setVisibility(visibility);
    // if (visibility == View.VISIBLE) {
    // resume();
    // } else {
    // pause();
    // }
    // }

    // ================================================================================
    // SurfaceHolder.Callback2
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceCreated.");

        // スターシーカーシステムのスレッドエクスキュータを設定
        // executorService = null;

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
        starSeekerEngine.setDisplaySize(width, height);
        starSeekerEngine.setStarSeekerListener(new IStarSeekerListener() {
            @Override
            public void onException(Exception e) {
                LogUtils.e(getClass(), "システムエラー", e);
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
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceDestroyed.");

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceRedrawNeeded.");
    }
}
