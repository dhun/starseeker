/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model.panel;

import jp.gr.java_conf.dhun.starseeker.model.Constellation;
import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;
import jp.gr.java_conf.dhun.starseeker.system.renderer.indicator.IAltitudeIndicator;
import jp.gr.java_conf.dhun.starseeker.system.renderer.indicator.IAzimuthIndicator;
import jp.gr.java_conf.dhun.starseeker.system.renderer.indicator.NumericAltitudeIndicator;
import jp.gr.java_conf.dhun.starseeker.system.renderer.indicator.NumericAzimuthIndicator;
import jp.gr.java_conf.dhun.starseeker.system.renderer.star.StarRenderer;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 天体シアター.<br/>
 * 
 * @see http://1st.geocities.jp/shift486909/program/spin.html
 * @see http://takabosoft.com/19991215151157.html
 * 
 * @author jun
 * 
 */
public class AstronomicalTheater {

    private static final float DEFAULT_PORTRAIT_THEATER_WIDTH = 140;  // 縦向き）天体シアターの基底の横幅
    private static final float DEFAULT_PORTRAIT_THEATER_HEIGHT = 30;  // 縦向き）天体シアターの基底の高さ
    private static final float DEFAULT_LANDSCAPE_THEATER_WIDTH = 90;  // 横向き）天体シアターの基底の横幅
    private static final float DEFAULT_LANDSCAPE_THEATER_HEIGHT = 60; // 横向き）天体シアターの基底の高さ

    // パネル
    private final AstronomicalTheaterPanel eastFacePanel; // X軸が0～+180, Y軸が～+90を割り当てる東側正面パネル
    private final AstronomicalTheaterPanel eastBackPanel; // X軸が0～-180, Y軸が～+90を割り当てる西側正面パネル
    private final AstronomicalTheaterPanel westFacePanel; // X軸が0～+180, Y軸が+90～を割り当てる東側背面パネル
    private final AstronomicalTheaterPanel westBackPanel; // X軸が0～-180, Y軸が+90～を割り当てる西側背面パネル
    private final AstronomicalTheaterPanel[] panels = new AstronomicalTheaterPanel[4];

    // サイズ系
    private final int displayWidth;     // ディスプレイの横幅(pixel)
    private final int displayHeight;    // ディスプレイの高さ(pixel)

    private float theaterWidth;         // 天体シアターの横幅(pixel)
    private float theaterHeight;        // 天体シアターの高さ(pixel)
    private final CoordinatesRect horizontalCoordinatesRect = new CoordinatesRect(); // 天体シアターの地平座標

    // 描画系
    private final Paint backgroundPaint;

    // レンダラ
    private final StarRenderer starRenderer;
    private final IAzimuthIndicator azimuthIndicator;
    private final IAltitudeIndicator altitudeIndicator;

