package jp.gr.java_conf.dhun.starseeker.system.model.panel;

import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerCoordinatesException;
import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextPaint;

/**
 * 天体シアターのパネルの抽象実装.<br/>
 * 
 * @author jun
 * 
 */
public abstract class AstronomicalTheaterPanel implements IAstronomicalTheaterPanel {

    private final AstronomicalTheaterPanelType panelType; // 天体シアターパネルの種類

    protected final int displayWidth;   // ディスプレイの横幅(pixel)
    protected final int displayHeight;  // ディスプレイの高さ(pixel)

    protected final CoordinatesRect horizontalCoordinatesRect = new CoordinatesRect(); // パネルの地平座標
    protected final CoordinatesRect displayCoordinatesRect = new CoordinatesRect();    // パネルのディスプレイ座標(pixel)

    // 描画系
    private final Paint gridPaint;

    private final Paint pointPaint;
    private final float pointTextAdjustToTop;
    private final float pointTextAdjustToCenter;

    /**
     * コンストラクタ.<br/>
     * 
     * @param panelType 天体シアターパネルの種類
     * @param displayWidth ディスプレイの横幅
     * @param displayHeight ディスプレイの高さ
     */
    public AstronomicalTheaterPanel(AstronomicalTheaterPanelType panelType, int displayWidth, int displayHeight) {
        this.panelType = panelType;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        gridPaint = new Paint();
        gridPaint.setColor(Color.WHITE);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setPathEffect(new DashPathEffect(new float[] { 2, 2 }, 0));

        pointPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.WHITE);
        pointPaint.setTextAlign(Paint.Align.LEFT);

