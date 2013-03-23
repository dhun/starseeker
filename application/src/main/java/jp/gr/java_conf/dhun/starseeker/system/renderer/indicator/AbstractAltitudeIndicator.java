package jp.gr.java_conf.dhun.starseeker.system.renderer.indicator;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * 高度インジケータの抽象実装
 * 
 * @author jun
 * 
 */
public abstract class AbstractAltitudeIndicator implements IAltitudeIndicator {

    // サイズ系
    protected final int displayWidth;     // ディスプレイの横幅(pixel)
    protected final int displayHeight;    // ディスプレイの高さ(pixel)
    protected float theaterHeightAngle;   // 天体シアターの高さ(角度)

    // 描画系
    protected final Paint tickPaint;
    protected final Paint textPaint;

    /**
     * コンストラクタ.<br/>
     * 
     * @param displayWidth ディスプレイの横幅(pixel)
     * @param displayHeight ディスプレイの高さ(pixel)
     */
    public AbstractAltitudeIndicator(int displayWidth, int displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(Color.WHITE);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(12);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTheaterHeightAngle(float theaterHeightAngle) {
        this.theaterHeightAngle = theaterHeightAngle;
    }
}