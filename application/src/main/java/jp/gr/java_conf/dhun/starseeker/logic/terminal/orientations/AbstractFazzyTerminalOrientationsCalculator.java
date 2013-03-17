/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.terminal.orientations;

import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import android.content.Context;
import android.view.Display;

/**
 * @author jun
 * 
 */
public abstract class AbstractFazzyTerminalOrientationsCalculator extends AbstractTerminalOrientationsCalculator {

    private static final float DELTA_X = 8.0f;
    private static final float DELTA_Y = 4.0f;
    private static final float DELTA_Z = 2.0f;

    private final Orientations lastNoticeOrientations = new Orientations();

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     * @param displayRotation 端末の回転状態. {@link Display#getRotation()}の値
     */
    public AbstractFazzyTerminalOrientationsCalculator(Context context, int displayRotation) {
        super(context, displayRotation);
    }

    @Override
    protected void raiseChangeTerminalOrientations() {
        if (Math.abs(lastNoticeOrientations.azimuth - siteLocation.azimuth) > DELTA_X //
                || Math.abs(lastNoticeOrientations.pitch - siteLocation.pitch) > DELTA_Y //
        /* || Math.abs(lastNoticeOrientations.roll - siteLocation.roll) > DELTA_Z */) {
            lastNoticeOrientations.copyFrom(siteLocation);
            onChangeSiteLocationListener.onChangeTerminalOrientations(lastNoticeOrientations);
        }
    }
}
