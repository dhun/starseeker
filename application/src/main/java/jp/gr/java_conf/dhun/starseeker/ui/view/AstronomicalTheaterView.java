/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author jun
 * 
 */
public class AstronomicalTheaterView extends SurfaceView implements SurfaceHolder.Callback2 {

    private static final int FPS = 60;
    private static final int FPS_OF_MILLIS = 1000 / FPS;

    private ScheduledExecutorService executorService;

    public AstronomicalTheaterView(Context context) {
        super(context);
        this.initialize();
    }

    public AstronomicalTheaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    private void initialize() {
        this.getHolder().addCallback(this);
    }

    // ================================================================================
    // SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.d(getClass(), "surfaceCreated.");

        Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    draw();
                } catch (Exception e) {
                    executorService.shutdown();
                    if (astronomicalTheaterViewListener != null) {
                        astronomicalTheaterViewListener.onException(e);
                    }
                }
            }
        };

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(command, FPS_OF_MILLIS, FPS_OF_MILLIS, TimeUnit.MILLISECONDS);
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

    // ================================================================================
    // custom
    private int count = 0;
    private final Paint paint = new Paint() {
        {
            setFlags(Paint.ANTI_ALIAS_FLAG);
            setColor(Color.WHITE);
        }
    };

    private final void draw() {
        Canvas canvas = getHolder().lockCanvas();

        canvas.drawColor(Color.BLACK);
        canvas.drawText("" + count, 100, 100, paint);
        count++;

        getHolder().unlockCanvasAndPost(canvas);
    }

    // ================================================================================
    // リスナ
    private AstronomicalTheaterViewListener astronomicalTheaterViewListener;

    public void setAstronomicalTheaterViewListener(AstronomicalTheaterViewListener listener) {
        this.astronomicalTheaterViewListener = listener;
    }

    public interface AstronomicalTheaterViewListener {
        void onException(Exception e);
    }
}
