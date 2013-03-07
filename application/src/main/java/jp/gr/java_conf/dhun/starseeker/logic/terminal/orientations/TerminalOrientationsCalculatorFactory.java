/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations;

import android.content.Context;
import android.view.Surface;

/**
 * @author jun
 * 
 */
public class TerminalOrientationsCalculatorFactory {

    private TerminalOrientationsCalculatorFactory() {
    }

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
