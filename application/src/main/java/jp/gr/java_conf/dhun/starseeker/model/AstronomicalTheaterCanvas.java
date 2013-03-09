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

    private final int theaterWidth;
    private final int theaterHeight;

    private Orientations orientations; // 端末の回転状況

    private final PointF pointLT = new PointF(); // キャンバスの左上の座標
    private final PointF pointLB = new PointF(); // キャンバスの左下の座標
    private final PointF pointRT = new PointF(); // キャンバスの右上の座標
    private final PointF pointRB = new PointF(); // キャンバスの右下の座標

    public AstronomicalTheaterCanvas(int theaterWidth, int theaterHeight) {
        this.theaterWidth = theaterWidth;
        this.theaterHeight = theaterHeight;
    }

    /**
     * 天体シアターのキャンバスを準備します.<br/>
     * 
     * @param newOrientations
     */
    public void prepare(Orientations newOrientations) {
        this.orientations = newOrientations;

        // 端末の回転状態から、キャンバスに描画する範囲を算出
        final float rangeX;
        final float rangeY;
        if (orientations.getDisplayRotation().isPortrait()) {
            rangeX = 140;
            rangeY = 30;
        } else {
            rangeX = 90;
            rangeY = 60;
        }

        // 端末の方位とピッチから、キャンバスの座標を算出
        pointLT.x = (float) orientations.azimuth - (rangeX / 2);
        pointLT.y = (float) orientations.pitch - (rangeY / 2);
        pointLB.x = pointLT.x;
        pointLB.y = pointLT.y - rangeY;
        pointRT.x = pointLT.x + rangeX;
        pointRT.y = pointLB.y;
        pointRB.x = pointRT.x;
        pointRB.y = pointLB.y;

        // TODO スケールで座標を補正
        // TODO 端末の回転状況により座標を回転
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