    /**
     * コンストラクタ.<br/>
     * 
     * @param displayWidth ディスプレイの横幅
     * @param displayHeight ディスプレイの高さ
     */
    public AstronomicalTheater(int displayWidth, int displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        // レンダラ
        starRenderer = new StarRenderer();
        azimuthIndicator = new NumericAzimuthIndicator(displayWidth, displayHeight);
        altitudeIndicator = new NumericAltitudeIndicator(displayWidth, displayHeight);

        setTheaterSizeToDefault();

        // 天体シアターのパネル
        eastFacePanel = new AstronomicalTheaterEastFacePanel(displayWidth, displayHeight);
        eastBackPanel = new AstronomicalTheaterEastBackPanel(displayWidth, displayHeight);
        westFacePanel = new AstronomicalTheaterWestFacePanel(displayWidth, displayHeight);
        westBackPanel = new AstronomicalTheaterWestBackPanel(displayWidth, displayHeight);
        panels[0] = eastFacePanel;
        panels[1] = eastBackPanel;
        panels[2] = westFacePanel;
        panels[3] = westBackPanel;

        // 描画系の設定
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);
    }

    /**
     * 天体シアターのサイズを、ディスプレイサイズに応じた規定値に設定します
     */
    public void setTheaterSizeToDefault() {
        // 端末の回転状態から、天体シアターのサイズを算出
        float theaterWidth, theaterHeight;
        if (displayWidth > displayHeight) {
            theaterWidth = DEFAULT_PORTRAIT_THEATER_WIDTH;
        } else {
            theaterWidth = DEFAULT_LANDSCAPE_THEATER_WIDTH;
        }
        theaterHeight = (int) ((double) displayHeight / displayWidth * theaterWidth);
        setTheaterSize(theaterWidth, theaterHeight);
    }

    /**
     * 天体シアターのサイズを設定します
     * 
     * @param theaterWidth 天体シアターの横幅
     * @param theaterHeight 天体シアターの高さ
     */
    public void setTheaterSize(float theaterWidth, float theaterHeight) {
        this.theaterWidth = theaterWidth;
        this.theaterHeight = theaterHeight;

        azimuthIndicator.setTheaterWidthAngle(theaterWidth);
        altitudeIndicator.setTheaterHeightAngle(theaterHeight);
    }

    /**
     * 端末の方位とピッチから、天体シアターの座標を算出します
     * 
     * @param terminalAzimuth 端末の方位
     * @param terminalPitch 端末のピッチ
     */
    public void calculateCoordinatesRect(float terminalAzimuth, float terminalPitch) {
        // 端末の方位とピッチから、天体シアターの座標を算出
        horizontalCoordinatesRect.xL = adjustAzimuth(terminalAzimuth - theaterWidth / 2);
        horizontalCoordinatesRect.xR = adjustAzimuth(horizontalCoordinatesRect.xL + theaterWidth);

        horizontalCoordinatesRect.yT = -terminalPitch + theaterHeight / 2;
        horizontalCoordinatesRect.yB = horizontalCoordinatesRect.yT - theaterHeight;

        assignPanelTheaterRect();
        assignPanelDisplayRect();

        for (AstronomicalTheaterPanel panel : panels) {
            panel.prepareContains();
        }
    }

    private float adjustAzimuth(float azimuth) {
        if (azimuth > +180) {
            return azimuth - 360;
        }
        if (azimuth < -180) {
            return azimuth + 360;
        }
        return azimuth;
    }

    /**
     * 天体シアターの座標をパネルに割り当てます
     */
    /* package */void assignPanelTheaterRect() {
        // 表面のX軸
        if (horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR) {
            // X軸の ±180°をまたいでいない場合
            eastFacePanel.horizontalCoordinatesRect.xL = Math.max(horizontalCoordinatesRect.xL, 0);
            eastFacePanel.horizontalCoordinatesRect.xR = Math.max(horizontalCoordinatesRect.xR, 0);

            westFacePanel.horizontalCoordinatesRect.xL = Math.min(horizontalCoordinatesRect.xL, 0);
            westFacePanel.horizontalCoordinatesRect.xR = Math.min(horizontalCoordinatesRect.xR, 0);

            if (0 < horizontalCoordinatesRect.xL) {
                // 東側を向いているとき
                eastBackPanel.horizontalCoordinatesRect.xL = 0;
                eastBackPanel.horizontalCoordinatesRect.xR = 0;

                westBackPanel.horizontalCoordinatesRect.xL = -180 + eastFacePanel.horizontalCoordinatesRect.xR;
                westBackPanel.horizontalCoordinatesRect.xR = -180 + eastFacePanel.horizontalCoordinatesRect.xL;

            } else if (horizontalCoordinatesRect.xR < 0) {
                // 西側を向いているとき
                eastBackPanel.horizontalCoordinatesRect.xL = +180 + westFacePanel.horizontalCoordinatesRect.xR;
                eastBackPanel.horizontalCoordinatesRect.xR = +180 + westFacePanel.horizontalCoordinatesRect.xL;

                westBackPanel.horizontalCoordinatesRect.xL = 0;
                westBackPanel.horizontalCoordinatesRect.xR = 0;

            } else {
                // X軸の 0° をまたいでいる場合
                eastBackPanel.horizontalCoordinatesRect.xL = +180;
                eastBackPanel.horizontalCoordinatesRect.xR = +180 + westFacePanel.horizontalCoordinatesRect.xL;

                westBackPanel.horizontalCoordinatesRect.xL = -180 + eastFacePanel.horizontalCoordinatesRect.xR;
                westBackPanel.horizontalCoordinatesRect.xR = -180;
            }

        } else {
            // X軸の ±180°をまたいでいる場合
            eastFacePanel.horizontalCoordinatesRect.xL = horizontalCoordinatesRect.xL;
            eastFacePanel.horizontalCoordinatesRect.xR = +180;

            westFacePanel.horizontalCoordinatesRect.xL = -180;
            westFacePanel.horizontalCoordinatesRect.xR = horizontalCoordinatesRect.xR;

            eastBackPanel.horizontalCoordinatesRect.xL = +180 + westFacePanel.horizontalCoordinatesRect.xR;
            eastBackPanel.horizontalCoordinatesRect.xR = 0;

            westBackPanel.horizontalCoordinatesRect.xL = 0;
            westBackPanel.horizontalCoordinatesRect.xR = -180 + eastFacePanel.horizontalCoordinatesRect.xL;
        }

        // 背面のX軸
        if (horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR) {
            // X軸の ±180 をまたいでいない場合

        } else if (horizontalCoordinatesRect.xR < 0 || 0 <= horizontalCoordinatesRect.xL) {
            // X軸の ±180 も 0° も をまたいでいない場合

        } else {
            // X軸の境界線をまたいでいる場合
            eastBackPanel.horizontalCoordinatesRect.xL = -eastFacePanel.horizontalCoordinatesRect.xL;
            eastBackPanel.horizontalCoordinatesRect.xR = -eastFacePanel.horizontalCoordinatesRect.xR;

            westBackPanel.horizontalCoordinatesRect.xL = -westFacePanel.horizontalCoordinatesRect.xL;
            westBackPanel.horizontalCoordinatesRect.xR = -westFacePanel.horizontalCoordinatesRect.xR;
        }

        // 正面と背面のY軸
        if (horizontalCoordinatesRect.yT <= +90 && horizontalCoordinatesRect.yB >= -90) {
            // Y軸の +90°も -90°もまたいでいない場合
            if (eastFacePanel.horizontalCoordinatesRect.hasWidth()) {
                eastFacePanel.horizontalCoordinatesRect.yT = horizontalCoordinatesRect.yT;
                eastFacePanel.horizontalCoordinatesRect.yB = horizontalCoordinatesRect.yB;
            }
            if (westFacePanel.horizontalCoordinatesRect.hasWidth()) {
                westFacePanel.horizontalCoordinatesRect.yT = horizontalCoordinatesRect.yT;
                westFacePanel.horizontalCoordinatesRect.yB = horizontalCoordinatesRect.yB;
            }
            eastBackPanel.horizontalCoordinatesRect.setupZero();
            westBackPanel.horizontalCoordinatesRect.setupZero();

        } else if (horizontalCoordinatesRect.yT >= +90) {
            // Y軸の +90°をまたいでいる場合
            eastFacePanel.horizontalCoordinatesRect.yT = westFacePanel.horizontalCoordinatesRect.yT = +90;
            eastFacePanel.horizontalCoordinatesRect.yB = westFacePanel.horizontalCoordinatesRect.yB = +horizontalCoordinatesRect.yB;

            eastBackPanel.horizontalCoordinatesRect.yT = westBackPanel.horizontalCoordinatesRect.yT = +90 - (horizontalCoordinatesRect.yT - 90);
            eastBackPanel.horizontalCoordinatesRect.yB = westBackPanel.horizontalCoordinatesRect.yB = +90;

        } else {
            // Y軸の -90°をまたいでいる場合
            eastFacePanel.horizontalCoordinatesRect.yT = westFacePanel.horizontalCoordinatesRect.yT = +horizontalCoordinatesRect.yT;
            eastFacePanel.horizontalCoordinatesRect.yB = westFacePanel.horizontalCoordinatesRect.yB = -90;

            eastBackPanel.horizontalCoordinatesRect.yT = westBackPanel.horizontalCoordinatesRect.yT = -90;
            eastBackPanel.horizontalCoordinatesRect.yB = westBackPanel.horizontalCoordinatesRect.yB = -90 - (horizontalCoordinatesRect.yB + 90);
        }

    }

    /**
     * ディスプレイの座標をパネルに割り当てます
     */
    /* package */void assignPanelDisplayRect() {
        /* final */AstronomicalTheaterPanel panelFaceL, panelFaceR, panelBackL, panelBackR;
        if (horizontalCoordinatesRect.xL < horizontalCoordinatesRect.xR) {
            // X軸の ±180°をまたいでいない場合
            panelFaceL = westFacePanel;
            panelFaceR = eastFacePanel;

            if (horizontalCoordinatesRect.xR < 0 || 0 < horizontalCoordinatesRect.xL) {
                // 東側か西側を向いているとき
                panelBackL = eastBackPanel;
                panelBackR = westBackPanel;

            } else {
                // X軸の 0° をまたいでいる場合
                panelBackL = westBackPanel;
                panelBackR = eastBackPanel;
            }
        } else {
            // X軸の ±180°をまたいでいる場合
            panelFaceL = eastFacePanel;
            panelFaceR = westFacePanel;
            panelBackL = eastBackPanel;
            panelBackR = westBackPanel;
        }

        if (horizontalCoordinatesRect.yB < -90) {
            // Y軸の -90°もまたいでいない場合
            AstronomicalTheaterPanel temp = panelFaceL;
            panelFaceL = panelBackL;
            panelBackL = temp;

            temp = panelFaceR;
            panelFaceR = panelBackR;
            panelBackR = temp;
        }

        float consumeW = 0;
        if (panelFaceL.horizontalCoordinatesRect.hasRegion()) {
            consumeW = displayWidth * (panelFaceL.horizontalCoordinatesRect.width() / theaterWidth);
            panelFaceL.displayCoordinatesRect.xL = 0;
            panelFaceL.displayCoordinatesRect.xR = consumeW;
        }
        if (panelFaceR.horizontalCoordinatesRect.hasRegion()) {
            panelFaceR.displayCoordinatesRect.xL = consumeW;
            panelFaceR.displayCoordinatesRect.xR = displayWidth;
        }

        consumeW = 0;
        if (panelBackL.horizontalCoordinatesRect.hasRegion()) {
            consumeW = displayWidth * (panelBackL.horizontalCoordinatesRect.width() / theaterWidth);
            panelBackL.displayCoordinatesRect.xL = 0;
            panelBackL.displayCoordinatesRect.xR = consumeW;
        }
        if (panelBackR.horizontalCoordinatesRect.hasRegion()) {
            panelBackR.displayCoordinatesRect.xL = consumeW;
            panelBackR.displayCoordinatesRect.xR = displayWidth;
        }

        float consumeH = 0;
        boolean hasRangeL = panelBackL.horizontalCoordinatesRect.hasRegion();
        boolean hasRangeR = panelBackR.horizontalCoordinatesRect.hasRegion();
        if (hasRangeL || hasRangeR) {
            consumeH = displayHeight * (panelBackL.horizontalCoordinatesRect.height() / theaterHeight);
            if (hasRangeL) {
                panelBackL.displayCoordinatesRect.yT = 0;
                panelBackL.displayCoordinatesRect.yB = consumeH;
            } else {
                panelBackL.displayCoordinatesRect.setupZero();
            }
            if (hasRangeR) {
                panelBackR.displayCoordinatesRect.yT = 0;
                panelBackR.displayCoordinatesRect.yB = consumeH;
            } else {
                panelBackR.displayCoordinatesRect.setupZero();
            }
        } else {
            panelBackL.displayCoordinatesRect.setupZero();
            panelBackR.displayCoordinatesRect.setupZero();
        }

        hasRangeL = panelFaceL.horizontalCoordinatesRect.hasRegion();
        hasRangeR = panelFaceR.horizontalCoordinatesRect.hasRegion();
        if (hasRangeL && hasRangeR) {
            panelFaceL.displayCoordinatesRect.yT = panelFaceR.displayCoordinatesRect.yT = consumeH;
            panelFaceL.displayCoordinatesRect.yB = panelFaceR.displayCoordinatesRect.yB = displayHeight;
        } else if (hasRangeL) {
            panelFaceL.displayCoordinatesRect.yT = consumeH;
            panelFaceL.displayCoordinatesRect.yB = displayHeight;
            panelFaceR.displayCoordinatesRect.setupZero();
        } else if (hasRangeR) {
            panelFaceL.displayCoordinatesRect.setupZero();
            panelFaceR.displayCoordinatesRect.yT = consumeH;
            panelFaceR.displayCoordinatesRect.yB = displayHeight;
        } else {
            panelFaceL.displayCoordinatesRect.setupZero();
            panelFaceR.displayCoordinatesRect.setupZero();
        }
    }

    public void remapDisplayCoordinates(Iterable<Star> starIterable) {
        for (Star star : starIterable) {
            AstronomicalTheaterPanel panel = containsPanel(star);
            if (null == panel) {
                continue;
            }

            // 星にディスプレイ座標を割り当て
            panel.remapDisplayCoordinates(star);

            // 同じパネルを使って、関連する星座を構成する星にもディスプレイ座標を割り当て
            // TODO 同じパネルであってる？？
            for (Constellation constellation : star.getRelatedConstellations()) {
                for (Star componentStars : constellation.getComponentStars()) {
                    panel.remapDisplayCoordinates(componentStars);
                }
            }
        }
    }

    /**
     * 描画します.<br/>
     * 
     * @param canvas Androidキャンバス
     */
    public void draw(Canvas canvas) {
        canvas.drawPaint(backgroundPaint);
        for (AstronomicalTheaterPanel panel : panels) {
            panel.draw(canvas);
        }

        azimuthIndicator.draw(canvas, horizontalCoordinatesRect);  // 方位インジケータの描画
        altitudeIndicator.draw(canvas, horizontalCoordinatesRect); // 高度インジケータの描画
    }

    /**
     * 指定された星が天体シアターの地平座標の領域に含まれるかどうかを返却します.<br/>
     * 
     * @param star 星
     * @return 描画領域に含まれる場合はtrue
     */
    public AstronomicalTheaterPanel containsPanel(Star star) {
        for (AstronomicalTheaterPanel panel : panels) {
            if (panel.contains(star)) {
                return panel;
            }
        }
        return null;
    }

    /**
     * 指定された星をキャンバスへ描画します.<br/>
     * 
     * @param star 星
     * @param canvas キャンバス
     */
    public void draw(Canvas canvas, Star star) {
        // AstronomicalTheaterPanel panel = containsPanel(star);
        // if (null == panel) {
        // return;
        // }
        //
        // panel.remapDisplayCoordinates(star);
        if (star.isDisplayCoordinatesLocated()) {
            starRenderer.drawStar(canvas, star);
        }
    }

}
