package jp.gr.java_conf.dhun.starseeker.logic;

import jp.gr.java_conf.dhun.starseeker.model.Orientations;
import android.view.Display;

// http://yoko-gb.blogspot.jp/2012/10/android.html
// http://kamoland.com/wiki/wiki.cgi?TYPE_ORIENTATION%A4%F2%BB%C8%A4%EF%A4%BA%A4%CB%CA%FD%B0%CC%B3%D1%A4%F2%BC%E8%C6%C0
//
/**
 * 端末方位計算機のインターフェース.<br/>
 * 
 * @author jun
 * 
 */
public interface ITerminalOrientationsCalculator {

    /**
     * センサーマネージャにリスナを登録します.<br/>
     * Activity.onResumeなどで呼び出してください
     */
    void registerSensorListeners();

    /**
     * センサーマネージャからリスナを削除します.<br/>
     * Activity.onPauseなどで呼び出してください
     */
    void unregisterSensorListeners();

    /**
     * 端末の回転状況を設定します.<br/>
     * 
     * @param {@link {@link Display#getRotation()}の値
     */
    void setTerminalRotation(int displayRotation);

    /**
     * 端末方位リスナを設定します.<br/>
     */
    void setOnChangeTerminalOrientationsListener(OnChangeTerminalOrientationsListener listener);

    /**
     * 端末方位リスナ
     * 
     */
    interface OnChangeTerminalOrientationsListener {
        void onChangeTerminalOrientations(Orientations siteLocation);
    }
}