/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.renderer.indicator;

import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;

/**
 * 数値形式の高度インジケータ
 * 
 * @author jun
 * 
 */
public class NumericAltitudeIndicator extends AbstractAltitudeIndicator implements IAltitudeIndicator {

    // 描画系
    private final float textAdjustToCenterHeight;

    /**
     * コンストラクタ.<br/>
     * 
     * @param displayWidth ディスプレイの横幅(pixel)
     * @param displayHeight ディスプレイの高さ(pixel)
     */
    public NumericAltitudeIndicator(int displayWidth, int displayHeight) {
        super(displayWidth, displayHeight);

        FontMetrics fontMetrics = textPaint.getFontMetrics();
        if (null == fontMetrics) { // FIXME for robolectric
            textAdjustToCenterHeight = 0;
        } else {
            textAdjustToCenterHeight = (fontMetrics.ascent + fontMetrics.descent) / 2;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas, CoordinatesRect theaterHorizontalCoordinatesRect) {
        float degreeFractions = theaterHorizontalCoordinatesRect.yT % 1;
        int currDegree = (int) (theaterHorizontalCoordinatesRect.yT - degreeFractions);
        int incrDegree = +1;
        if (theaterHorizontalCoordinatesRect.yT > +90) {
            currDegree = +90 - (currDegree - 90); // +100 -> +80
            incrDegree = +1;
        } else {
            incrDegree = -1;
        }

        float degreeOnePixcel = displayHeight / theaterHeightAngle;

        float y = degreeFractions * degreeOnePixcel;

        float tickXLeft = 0;
        float majorTickXR = +10;
        float minorTickXR = +5;

        String tickText;
        float margin = 3;

        while (y < displayHeight) {
            if (currDegree % 10 == 0) {
                if (currDegree == 0) {
                    tickText = "0";
                } else if (currDegree < 0) {
                    tickText = String.valueOf(currDegree);
                } else {
                    tickText = "+" + String.valueOf(currDegree);
                }

                canvas.drawLine(tickXLeft, y, majorTickXR, y, tickPaint);
                canvas.drawText(tickText, majorTickXR + margin, y - textAdjustToCenterHeight, textPaint);
            } else {
                canvas.drawLine(tickXLeft, y, minorTickXR, y, tickPaint);
            }

            currDegree += incrDegree;
            if (currDegree == 0) {
                // incrDegree = +1;
            } else if (currDegree == +90) {
                incrDegree = -1;
            }
            y += degreeOnePixcel;
        }
    }
}
