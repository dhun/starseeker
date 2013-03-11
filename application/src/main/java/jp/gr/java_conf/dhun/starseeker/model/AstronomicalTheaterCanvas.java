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
    private final PointF pointLT = new PointF(); // キャンバスの左上の座標
    private final PointF pointLB = new PointF(); // キャンバスの左下の座標
    private final PointF pointRT = new PointF(); // キャンバスの右上の座標
    private final PointF pointRB = new PointF(); // キャンバスの右下の座標

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

        pointLT.x = pointCT.x - halfRangeX;
        pointLT.y = pointCT.y - halfRangeY;
        pointLB.x = pointLT.x;
        pointLB.y = pointLT.y - fullRangeX;
        pointRT.x = pointLT.x + fullRangeY;
        pointRT.y = pointLB.y;
        pointRB.x = pointRT.x;
        pointRB.y = pointLB.y;

        // TODO スケールで座標を補正
        // TODO 端末の回転状況により座標を回転

        //
        assign();
    }

    private void assign() {
        if (pointLT.x > 0) {
            panes[NORTH_EAST_PANE].pointLT.x = (pointRT.x > 0) ? 0 : -180;
        } else {
            panes[NORTH_EAST_PANE].pointLT.x = Math.min(0, pointLT.x);
        }
        if (pointRT.x < 0) {
            panes[NORTH_EAST_PANE].pointRT.x = (pointLT.x < 0) ? 0 : +180;
        } else {
            panes[NORTH_EAST_PANE].pointRT.x = Math.max(0, pointRT.x);
        }
    }

    /**
     * 指定された星がキャンバスの描画領域に含まれるかどうかを返却します.<br/>
     * 
     * @param star 星
     * @return 描画領域に含まれる場合はtrue
     */
    public boolean contains(Star star) {
        float minX = Math.min(pointLT.x, pointLB.x);
        float maxX = Math.max(pointRT.x, pointRB.x);
        float minY = Math.min(pointLT.y, pointRT.y);
        float maxY = Math.max(pointLB.y, pointRB.y);

        float starAzimuth = (float) star.getAzimuth();
        float starAltitude = (float) star.getAltitude();
        if (starAzimuth < minX || maxX < starAzimuth) {
            return false;
        }
        if (starAltitude < minY || maxY < starAltitude) {
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

        RectF rect = new RectF();
        rect.left = 50;
        rect.top = 50;
        rect.right = 100;
        rect.bottom = 100;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawOval(rect, paint);
    }
}
