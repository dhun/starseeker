package jp.gr.java_conf.dhun.starseeker.system.model.panel;

import jp.gr.java_conf.dhun.starseeker.model.AstronomicalTheater.Rect;
import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerCoordinatesException;
import android.graphics.PointF;

/**
 * 天体シアターのパネルの抽象実装.<br/>
 * 
 * @author jun
 * 
 */
public abstract class AbstractAstronomicalTheaterPanel implements IAstronomicalTheaterPanel {

    private final AstronomicalTheaterPanelType panelType; // 天体シアターパネルの種類

    protected final int displayWidth;   // ディスプレイの横幅
    protected final int displayHeight;  // ディスプレイの高さ

    protected final Rect horizontalCoordinatesRect = new Rect(); // パネルの地平座標
    protected final Rect displayCoordinatesRect = new Rect();    // パネルのディスプレイ座標

    /**
     * コンストラクタ.<br/>
     * 
     * @param panelType 天体シアターパネルの種類
     * @param displayWidth ディスプレイの横幅
     * @param displayHeight ディスプレイの高さ
     */
    public AbstractAstronomicalTheaterPanel(AstronomicalTheaterPanelType panelType, int displayWidth, int displayHeight) {
        this.panelType = panelType;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
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
     * 指定された星の地平座標をディスプレイ座標に変換して、displayCoordinatesに割り当てます.<br/>
     * 
     * @param star 星
     * @param starDisplayCoordinates 星のディスプレイ座標を割り当てる変数
     */
    @Override
    public void remapToDisplayCoordinates(Star star, PointF starDisplayCoordinates) {
        float displayRatioX = calcAzimuthDisplayVector(star) / horizontalCoordinatesRect.width();
        float displayRatioY = calcAltitudeDisplayVector(star) / horizontalCoordinatesRect.height();

        starDisplayCoordinates.x = displayCoordinatesRect.xL + (displayCoordinatesRect.width() * displayRatioX);
        starDisplayCoordinates.y = displayCoordinatesRect.yT + (displayCoordinatesRect.height() * displayRatioY);
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