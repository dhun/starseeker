/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.model.AstronomicalTheater;
import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import jp.gr.java_conf.dhun.starseeker.system.listener.IStarSeekerListener;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * スターシーカーシステムのエンジン.<br/>
 * 
 * @author jun
 * 
 */
public class StarSeekerEngine implements //
        ITerminalOrientationsCalculator.OnChangeTerminalOrientationsListener {

    // void main() {
    // /*
    // * n. 端末の方位と傾きを算出
    // * n. 端末の方位と傾きから、スクリーンの座標系を算出
    // * n. スクリーンの座標系から、描画対象となる星を抽出
    // * http://www.c3.club.kyutech.ac.jp/gamewiki/index.php?%C5%F6%A4%BF%A4%EA%C8%BD%C4%EA
    // *
    // * n. 描画対象となる星に対する星座を抽出
    // * n. 描画
    // */
    // }

    private IStarSeekerListener starSeekerListener; // スターシーカーシステムのリスナ

    private AstronomicalTheater astronomicalTheater;
    private Orientations orientations;

    private float lastFps;
    private long lastTime;
    private final NumberFormat fpsFormat = new DecimalFormat("'FPS='0.0");

    /**
     * デフォルト・コンストラクタ
     */
    public StarSeekerEngine() {
        orientations = new Orientations();
    }

    /**
     * エンジンを準備します.<br/>
     * 
     * @param displayWidth ディスプレイの横幅
     * @param displayHeight ディスプレイの高さ
     */
    public void prepare(int displayWidth, int displayHeight) {
        astronomicalTheater = new AstronomicalTheater(displayWidth, displayHeight);
    }

    // ＞＞＞ 開発中のコード
    private int count = 0;
    private final Paint paint = new Paint() {
        {
            setFlags(Paint.ANTI_ALIAS_FLAG);
            setColor(Color.WHITE);
        }
    };
    private final NumberFormat decFormat = new DecimalFormat("0.0") {
        {
            setPositivePrefix("+");
            setNegativePrefix("-");
        }
    };

    // ＜＜＜ 開発中のコード. ここまで

    /**
     * 演算処理を行います.<br/>
     */
    public void calculate() {
        long now = System.currentTimeMillis();
        lastFps = 1000 / (now - lastTime);
        lastTime = now;

        try {
            astronomicalTheater.calculateTheaterRect(orientations.azimuth, orientations.pitch);
            count++;

        } catch (Exception e) {
            LogUtils.e(getClass(), "演算処理で例外が発生しました.", e);
            if (starSeekerListener != null) {
                starSeekerListener.onException(e);
            }
        }
    }

    /**
     * 描画処理を行います.<br/>
     * 
     * @param canvas Androidキャンバス
     */
    public void draw(Canvas canvas) {
        try {
            astronomicalTheater.draw(canvas);

            canvas.drawText(fpsFormat.format(lastFps), 100, 100, paint);
            canvas.drawText("azimuth=" + decFormat.format(orientations.azimuth), 100, 120, paint);
            canvas.drawText("pitch=" + decFormat.format(orientations.pitch), 100, 130, paint);
            canvas.drawText("roll=" + decFormat.format(orientations.roll), 100, 140, paint);

        } catch (Exception e) {
            LogUtils.e(getClass(), "描画処理で例外が発生しました.", e);
            if (starSeekerListener != null) {
                starSeekerListener.onException(e);
            }
        }
    }

    /**
     * スターシーカーシステムのリスナを設定します.
     */
    public void setStarSeekerListener(IStarSeekerListener listener) {
        this.starSeekerListener = listener;
    }

    // ================================================================================
    // ITerminalOrientationsCalculator.OnChangeTerminalOrientationsListener
    @Override
    public void onChangeTerminalOrientations(Orientations orientations) {
        this.orientations = orientations;
    }
}
