/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.renderer.star;

import jp.gr.java_conf.dhun.starseeker.model.Star;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * 星のレンダラ.<br/>
 * 
 * @author jun
 * 
 */
public class StarRenderer {

    // 描画系
    private final Paint namePaint;
    private final Paint starPaint;

    public StarRenderer() {
        namePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        namePaint.setColor(Color.WHITE);

        starPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        starPaint.setColor(Color.WHITE);
    }

    /**
     * 指定された星をキャンバスへ描画します.<br/>
     * 
     * @param star 星
     * @param canvas キャンバス
     */
    public void drawStar(Canvas canvas, Star star) {
        starPaint.setColor(resolveStarColor(star));

        final float radius = resolveStarRadius(star);
        canvas.drawCircle(star.getDisplayX(), star.getDisplayY(), radius, starPaint);

        String displayText = star.getDisplayText();
        if (null != displayText) {
            final float textMarginX = radius + 3;
            final float textMarginY = radius + 3;
            canvas.drawText(displayText, star.getDisplayX() + textMarginX, star.getDisplayY() + textMarginY, namePaint);
        }
    }

    private float resolveStarRadius(Star star) {
        float magnitude = star.getMagnitude();
        if (magnitude < -20) {
            // 太陽クラス
            return 15;
        } else if (magnitude < -12) {
            // 満月クラス
            return 10;
        } else if (magnitude < -10) {
            // 半月クラス
            return 10;
        } else if (magnitude < -6) {
            // 超新星クラス
            return 8;
        } else if (magnitude < -1) {
            return 6;
        } else if (magnitude < 0) {
            return 5;
        } else if (magnitude < +1) {
            return 4;
        } else if (magnitude < +2) {
            return 4;
        } else if (magnitude < +3) {
            return 3;
        } else if (magnitude < +4) {
            return 3;
        } else if (magnitude < +5) {
            return 2;
        } else if (magnitude < +6) {
            return 2;
        } else {
            return 2;
        }
    }

    private int resolveStarColor(Star star) {
        return Color.WHITE;
    }
}
