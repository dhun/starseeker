/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * 天体シアターのキャンバス.<br/>
 * 
 * @see http://1st.geocities.jp/shift486909/program/spin.html
 * @see http://takabosoft.com/19991215151157.html
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterCanvas {

    private static final int PANE_COUNT = 4;
    private static final int NORTH_EAST_PANE = 0;
    private static final int NORTH_WEST_PANE = 1;
    private static final int SOUTH_EAST_PANE = 2;
    private static final int SOUTH_WEST_PANE = 3;

    private final int width;
    private final int height;

    private final AstronomicalTheaterPanel[] panes;

    private final PointF pointCT = new PointF(); // キャンバスの中央の座標
    private final Rect rect = new Rect();
    // private final PointF pointLT = new PointF(); // キャンバスの左上の座標
    // private final PointF pointLB = new PointF(); // キャンバスの左下の座標
    // private final PointF pointRT = new PointF(); // キャンバスの右上の座標
    // private final PointF pointRB = new PointF(); // キャンバスの右下の座標

    private Orientations orientations; // 端末の回転状況

    private float fullRangeX;
    private float fullRangeY;
    private float halfRangeX;
    private float halfRangeY;

    public AstronomicalTheaterCanvas(int width, int height) {
        this.width = width;
        this.height = height;

        panes = new AstronomicalTheaterPanel[PANE_COUNT];
        for (int i = 0; i < PANE_COUNT; i++) {
            panes[i] = new AstronomicalTheaterPanel();
        }
    }

    /**
     * 天体シアターのキャンバスを準備します.<br/>
     * 
     * @param newOrientations
     */
    public void prepare(Orientations newOrientations) {
        this.orientations = newOrientations;

        // 端末の回転状態から、キャンバスに描画する範囲を算出
        if (orientations.getDisplayRotation().isPortrait()) {
            fullRangeX = 140;
            fullRangeY = 30;
        } else {
            fullRangeX = 90;
            fullRangeY = 60;
        }
        halfRangeX = fullRangeX / 2;
        halfRangeY = fullRangeY / 2;

        // 端末の方位とピッチから、キャンバスの座標を算出
        pointCT.x = (float) orientations.azimuth;
        pointCT.y = (float) orientations.pitch;

        rect.xL = pointCT.x - halfRangeX;
        rect.yT = pointCT.y - halfRangeY;
        rect.xR = rect.xL + fullRangeY;
        rect.yB = rect.yT - fullRangeX;

        // TODO スケールで座標を補正
        // TODO 端末の回転状況により座標を回転

        //
        assignAstronomical();
    }

    private void assignAstronomical() {
        if (rect.xL < rect.xR) {
            // ±180°をまたいでいない場合
            panes[NORTH_EAST_PANE].starRect.xL = Math.max(rect.xL, 0);
            panes[NORTH_EAST_PANE].starRect.xR = Math.max(rect.xR, 0);

            panes[NORTH_WEST_PANE].starRect.xL = Math.min(rect.xL, 0);
            panes[NORTH_WEST_PANE].starRect.xR = Math.min(rect.xR, 0);

        } else {
            // ±180°をまたいでいる場合
            panes[NORTH_EAST_PANE].starRect.xL = rect.xL;
            panes[NORTH_EAST_PANE].starRect.xR = +180;

            panes[NORTH_WEST_PANE].starRect.xL = -180;
            panes[NORTH_WEST_PANE].starRect.xR = rect.xR;
        }

        if (rect.yT <= +90) {
            // ＋90°をまたいでいない場合
            panes[NORTH_EAST_PANE].starRect.yT = rect.yT;
            panes[NORTH_EAST_PANE].starRect.yB = rect.yB;

            panes[NORTH_WEST_PANE].starRect.yT = rect.yT;
            panes[NORTH_WEST_PANE].starRect.yB = rect.yB;

            panes[SOUTH_EAST_PANE].starRect.setupZero();
            panes[SOUTH_WEST_PANE].starRect.setupZero();

        } else {
            // ＋90°をまたいでいる場合
            panes[NORTH_EAST_PANE].starRect.yT = +90;
            panes[NORTH_EAST_PANE].starRect.yB = rect.yB;

            panes[NORTH_WEST_PANE].starRect.yT = +90;
            panes[NORTH_WEST_PANE].starRect.yB = rect.yB;

            panes[SOUTH_EAST_PANE].starRect.yT = rect.yT;
            panes[SOUTH_EAST_PANE].starRect.yB = +90;
            panes[SOUTH_EAST_PANE].starRect.xL = panes[NORTH_EAST_PANE].starRect.xL;
            panes[SOUTH_EAST_PANE].starRect.xR = panes[NORTH_EAST_PANE].starRect.xR;

            panes[SOUTH_WEST_PANE].starRect.yT = rect.yT;
            panes[SOUTH_WEST_PANE].starRect.yB = +90;
            panes[SOUTH_WEST_PANE].starRect.xL = panes[NORTH_EAST_PANE].starRect.xL;
            panes[SOUTH_WEST_PANE].starRect.xR = panes[NORTH_EAST_PANE].starRect.xR;
        }
    }

    private void assignScreen() {
        if (rect.xL < rect.xR) {
            // ±180°をまたいでいない場合
            float consumeW = 0;
            if (panes[NORTH_WEST_PANE].starRect.hasRange()) {
                consumeW = width * (panes[NORTH_WEST_PANE].starRect.width() / fullRangeX);
                panes[NORTH_EAST_PANE].screenRect.xL = 0;
                panes[NORTH_EAST_PANE].screenRect.xR = consumeW;
            }
            if (panes[NORTH_WEST_PANE].starRect.hasRange()) {
                panes[NORTH_EAST_PANE].screenRect.xL = consumeW;
                panes[NORTH_EAST_PANE].screenRect.xR = width - consumeW;
            }

        } else {
            // ±180°をまたいでいない場合
            float consumeW = 0;
            if (panes[NORTH_WEST_PANE].starRect.hasRange()) {
                consumeW = width * (panes[NORTH_WEST_PANE].starRect.width() / fullRangeX);
                panes[NORTH_EAST_PANE].screenRect.xL = 0;
                panes[NORTH_EAST_PANE].screenRect.xR = consumeW;
            }
            if (panes[NORTH_WEST_PANE].starRect.hasRange()) {
                panes[NORTH_EAST_PANE].screenRect.xL = consumeW;
                panes[NORTH_EAST_PANE].screenRect.xR = width - consumeW;
            }
        }
    }

    /**
     * 指定された星がキャンバスの描画領域に含まれるかどうかを返却します.<br/>
     * 
     * @param star 星
     * @return 描画領域に含まれる場合はtrue
     */
    public boolean contains(Star star) {
        float starAzimuth = (float) star.getAzimuth();
        float starAltitude = (float) star.getAltitude();
        if (starAzimuth < rect.xL || rect.xR < starAzimuth) {
            return false;
        }
        if (starAltitude < rect.yT || rect.yB < starAltitude) {
            return false;
        }
        return true;
    }

    /**
     * 指定された星をキャンバスへ描画します.<br/>
     * 
     * @param star 星
     * @param canvas キャンバス
     */
    public void draw(Star star, Canvas canvas) {
        float starAzimuth = (float) star.getAzimuth();
        float starAltitude = (float) star.getAltitude();

        RectF rectF = new RectF();
        rectF.left = 50;
        rectF.top = 50;
        rectF.right = 100;
        rectF.bottom = 100;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawOval(rectF, paint);
    }

    public static class Rect {
        public float xL;
        public float xR;
        public float yT;
        public float yB;

        public void setupZero() {
            xL = 0f;
            xR = 0f;
            yT = 0f;
            yB = 0f;
        }

        public boolean hasRange() {
            return xL != 0 || yT != 0 || xR != 0 || yB != 0;
        }

        public float width() {
            return Math.abs((xL - xR));
        }

        public float height() {
            return Math.abs((yT - yB));
        }
    }

}
