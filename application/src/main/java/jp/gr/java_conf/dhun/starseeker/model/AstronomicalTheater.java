/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

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

    private static final int FACE_EAST_PANEL = 0; // X=-180～0, Y=0～+90にあたるパネル
    private static final int FACE_WEST_PANEL = 1; // X=+180～0, Y=0～+90にあたるパネル
    private static final int BACK_EAST_PANEL = 2; // X=-180～0, Y=+90より背面にあたるパネル
    private static final int BACK_WEST_PANEL = 3; // X=+180～0, Y=+90より背面にあたるパネル

    private final AstronomicalTheaterPanel[] panels = new AstronomicalTheaterPanel[4];

    private final int displayWidth;                    // ディスプレイの横幅
    private final int displayHeight;                   // ディスプレイの高さ
    private final Rect theaterRect = new Rect();       // 天体シアターの座標

    private float theaterWidth;  // 天体シアターの横幅
    private float theaterHeight; // 天体シアターの高さ

    /**
     * コンストラクタ.<br/>
     * 
     * @param displayWidth ディスプレイの横幅
     * @param displayHeight ディスプレイの高さ
     */
    public AstronomicalTheater(int displayWidth, int displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        for (int i = 0; i < panels.length; i++) {
            panels[i] = new AstronomicalTheaterPanel();
        }

        setTheaterSizeToDefault();
    }

    /**
     * 天体シアターのサイズを、ディスプレイサイズに応じた規定値に設定します
     */
    public void setTheaterSizeToDefault() {
        // 端末の回転状態から、天体シアターのサイズを算出
        float theaterWidth, theaterHeight;
        if (displayWidth < displayHeight) {
            theaterWidth = DEFAULT_PORTRAIT_THEATER_WIDTH;
            theaterHeight = DEFAULT_PORTRAIT_THEATER_HEIGHT;
        } else {
            theaterWidth = DEFAULT_LANDSCAPE_THEATER_WIDTH;
            theaterHeight = DEFAULT_LANDSCAPE_THEATER_HEIGHT;
        }
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
    }

    /**
     * 端末の方位とピッチから、天体シアターの座標を算出します
     * 
     * @param terminalAzimuth 端末の方位
     * @param terminalPitch 端末のピッチ
     */
    public void calculateTheaterRect(float terminalAzimuth, float terminalPitch) {
        // 端末の方位とピッチから、天体シアターの座標を算出
        theaterRect.xL = adjustAzimuth(terminalAzimuth - theaterWidth / 2);
        theaterRect.xR = adjustAzimuth(theaterRect.xL + theaterWidth);

        theaterRect.yT = terminalPitch - theaterHeight / 2;
        theaterRect.yB = theaterRect.yT + theaterHeight;

        assignPanelTheaterRect();
        assignPanelDisplayRect();
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
        if (theaterRect.xL < theaterRect.xR) {
            // X軸の ±180°をまたいでいない場合
            panels[FACE_EAST_PANEL].theaterRect.xL = Math.max(theaterRect.xL, 0);
            panels[FACE_EAST_PANEL].theaterRect.xR = Math.max(theaterRect.xR, 0);

            panels[FACE_WEST_PANEL].theaterRect.xL = Math.min(theaterRect.xL, 0);
            panels[FACE_WEST_PANEL].theaterRect.xR = Math.min(theaterRect.xR, 0);

        } else {
            // X軸の ±180°をまたいでいる場合
            panels[FACE_EAST_PANEL].theaterRect.xL = theaterRect.xL;
            panels[FACE_EAST_PANEL].theaterRect.xR = +180;

            panels[FACE_WEST_PANEL].theaterRect.xL = -180;
            panels[FACE_WEST_PANEL].theaterRect.xR = theaterRect.xR;
        }

        if (theaterRect.yT >= -90) {
            // Y軸の －90°をまたいでいない場合
            if (panels[FACE_EAST_PANEL].theaterRect.hasWidth()) {
                panels[FACE_EAST_PANEL].theaterRect.yT = theaterRect.yT;
                panels[FACE_EAST_PANEL].theaterRect.yB = theaterRect.yB;
            }
            if (panels[FACE_WEST_PANEL].theaterRect.hasWidth()) {
                panels[FACE_WEST_PANEL].theaterRect.yT = theaterRect.yT;
                panels[FACE_WEST_PANEL].theaterRect.yB = theaterRect.yB;
            }
            panels[BACK_EAST_PANEL].theaterRect.setupZero();
            panels[BACK_WEST_PANEL].theaterRect.setupZero();

        } else {
            // Y軸の －90°をまたいでいる場合
            panels[FACE_EAST_PANEL].theaterRect.yT = -90;
            panels[FACE_EAST_PANEL].theaterRect.yB = theaterRect.yB;

            panels[FACE_WEST_PANEL].theaterRect.yT = -90;
            panels[FACE_WEST_PANEL].theaterRect.yB = theaterRect.yB;

            panels[BACK_EAST_PANEL].theaterRect.yT = theaterRect.yT;
            panels[BACK_EAST_PANEL].theaterRect.yB = -90;
            panels[BACK_EAST_PANEL].theaterRect.xL = panels[FACE_EAST_PANEL].theaterRect.xL;
            panels[BACK_EAST_PANEL].theaterRect.xR = panels[FACE_EAST_PANEL].theaterRect.xR;

            panels[BACK_WEST_PANEL].theaterRect.yT = theaterRect.yT;
            panels[BACK_WEST_PANEL].theaterRect.yB = -90;
            panels[BACK_WEST_PANEL].theaterRect.xL = panels[FACE_WEST_PANEL].theaterRect.xL;
            panels[BACK_WEST_PANEL].theaterRect.xR = panels[FACE_WEST_PANEL].theaterRect.xR;
        }
    }

    /**
     * ディスプレイの座標をパネルに割り当てます
     */
    /* package */void assignPanelDisplayRect() {
        final int indexFaceL, indexFaceR, indexBackL, indexBackR;
        if (theaterRect.xL < theaterRect.xR) {
            // X軸の ±180°をまたいでいない場合
            indexFaceL = FACE_WEST_PANEL;
            indexFaceR = FACE_EAST_PANEL;
            indexBackL = BACK_WEST_PANEL;
            indexBackR = BACK_EAST_PANEL;
        } else {
            // X軸の ±180°をまたいでいる場合
            indexFaceL = FACE_EAST_PANEL;
            indexFaceR = FACE_WEST_PANEL;
            indexBackL = BACK_EAST_PANEL;
            indexBackR = BACK_WEST_PANEL;
        }

        float consumeW = 0;
        if (panels[indexFaceL].theaterRect.hasRange()) {
            consumeW = displayWidth * (panels[indexFaceL].theaterRect.width() / theaterWidth);
            panels[indexFaceL].displayRect.xL = 0;
            panels[indexFaceL].displayRect.xR = consumeW;
            panels[indexBackL].displayRect.xL = 0;
            panels[indexBackL].displayRect.xR = consumeW;
        }
        if (panels[indexFaceR].theaterRect.hasRange()) {
            panels[indexFaceR].displayRect.xL = consumeW;
            panels[indexFaceR].displayRect.xR = displayWidth;
            panels[indexBackR].displayRect.xL = consumeW;
            panels[indexBackR].displayRect.xR = displayWidth;
        }

        float consumeH = 0;
        boolean hasRangeL = panels[indexBackL].theaterRect.hasRange();
        boolean hasRangeR = panels[indexBackR].theaterRect.hasRange();
        if (hasRangeL || hasRangeR) {
            consumeH = displayHeight * (panels[indexBackL].theaterRect.height() / theaterHeight);
            if (hasRangeL) {
                panels[indexBackL].displayRect.yT = 0;
                panels[indexBackL].displayRect.yB = consumeH;
            } else {
                panels[indexBackL].displayRect.setupZero();
            }
            if (hasRangeR) {
                panels[indexBackR].displayRect.yT = 0;
                panels[indexBackR].displayRect.yB = consumeH;
            } else {
                panels[indexBackR].displayRect.setupZero();
            }
        } else {
            panels[indexBackL].displayRect.setupZero();
            panels[indexBackR].displayRect.setupZero();
        }

        hasRangeL = panels[indexFaceL].theaterRect.hasRange();
        hasRangeR = panels[indexFaceR].theaterRect.hasRange();
        if (hasRangeL && hasRangeR) {
            panels[indexFaceL].displayRect.yT = consumeH;
            panels[indexFaceL].displayRect.yB = displayHeight;
            panels[indexFaceR].displayRect.yT = consumeH;
            panels[indexFaceR].displayRect.yB = displayHeight;
        } else if (hasRangeL) {
            panels[indexFaceL].displayRect.yT = consumeH;
            panels[indexFaceL].displayRect.yB = displayHeight;
            panels[indexFaceR].displayRect.setupZero();
        } else if (hasRangeR) {
            panels[indexFaceL].displayRect.setupZero();
            panels[indexFaceR].displayRect.yT = consumeH;
            panels[indexFaceR].displayRect.yB = displayHeight;
        } else {
            panels[indexFaceL].displayRect.setupZero();
            panels[indexFaceR].displayRect.setupZero();
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
        if (starAzimuth < theaterRect.xL || theaterRect.xR < starAzimuth) {
            return false;
        }
        if (starAltitude < theaterRect.yT || theaterRect.yB < starAltitude) {
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

        public boolean hasWidth() {
            return xL != 0 || xR != 0;
        }

        public boolean hasHeight() {
            return xL != 0 || xR != 0;
        }

        public float width() {
            return Math.abs((xL - xR));
        }

        public float height() {
            return Math.abs((yT - yB));
        }
    }

}
