package jp.gr.java_conf.dhun.starseeker.system.renderer.indicator;

import jp.gr.java_conf.dhun.starseeker.system.model.coordinates.CoordinatesRect;
import android.graphics.Canvas;

/**
 * 方位インジケータのインターフェース
 * 
 * @author jun
 * 
 */
public interface IAzimuthIndicator {

    /**
     * 天体シアターの横幅(角度)を設定します
     */
    void setTheaterWidthAngle(float theaterWidthAngle);

    /**
     * 描画します.<br/>
     * 
     * @param canvas Androidキャンバス
     * @param theaterHorizontalCoordinatesRect 天体シアターの地平座標
     */
    void draw(Canvas canvas, CoordinatesRect theaterHorizontalCoordinatesRect);

}