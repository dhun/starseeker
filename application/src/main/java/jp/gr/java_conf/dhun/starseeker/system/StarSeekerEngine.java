/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.system.listener.IStarSeekerListener;
import jp.gr.java_conf.dhun.starseeker.system.logic.StarManager;
import jp.gr.java_conf.dhun.starseeker.system.model.FpsCounter;
import jp.gr.java_conf.dhun.starseeker.system.model.panel.AstronomicalTheater;
import jp.gr.java_conf.dhun.starseeker.util.DateTimeUtils;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
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
    private final StarManager starManager;

    private final Orientations orientations;

    private final FpsCounter fpsCounter;

    private String locationTitle;

    /**
     * コンストラクタ
     */
    public StarSeekerEngine(Context context) {
        starManager = new StarManager(context);
        starManager.configure(2); // FIXME 初期値は暫定

        orientations = new Orientations();

        fpsCounter = new FpsCounter();
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

    /**
     * 観測地点の位置を設定します.<br/>
     * 
     * @param longitude 観測地点の経度
     * @param latitude 観測地点の緯度
     * @param baseCalendar 座標算出の基準日時となるカレンダー
     */
    public void configureObservationSiteLocation(double longitude, double latitude, Calendar baseCalendar) {
        locationTitle = String.format("経度=[%6.2f], 緯度=[%6.2f], 日時=[%s]", longitude, latitude, DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(baseCalendar));

        starManager.relocate(longitude, latitude, baseCalendar);
    }

    /**
     * 抽出する星の等級の下限値を設定します.<br/>
     * 
     * @param magnitude 等級
     */
    public void configureExtractLowerstarMagnitude(float magnitude) {
        starManager.extract(magnitude);
    }

    // ＞＞＞ 開発中のコード
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
        fpsCounter.start();

        try {
            astronomicalTheater.calculateCoordinatesRect(orientations.azimuth, orientations.pitch);

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
            for (Star star : starManager.iterate()) {
                astronomicalTheater.draw(canvas, star);
            }

            fpsCounter.finish();
            canvas.drawText(fpsCounter.getDisplayText(), 100, 100, paint);
            canvas.drawText("azimuth=" + decFormat.format(orientations.azimuth), 100, 120, paint);
            canvas.drawText("pitch=" + decFormat.format(orientations.pitch), 100, 130, paint);
            canvas.drawText("roll=" + decFormat.format(orientations.roll), 100, 140, paint);

            canvas.drawText(locationTitle, 100, 200, paint);

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
        this.orientations.copyFrom(orientations);
        // this.orientations.azimuth = -130; // FIXME コミットされていたら後で戻す
        // this.orientations.pitch = -80;
        // this.orientations.roll = 0;
    }
}
