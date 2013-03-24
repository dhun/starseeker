package jp.gr.java_conf.dhun.starseeker.system.renderer.indicator;

import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;
import android.graphics.Canvas;

/**
 * 高度インジケータのインターフェース
 * 
 * @author jun
 * 
 */
public interface IAltitudeIndicator {

    /**
     * 天体シアターの高さ(角度)を設定します
     */
    void setTheaterHeightAngle(float theaterHeightAngle);

    /**
     * 描画します.<br/>
     * 
     * @param canvas Androidキャンバス
     * @param theaterHorizontalCoordinatesRect 天体シアターの地平座標
     */
    void draw(Canvas canvas, CoordinatesRect theaterHorizontalCoordinatesRect);
}