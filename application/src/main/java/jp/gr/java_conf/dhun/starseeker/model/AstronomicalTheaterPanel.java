/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.model.AstronomicalTheater.Rect;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

/**
 * 天体シアターのパネル.<br/>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterPanel {

    public final Rect theaterRect = new Rect(); // 天体シアターの座標
    public final Rect displayRect = new Rect(); // ディスプレイの座標

    public boolean contains(Star star) {
        float starAzimuth = (float) star.getAzimuth();
        float starAltitude = (float) star.getAltitude();
        if (starAzimuth < theaterRect.xL || theaterRect.xR < starAzimuth) {
            return false;
        }
        if (starAltitude < theaterRect.yB || theaterRect.yT < starAltitude) {
            return false;
        }
        return true;
    }

    // ＞＞＞開発コード
    private TextPaint textPaint;
    private Paint gridPaint;
    {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);

        gridPaint = new Paint();
        gridPaint.setColor(Color.WHITE);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setPathEffect(new DashPathEffect(new float[] { 2, 2 }, 0));
    }

    // ＜＜＜開発コード

    /**
     * 描画します.<br/>
     * 
     * @param canvas Androidキャンバス
     */
    public void draw(Canvas canvas) {
        canvas.drawRect(displayRect.xL, displayRect.yT, displayRect.xR, displayRect.yB, gridPaint);
    }

    /**
     * 指定された星をキャンバスへ描画します.<br/>
     * 
     * @param star 星
     * @param canvas キャンバス
     */
    public void draw(Canvas canvas, Star star) {
        final float displayRatioX; // = Math.abs(starAzimuth - theaterRect.xL) / theaterRect.width();
        final float displayRatioY; // = Math.abs(starAltitude - theaterRect.yT) / theaterRect.height();

        if (theaterRect.xL >= 0) {
            // 東側のパネル
            // ・star.getAzimuth() >= 0
            // ・theaterRect.xL <= star.getAzimuth() <= theaterRect.xR
            displayRatioX = ((float) star.getAzimuth() - theaterRect.xL) / theaterRect.width();
        } else {
            // 西側のパネル
            // ・star.getAzimuth() < 0
            // ・theaterRect.xL <= star.getAzimuth() <= theaterRect.xR
            displayRatioX = ((float) star.getAzimuth() + Math.abs(theaterRect.xL)) / theaterRect.width();
        }

        if (theaterRect.yT > theaterRect.yB) {
            // 正面のパネル
            // ・star.getAltitude() は不定
            // ・theaterRect.yT >= star.getAltitude() >= theaterRect.yB
            displayRatioY = (theaterRect.yT - (float) star.getAltitude()) / theaterRect.height();
        } else {
            // 背面のパネル
            // ・star.getAltitude() >= 0
            // ・theaterRect.yT <= star.getAltitude() <= theaterRect.yB(+90)
            displayRatioY = ((float) star.getAltitude() - theaterRect.yT) / theaterRect.height();
        }

        float displayX = displayRect.xL + (displayRect.width() * displayRatioX);
        float displayY = displayRect.yT + (displayRect.height() * displayRatioY);

        final float STAR_SIZE = 30;
        RectF rectF = new RectF();
        rectF.left = displayX - (STAR_SIZE / 2);
        rectF.top = displayY - (STAR_SIZE / 2);
        rectF.right = rectF.left + STAR_SIZE;
        rectF.bottom = rectF.top + STAR_SIZE;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        canvas.drawOval(rectF, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(star.toString(), displayX, displayY, textPaint);
    }

    @Override
    public String toString() {
        return theaterRect.toString();
    }
}