        FontMetrics fontMetrics = pointPaint.getFontMetrics();
        if (null == fontMetrics) { // FIXME for robolectric
            pointTextAdjustToTop = 0;
            pointTextAdjustToCenter = 0;
        } else {
            pointTextAdjustToTop = fontMetrics.top;
            pointTextAdjustToCenter = (fontMetrics.ascent + fontMetrics.descent) / 2;
        }
    }

    /**
     * 指定された星の地平座標がパネルに含まれるかを判定します.<br/>
     * 
     * @param star 星
     * @return 含まれていればtrue.
     */
    @Override
    public boolean contains(Star star) {
        float starAzimuth = star.getAzimuth();
        float starAltitude = star.getAltitude();
        if (starAzimuth < horizontalCoordinatesRect.xL || horizontalCoordinatesRect.xR < starAzimuth) {
            return false;
        }
        if (starAltitude < horizontalCoordinatesRect.yB || horizontalCoordinatesRect.yT < starAltitude) {
            return false;
        }
        return true;
    }

    /**
     * 指定された星の地平座標をディスプレイ座標に変換して、星のディスプレイ座標に割り当てます.<br/>
     * 
     * @param star 星
     */
    @Override
    public void remapDisplayCoordinates(Star star) {
        float displayRatioX = calcAzimuthDisplayVector(star) / horizontalCoordinatesRect.width();
        float displayRatioY = calcAltitudeDisplayVector(star) / horizontalCoordinatesRect.height();

        star.setDisplayX(displayCoordinatesRect.xL + (displayCoordinatesRect.width() * displayRatioX));
        star.setDisplayY(displayCoordinatesRect.yT + (displayCoordinatesRect.height() * displayRatioY));
    }

    /**
     * パネルの左端を基点とした、指定された星の方位ディスプレイ・ベクトルを算出します.<br/>
     * 
     * @param star 星
     * @return 方位ディスプレイ・ベクトル. 常に正の値
     */
    protected float calcAzimuthDisplayVector(Star star) {
        if (panelType.isEast()) {
            // 東側のパネル
            // ・star.getAzimuth() >= 0
            // ・horizontalCoordinatesRect.xL <= star.getAzimuth() <= horizontalCoordinatesRect.xR
            return star.getAzimuth() - horizontalCoordinatesRect.xL;
        } else {
            // 西側のパネル
            // ・star.getAzimuth() < 0
            // ・horizontalCoordinatesRect.xL <= star.getAzimuth() <= horizontalCoordinatesRect.xR
            return star.getAzimuth() + Math.abs(horizontalCoordinatesRect.xL);
        }
    }

    /**
     * パネルの上端を基点とした、指定された星の高度ディスプレイ・ベクトルを算出します.<br/>
     * 
     * @param star 星
     * @return 方位ディスプレイ・ベクトル. 常に正の値
     */
    protected float calcAltitudeDisplayVector(Star star) {
        if (panelType.isFace()) {
            // 正面のパネル
            // ・star.getAltitude() は不定
            // ・horizontalCoordinatesRect.yT >= star.getAltitude() >= horizontalCoordinatesRect.yB
            return horizontalCoordinatesRect.yT - star.getAltitude();
        } else {
            // 背面のパネル
            // ・star.getAltitude() >= 0
            // ・horizontalCoordinatesRect.yT <= star.getAltitude() <= horizontalCoordinatesRect.yB(+90)
            return star.getAltitude() - horizontalCoordinatesRect.yT;
        }
    }

    /**
     * 描画します.<br/>
     * 
     * @param canvas Androidキャンバス
     */
    public void draw(Canvas canvas) {
        canvas.drawRect(displayCoordinatesRect.xL, displayCoordinatesRect.yT, displayCoordinatesRect.xR, displayCoordinatesRect.yB, gridPaint);

        if (horizontalCoordinatesRect.hasRegion()) {
            pointPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(panelType.toString(), displayCoordinatesRect.centerX(), displayCoordinatesRect.centerY() - pointTextAdjustToCenter, pointPaint);

            drawCoordinatesTop(canvas, horizontalCoordinatesRect.xL, horizontalCoordinatesRect.yT, displayCoordinatesRect.xL, displayCoordinatesRect.yT, Paint.Align.LEFT);
            drawCoordinatesTop(canvas, horizontalCoordinatesRect.xR, horizontalCoordinatesRect.yT, displayCoordinatesRect.xR, displayCoordinatesRect.yT, Paint.Align.RIGHT);
            drawCoordinatesBottom(canvas, horizontalCoordinatesRect.xL, horizontalCoordinatesRect.yB, displayCoordinatesRect.xL, displayCoordinatesRect.yB, Paint.Align.LEFT);
            drawCoordinatesBottom(canvas, horizontalCoordinatesRect.xR, horizontalCoordinatesRect.yB, displayCoordinatesRect.xR, displayCoordinatesRect.yB, Paint.Align.RIGHT);
        }
    }

    @SuppressLint("DefaultLocale")
    private void drawCoordinatesTop(Canvas canvas, float horizontalCoordinatesX, float horizontalCoordinatesY, float displayCoordinatesX, float displayCoordinatesY, Paint.Align align) {
        String text = " [" + (int) horizontalCoordinatesX + ", " + (int) horizontalCoordinatesY + "] ";
        pointPaint.setTextAlign(align);
        canvas.drawText(text, displayCoordinatesX, displayCoordinatesY - pointTextAdjustToTop, pointPaint);
    }

    @SuppressLint("DefaultLocale")
    private void drawCoordinatesBottom(Canvas canvas, float horizontalCoordinatesX, float horizontalCoordinatesY, float displayCoordinatesX, float displayCoordinatesY, Paint.Align align) {
        float offsetY = (panelType.isFace()) ? 30 : 0;
        String text = " [" + (int) horizontalCoordinatesX + ", " + (int) horizontalCoordinatesY + "] ";
        pointPaint.setTextAlign(align);
        canvas.drawText(text, displayCoordinatesX, displayCoordinatesY - offsetY, pointPaint);
    }

    /**
     * パネルの座標を検証します.
     */
    @Override
    public void verifyCoordinatesRect() {
        if (panelType.isEast()) {
            verifyForEastPanel();
        } else {
            verifyForWestPanel();
        }
        if (panelType.isFace()) {
            verifyForFacePanel();
        } else {
            verifyForBackPanel();
        }
    }

    /**
     * 東側パネル向けの検証
     */
    protected void verifyForEastPanel() {
        // 地平座標の検証
        if (horizontalCoordinatesRect.hasWidth()) {
            if (!(0 <= horizontalCoordinatesRect.xL)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(0 <= horizontalCoordinatesRect.xL)");
            }
            if (!(0 <= horizontalCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(0 <= horizontalCoordinatesRect.xR)");
            }
            if (!(horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR)");
            }
        }

        // ディスプレイ座標の検証
        if (displayCoordinatesRect.hasWidth()) {
            if (!(0 <= displayCoordinatesRect.xL)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.xL)");
            }
            if (!(0 <= displayCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.xR)");
            }
            if (!(displayCoordinatesRect.xL < displayCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(displayCoordinatesRect.xL < displayCoordinatesRect.xR)");
            }
        }
    }

    /**
     * 西側パネル向けの検証
     */
    protected void verifyForWestPanel() {
        // 地平座標の検証
        if (horizontalCoordinatesRect.hasWidth()) {
            if (!(0 >= horizontalCoordinatesRect.xL)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(0 >= horizontalCoordinatesRect.xL)");
            }
            if (!(0 >= horizontalCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(0 >= horizontalCoordinatesRect.xR)");
            }
            if (!(horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR)");
            }
        }

        // ディスプレイ座標の検証
        if (displayCoordinatesRect.hasWidth()) {
            if (!(0 <= displayCoordinatesRect.xL)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.xL)");
            }
            if (!(0 <= displayCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.xR)");
            }
            if (!(displayCoordinatesRect.xL < displayCoordinatesRect.xR)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(displayCoordinatesRect.xL < displayCoordinatesRect.xR)");
            }
        }
    }

    /**
     * 正面パネル向けの検証
     */
    protected void verifyForFacePanel() {
        // 地平座標の検証
        if (horizontalCoordinatesRect.hasHeight()) {
            if (!(horizontalCoordinatesRect.yB < horizontalCoordinatesRect.yT)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(horizontalCoordinatesRect.yB < horizontalCoordinatesRect.yT)");
            }
        }

        // ディスプレイ座標の検証
        if (displayCoordinatesRect.hasHeight()) {
            if (!(0 <= displayCoordinatesRect.yT)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.yT)");
            }
            if (!(0 < displayCoordinatesRect.yB)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 < displayCoordinatesRect.yB)");
            }
            if (!(displayCoordinatesRect.yT < displayCoordinatesRect.yB)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(displayCoordinatesRect.yT < displayCoordinatesRect.yB)");
            }
        }
    }

    /**
     * 背面パネル向けの検証
     */
    protected void verifyForBackPanel() {
        // 地平座標の検証
        if (horizontalCoordinatesRect.hasHeight()) {
            if (!(horizontalCoordinatesRect.yB > horizontalCoordinatesRect.yT)) {
                throw new StarSeekerCoordinatesException(panelType, horizontalCoordinatesRect, "!(horizontalCoordinatesRect.yB > horizontalCoordinatesRect.yT)");
            }
        }

        // ディスプレイ座標の検証
        if (displayCoordinatesRect.hasHeight()) {
            if (!(0 <= displayCoordinatesRect.yT)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 <= displayCoordinatesRect.yT)");
            }
            if (!(0 < displayCoordinatesRect.yB)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(0 < displayCoordinatesRect.yB)");
            }
            if (!(displayCoordinatesRect.yT < displayCoordinatesRect.yB)) {
                throw new StarSeekerCoordinatesException(panelType, displayCoordinatesRect, "!(displayCoordinatesRect.yT < displayCoordinatesRect.yB)");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] [%s]", panelType, horizontalCoordinatesRect.toString());
    }
}