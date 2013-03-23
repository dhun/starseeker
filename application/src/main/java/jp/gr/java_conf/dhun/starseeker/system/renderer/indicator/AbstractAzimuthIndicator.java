package jp.gr.java_conf.dhun.starseeker.system.renderer.indicator;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * 方位インジケータの抽象実装
 * 
 * @author jun
 * 
 */
public class AbstractAzimuthIndicator {

    // サイズ系
    protected final int displayWidth;     // ディスプレイの横幅(pixel)
    protected final int displayHeight;    // ディスプレイの高さ(pixel)
    protected float theaterWidthAngle;    // 天体シアターの横幅(角度)

    // 描画系
    protected final Paint tickPaint;
    protected final Paint textPaint;

    /**
     * コンストラクタ.<br/>
     * 
     * @param displayWidth ディスプレイの横幅(pixel)
     * @param displayHeight ディスプレイの高さ(pixel)
     * @param theaterWidthAngle 天体シアターの横幅(角度)
     */
    public AbstractAzimuthIndicator(int displayWidth, int displayHeight, float theaterWidthAngle) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.theaterWidthAngle = theaterWidthAngle;

        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(Color.WHITE);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(12);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

}