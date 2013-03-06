package jp.gr.java_conf.dhun.starseeker.logic;

// http://yoko-gb.blogspot.jp/2012/10/android.html
// http://kamoland.com/wiki/wiki.cgi?TYPE_ORIENTATION%A4%F2%BB%C8%A4%EF%A4%BA%A4%CB%CA%FD%B0%CC%B3%D1%A4%F2%BC%E8%C6%C0
//
/**
 * 端末の方位計算機のインターフェース.<br/>
 * 
 * @author jun
 * 
 */
public interface IObservationSiteLocator {

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

    void setOnChangeSiteLocationListener(OnChangeSiteLocationListener listener);

    interface OnChangeSiteLocationListener {
        void onChangeSiteLocation(SiteLocation siteLocation);
    }

    class SiteLocation {
        public double azimuth; // 方位.
        public double pitch;   // ピッチ.
        public double roll;    // ロール.
    }
}