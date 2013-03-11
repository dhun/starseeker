/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.activity;

import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

/**
 * @author jun
 * 
 */
public class SurfaceViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        this.setContentView(rootLayout);

        CustomSurfaceView surfaceView = new CustomSurfaceView(this);
        rootLayout.addView(surfaceView);
    }

    private class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback2, Runnable {
        private final SurfaceHolder holder;
        private Thread thread;

        public CustomSurfaceView(Context context) {
            super(context);

            holder = getHolder();
            holder.addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            LogUtils.d(getClass(), "surfaceCreated.");

            thread = new Thread(this, "surfaceViewThread");
            thread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            LogUtils.d(getClass(), "surfaceDestroyed.");

            cancel = true;
            thread = null;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            LogUtils.d(getClass(), "surfaceChanged.");
        }

        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {
            LogUtils.d(getClass(), "surfaceRedrawNeeded.");
        }

        @Override
        public void run() {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);

            while (true) {
                if (cancel) {
                    break;
                }

                count++;
                Canvas canvas = holder.lockCanvas();

                canvas.drawColor(Color.BLACK);
                canvas.drawText("" + count, 100, 100, paint);

                holder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
            }
        }

        private int count = 0;
        private boolean cancel;
    }
}
