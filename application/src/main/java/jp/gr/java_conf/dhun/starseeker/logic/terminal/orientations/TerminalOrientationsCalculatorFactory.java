/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations;

import android.content.Context;
import android.view.Display;
import android.view.Surface;

/**
 * 端末方位計算機のファクトリ.<br/>
 * 
 * @author jun
 * 
 */
public class TerminalOrientationsCalculatorFactory {

    private TerminalOrientationsCalculatorFactory() {
    }

    /**
     * 
     * @param context Androidコンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値.
     * @return 端末方位計算機
     */
    public static final ITerminalOrientationsCalculator create(Context context, int displayRotation) {
        switch (displayRotation) {
        case Surface.ROTATION_0:
        case Surface.ROTATION_180:
            return new PortraitTerminalOrientationsCalculator(context, displayRotation);

        case Surface.ROTATION_90:
        case Surface.ROTATION_270:
            return new LandscapeTerminalOrientationsCalculator(context, displayRotation);

        default:
            throw new IllegalStateException("displayRotationの値が不正です. value=" + displayRotation);
        }
    }
}
