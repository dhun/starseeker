/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.Calendar;
import java.util.Date;

/**
 * @author jun
 * 
 */
public class StarLocationCalculator {

    /** 座標算出の基準日時 */
    private final Date baseDateTime;

    /** 座標算出基準日時に対するＭＪＤ */
    private final double mjd;

    /**
     * コンストラクタ.<br/>
     * システム日時が座標算出の基準日時になります.<br/>
     */
    public StarLocationCalculator() {
        this(new Date(System.currentTimeMillis()));
    }

    /**
     * コンストラクタ.<br/>
     * 指定された日時が座標算出の基準日時になります.<br/>
     * 
     * @param baseDateTime 座標算出の基準日時
     */
    public StarLocationCalculator(Date baseDateTime) {
        this.baseDateTime = baseDateTime;
        this.mjd = calculateMJD(baseDateTime);
    }

    /**
     * 指定された日時に対するMJDを算出します.<br/>
     * 
     * @see http://star.gs/nyumon/koseiji.htm
     * @param baseDateTime MJDを算出対象とする日時
     * @return MJD
     * 
     * @see http://star.gs/nyumon/koseiji.htm
     */
    public double calculateMJD(Date baseDateTime) {
        // グレゴリオ暦（1582年10月15日以降）の西暦年をY、月をM、日をDとする。
        // ただし1月のはM=13、2月はM=14、YはY=Y-1とする。
        // MJD=365.25Y+Y/400-Y/100+30.59(M-2)+D+1721088.5-2400000.5

        // site2.
        // http://t-sakachan.net/omosirozatugaku/yuriusu2.htm
        // MJD＝int[365.25 * y] + int[y / 400] - int[y / 100]
        // + int[30.59 * (m - 2)] + d - 678912

        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDateTime);
        double y = cal.get(Calendar.YEAR);
        double m = cal.get(Calendar.MONTH);
        double d = cal.get(Calendar.DAY_OF_MONTH);

        y += (m <= 1) ? -1 : 0;
        m += (m <= 1) ? 13 : 1;
        d += cal.get(Calendar.HOUR_OF_DAY) / 24d;
        d += cal.get(Calendar.MINUTE) / 24d / 60;
        d += cal.get(Calendar.SECOND) / 24d / 60 / 60;
        // d += cal.get(Calendar.MILLISECOND) / 24d / 60 / 60 / 1000;

        return extractInteger(365.25 * y) + extractInteger(y / 400) - extractInteger(y / 100) + extractInteger(30.59 * (m - 2)) + d + 1721088.5 - 2400000.5;
    }

    private double extractInteger(double value) {
        return Math.floor(value);
    }

    /**
     * グリニッジ恒星時を算出します.<br/>
     * 
     * @return グリニッジ恒星時
     */
    public double calculateGreenwichSiderealTime() {
        // MJD = 51544.50
        // θG = 24hx(0.67239＋1.00273781x(MJD-40000.0))　2000.0分点に準拠
        // θG : グリニッジ恒星時。(0.67239＋1.00273781x(MJD-40000.0))の値から小数点以下のみ
        // 18.69690h = 18h 41.8m

        double tmpAnswer = (0.67239 + 1.00273781 * (mjd - 40000.0));
        double ofDecimal = tmpAnswer % 1;
        double ofHour = 24 * ofDecimal;
        return ofHour;
    }

    // ************************************************************************************************************************
    // setter, getter
    // ************************************************************************************************************************
    public Date getBaseDate() {
        return baseDateTime;
    }

    public double getMjd() {
        return mjd;
    }
}
