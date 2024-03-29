/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.renderer.indicator;

import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;
import android.graphics.Canvas;

/**
 * 数値形式の方位インジケータ
 * 
 * @author jun
 * 
 */
public class NumericAzimuthIndicator extends AbstractAzimuthIndicator implements IAzimuthIndicator {

    // 描画系
    private final float positiveSignHalfWidth;  // 「＋」記号の横幅の半分
    private final float negativeSignHalfWidth;  // 「－」記号の横幅の半分

    /**
     * コンストラクタ.<br/>
     * 
     * @param displayWidth ディスプレイの横幅(pixel)
     * @param displayHeight ディスプレイの高さ(pixel)
     */
    public NumericAzimuthIndicator(int displayWidth, int displayHeight) {
        super(displayWidth, displayHeight);

        positiveSignHalfWidth = textPaint.measureText("+") / 2;
        negativeSignHalfWidth = textPaint.measureText("-") / 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas, CoordinatesRect theaterHorizontalCoordinatesRect) {
        float degreeFractions = theaterHorizontalCoordinatesRect.xL % 1;
        int currDegree = (int) (theaterHorizontalCoordinatesRect.xL - degreeFractions);
        int incrDegree = +1;

        float degreeOnePixcel = displayWidth / theaterWidthAngle;

        float x = degreeFractions * degreeOnePixcel;

        float tickYBottom = displayHeight;
        float majorTickYT = displayHeight - 10;
        float minorTickYT = displayHeight - 5;

        String tickText;
        float signWidth;

        while (x < displayWidth) {
            if (currDegree % 10 == 0) {
                if (currDegree == 0) {
                    tickText = "0";
                    signWidth = 0;
                } else if (currDegree == +180 || currDegree == -180) {
                    tickText = "180";
                    signWidth = 0;
                } else if (currDegree < 0) {
                    tickText = String.valueOf(currDegree);
                    signWidth = positiveSignHalfWidth;
                } else {
                    tickText = "+" + String.valueOf(currDegree);
                    signWidth = negativeSignHalfWidth;
                }

                canvas.drawLine(x, majorTickYT, x, tickYBottom, tickPaint);
                canvas.drawText(tickText, x - signWidth, majorTickYT - 3, textPaint);
            } else {
                canvas.drawLine(x, minorTickYT, x, tickYBottom, tickPaint);
            }

            currDegree += incrDegree;
            if (currDegree == 0) {
                // incrDegree = +1;
            } else if (currDegree == 180) {
                currDegree = -180;
            }
            x += degreeOnePixcel;
        }
    }
}
