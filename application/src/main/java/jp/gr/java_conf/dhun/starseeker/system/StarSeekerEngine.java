/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import jp.gr.java_conf.dhun.starseeker.system.listener.IStarSeekerListener;
import jp.gr.java_conf.dhun.starseeker.system.logic.StarManager;
import jp.gr.java_conf.dhun.starseeker.system.logic.terminal.orientations.ITerminalOrientationsCalculator;
import jp.gr.java_conf.dhun.starseeker.system.model.FpsCounter;
import jp.gr.java_conf.dhun.starseeker.system.model.Orientations;
import jp.gr.java_conf.dhun.starseeker.system.model.panel.AstronomicalTheater;
import jp.gr.java_conf.dhun.starseeker.system.model.star.Constellation;
import jp.gr.java_conf.dhun.starseeker.system.model.star.ConstellationPath;
import jp.gr.java_conf.dhun.starseeker.system.model.star.Star;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget.SeekTargetType;
import jp.gr.java_conf.dhun.starseeker.util.DateTimeUtils;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * スターシーカーシステムのエンジン.<br/>
 * 次の要素で構成しています.<br/>
 * <ul>
 * <li>{@link AstronomicalTheater}</li>
 * <li>{@link StarManager}</li>
 * </ul>
 * 
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

    private boolean enabled;

    private SeekTargetType seekTargetType;
    private Integer seekTargetStarHipNumber;
    private String seekTargetConsCode;

    /**
     * コンストラクタ
     */
    public StarSeekerEngine(Context context) {
        starManager = new StarManager(context);
        starManager.initialize();

        orientations = new Orientations();

        fpsCounter = new FpsCounter();

        enabled = false;
    }

    /**
     * ディスプレイサイズを設定します.<br/>
     * 
     * @param displayWidth ディスプレイの横幅
     * @param displayHeight ディスプレイの高さ
     */
    public void setDisplaySize(int displayWidth, int displayHeight) {
        astronomicalTheater = new AstronomicalTheater(displayWidth, displayHeight);
    }

    /**
     * 観測条件を設定します.<br/>
     * 
     * @param longitude 観測地点の経度
     * @param latitude 観測地点の緯度
     * @param baseCalendar 座標算出の基準日時となるカレンダー
     */
    public void setObservationCondition(ObservationSiteLocation location, Calendar baseCalendar) {
        locationTitle = String.format("経度=[%6.2f], 緯度=[%6.2f], 日時=[%s]" //
                , location.getLongitude()   //
                , location.getLatitude()    //
                // , location.getAltitude() // XXX 高度は使ってない
                , DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(baseCalendar));

        starManager.setObservationLocation(location.getLongitude(), location.getLatitude());
        starManager.setObservationDatetime(baseCalendar);
    }

    /**
     * 抽出する等級の上限値を設定します.
     * 
     * @param magnitude 抽出する等級の上限
     */
    public void setExtractUpperStarMagnitude(float magnitude) {
        starManager.setExtractUpperStarMagnitude(magnitude);
    }

    public void setSeekTarget(SeekTarget<?> seekTarget) {
        this.seekTargetType = null;
        this.seekTargetStarHipNumber = null;
        this.seekTargetConsCode = null;

        if (null == seekTarget) {
            return;

        } else {
            this.seekTargetType = seekTarget.getSeekTargetType();

            if (seekTargetType == SeekTargetType.STAR) {
                this.seekTargetStarHipNumber = Integer.valueOf(seekTarget.getSeekTargetId());
            } else {
                this.seekTargetConsCode = seekTarget.getSeekTargetId();
            }
        }
    }

    /**
     * 星を抽出します.
     */
    public void extract() {
        starManager.extract();
    }

    /**
     * 星を配置します.
     */
    public void locate() {
        starManager.locate();
    }

    public void prepare() {
        starManager.prepare();  // FIXME 呼び出し先のFIXMEを除去
    }

    // /**
    // * 観測条件を設定します.<br/>
    // *
    // * @param longitude 観測地点の経度
    // * @param latitude 観測地点の緯度
    // * @param baseCalendar 座標算出の基準日時となるカレンダー
    // */
    // @Deprecated
    // public void configureObservationCondition(double longitude, double latitude, Calendar baseCalendar) {
    // locationTitle = String.format("経度=[%6.2f], 緯度=[%6.2f], 日時=[%s]", longitude, latitude, DateTimeUtils.toLocalYYYYMMDDHHMMSSWithSegment(baseCalendar));
    //
    // starManager.setObservationLocation(longitude, latitude);
    // starManager.setObservationDatetime(baseCalendar);
    // if (enabled) {
    // starManager.prepare();
    // }
    // }

    // /**
    // * 抽出する星の等級の上限値を設定します.<br/>
    // *
    // * @param magnitude 等級
    // */
    // @Deprecated
    // public void configureExtractUpperStarMagnitude(float magnitude) {
    // starManager.setExtractUpperStarMagnitude(magnitude);
    // if (enabled) {
    // starManager.prepare();
    // }
    // }

    public void resume() {
        enabled = true;
        starManager.prepare();
    }

    public void pause() {
        enabled = false;
    }

    // ＞＞＞ 開発中のコード
    private final NumberFormat decFormat = new DecimalFormat("0.0") {
        {
            setPositivePrefix("+");
            setNegativePrefix("-");
        }
    };

    private final Paint normalStarPaint = new Paint() {
        {
            setFlags(Paint.ANTI_ALIAS_FLAG);
            setColor(Color.WHITE);
        }
    };
    private final Paint normalPathPaint = new Paint() {
        {
            setFlags(Paint.ANTI_ALIAS_FLAG);
            setColor(Color.WHITE);
        }
    };

    private final Paint targetStarPaint = new Paint() {
        {
            setFlags(Paint.ANTI_ALIAS_FLAG);
            setColor(Color.GREEN);
        }
    };
    private final Paint targetPathPaint = new Paint() {
        {
            setFlags(Paint.ANTI_ALIAS_FLAG);
            setColor(Color.GREEN);
        }
    };

    // ＜＜＜ 開発中のコード. ここまで

    /**
     * 演算処理を行います.<br/>
     */
    public void calculate() {
        if (null == astronomicalTheater) {  // FIXME システムエラーを無理やり抑制. 本当はノイズ
            return;
        }

        fpsCounter.start();

        try {
            astronomicalTheater.calculateCoordinatesRect(orientations.azimuth, orientations.pitch);

            for (Star star : starManager.provideTargetStars()) {
                star.resetDisplayCoordinatesLocated();
            }
            astronomicalTheater.remapDisplayCoordinates(starManager.provideTargetStars());

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
        if (null == astronomicalTheater) {  // FIXME システムエラーを無理やり抑制. 本当はノイズ
            return;
        }

        try {
            astronomicalTheater.draw(canvas);

            astronomicalTheater.setSeekTargetHipNumber(seekTargetStarHipNumber);
            for (Star star : starManager.provideTargetStars()) {
                astronomicalTheater.draw(canvas, star);
            }

            // TODO 星座の座標補正が必要なので、このままではダメ
            for (Constellation constellation : starManager.provideTargetConstellations()) {
                Paint pathPaint = normalPathPaint;
                if (seekTargetConsCode != null && seekTargetConsCode.equals(constellation.getConstellationCode())) {
                    pathPaint = targetPathPaint;
                }

                for (ConstellationPath path : constellation.getConstellationPaths()) {
                    Star fmStar = path.getFromStar();
                    Star toStar = path.getToStar();
                    if (fmStar.isDisplayCoordinatesLocated() && toStar.isDisplayCoordinatesLocated()) {
                        canvas.drawLine(fmStar.getDisplayX(), fmStar.getDisplayY(), toStar.getDisplayX(), toStar.getDisplayY(), pathPaint);
                    }
                }
            }

            fpsCounter.finish();
            canvas.drawText(fpsCounter.getDisplayText(), 100, 100, normalStarPaint);
            canvas.drawText("azimuth=" + decFormat.format(orientations.azimuth), 100, 120, normalStarPaint);
            canvas.drawText("pitch=" + decFormat.format(orientations.pitch), 100, 130, normalStarPaint);
            canvas.drawText("roll=" + decFormat.format(orientations.roll), 100, 140, normalStarPaint);

            canvas.drawText(locationTitle, 100, 200, normalStarPaint);

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
