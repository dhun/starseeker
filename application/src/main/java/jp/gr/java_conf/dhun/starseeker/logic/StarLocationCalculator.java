/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.Calendar;
import java.util.Date;

import jp.gr.java_conf.dhun.starseeker.util.MathUtils;

/**
 * 星座標の計算機.<br/>
 * 
 * @author jun
 * 
 */
public class StarLocationCalculator {

    /** 座標計算の基準日時 */
    private final Date baseDateTime;

    /** 座標計算基準日時に対するＭＪＤ */
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

        return MathUtils.floor(365.25 * y) + MathUtils.floor(y / 400) - MathUtils.floor(y / 100) + MathUtils.floor(30.59 * (m - 2)) + d + 1721088.5 - 2400000.5;
    }

    /**
     * グリニッジ恒星時を算出します.<br/>
     * 
     * @return グリニッジ恒星時(h). 経度０°において、南中している星の赤経
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

    /**
     * 地方恒星時を算出します.<br/>
     * 
     * @param greenwichSiderealTime グリニッジ恒星時
     * @param longitude 経度. 経度、東経を - 西経を + とする. -180から+180
     * @return 地方恒星時(h). 経度λにおいて南中している星の赤経
     */
    public double calculateLocalSiderealTime(double greenwichSiderealTime, double longitude) {
        assert (-180 <= longitude && longitude <= +180);

        // θ = θG-λ = 18h 41.8m -(-(135+44/60)/15 ) = 18h 41.8m -(-9h 2.9m ) = 27h 44.7m
        // 27h 44.7m - 24h = 3h 44.7m
        double degree = MathUtils.floor(longitude);
        double minute = MathUtils.round((longitude - degree) * 100);

        final double DAYS_OF_HOUR = 24;
        double result = greenwichSiderealTime - (-(degree + minute / 60) / 15);
        result -= DAYS_OF_HOUR;
        return result;
    }

    /**
     * 時角を算出します.<br/>
     * 
     * @param localSiderealTime 地方恒星時(θ). 単位は(h)
     * @param rightAscension 赤経
     * @return 時角(H)
     */
    public double calculateHourAngle(double localSiderealTime, double rightAscension) {
        // H = θ-α = 3h 44.7m-6h45.1m = -3h 0.4m = -45.1°

        double diff = localSiderealTime - rightAscension;
        return 360 * (diff / 24);
    }

    /**
     * 赤道座標→地平座標の変換式(1).<br/>
     * = -cosδsinH
     * 
     * @param declination 赤緯(δ)
     * @param hourAngle 時角(H)
     * @return cosh sinA
     */
    public double convertEquatorialCoordinateToHorizontalCoordinate1(double declination, double hourAngle) {
        return -cos(declination) * sin(hourAngle);
    }

    /**
     * 赤道座標→地平座標の変換式(2).<br/>
     * = cosψsinδ-sinψcosδcosH
     * 
     * @param latitude 緯度(ψ)
     * @param declination 赤緯(δ)
     * @param hourAngle 時角(H)
     * @return cosh cosA
     */
    public double convertEquatorialCoordinateToHorizontalCoordinate2(double latitude, double declination, double hourAngle) {
        return cos(latitude) * sin(declination) - sin(latitude) * cos(declination) * cos(hourAngle);
    }

    /**
     * 赤道座標→地平座標の変換式(3).<br/>
     * = sinψsinδ+cosψcosδcosH
     * 
     * @param latitude 緯度(ψ)
     * @param declination 赤緯(δ)
     * @param hourAngle 時角(H)
     * @return sinh
     */
    public double convertEquatorialCoordinateToHorizontalCoordinate3(double latitude, double declination, double hourAngle) {
        return sin(latitude) * sin(declination) + cos(latitude) * cos(declination) * cos(hourAngle);
    }

    /**
     * 角度に対してsinを実行します.<br/>
     * 
     * @param angle 角度
     * @return sin
     */
    protected final double sin(double angle) {
        return Math.sin(angle * Math.PI / 180);
    }

    /**
     * 角度に対してcosを実行します.<br/>
     * 
     * @param angle 角度
     * @return cos
     */
    protected final double cos(double angle) {
        return Math.cos(angle * Math.PI / 180);
    }

    /**
     * 角度に対してtanを実行します.<br/>
     * 
     * @param angle 角度
     * @return tan
     */
    protected final double tan(double angle) {
        return Math.tan(angle * Math.PI / 180);
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
