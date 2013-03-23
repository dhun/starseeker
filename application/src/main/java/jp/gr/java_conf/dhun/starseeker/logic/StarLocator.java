/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import jp.gr.java_conf.dhun.starseeker.util.MathUtils;

/**
 * 星の座標配置クラス.<br/>
 * 観測地点の精度はdouble、星の座標系の精度はfloatのため、floatとdoubleが混在している.<br/>
 * Math系メソッドのシグネチャがdoubleなのでキャストを最小限に抑える効果もある(ハズ)
 * 
 * @author jun
 * 
 */
public class StarLocator {

    // 観測地点の座標
    private final double longitude; // 経度(λ). 東経を - 西経を + とする. -180から+180
    private final double latitude;  // 緯度(ψ). 北緯を + 南緯を - とする.. +90から -90

    // 座標計算基準日時
    private final Date baseDateTime;

    // 座標計算基準日時における観測地点の地方恒星時(θ)
    private final double localSiderealTime;

    /**
     * コンストラクタ.<br/>
     * 座標算出の基準日時はシステム日時になります.<br/>
     * 
     * @param longitude 観測地点の経度(λ)
     * @param latitude 観測地点の緯度(ψ)
     */
    public StarLocator(double longitude, double latitude) {
        this(longitude, latitude, new Date(System.currentTimeMillis()));
    }

    /**
     * コンストラクタ.<br/>
     * 座標算出の基準日時は指定された日時になります.<br/>
     * 
     * @param longitude 観測地点の経度(λ)
     * @param latitude 観測地点の緯度(ψ)
     * @param baseDateTime 座標算出の基準日時
     */
    public StarLocator(double longitude, double latitude, Date baseDateTime) {
        assert (-180 <= longitude && longitude <= +180);
        assert (-90 <= latitude && latitude <= +90);
        assert (null != baseDateTime);

        this.longitude = longitude;
        this.latitude = latitude;
        this.baseDateTime = baseDateTime;

        // MJD
        double mjd = calculateMJD(baseDateTime);

        // グリニッジ恒星時(θG)
        double greenwichSiderealTime = calculateGreenwichSiderealTime(mjd);

        // 地方恒星時(θ)
        localSiderealTime = calculateLocalSiderealTime(greenwichSiderealTime);
    }

    /**
     * 星を配置します.<br/>
     * 
     * @param star 星
     */
    public void locate(Star star) {
        // 時角
        double hourAngle = calculateHourAngle(localSiderealTime, star.getRightAscension());

        // 赤道座標→地平座標の変換式３兄弟
        double convertValue1 = convertEquatorialCoordinateToHorizontalCoordinate1(star.getDeclination(), hourAngle);
        double convertValue2 = convertEquatorialCoordinateToHorizontalCoordinate2(star.getDeclination(), hourAngle);
        double convertValue3 = convertEquatorialCoordinateToHorizontalCoordinate3(star.getDeclination(), hourAngle);

        // 方位(A)：北から東回り
        double azimuth = calculateAzimuth(convertValue1, convertValue2);

        // 高度(h)
        double altitude = calculateAltitude(convertValue2, convertValue3, azimuth);

        // 星を再配置
        star.relocate((float) azimuth, (float) altitude);

        LogUtils.v(getClass(), star.toString());
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
    protected double calculateMJD(Date baseDateTime) {
        // グレゴリオ暦（1582年10月15日以降）の西暦年をY、月をM、日をDとする。
        // ただし1月のはM=13、2月はM=14、YはY=Y-1とする。
        // MJD=365.25Y+Y/400-Y/100+30.59(M-2)+D+1721088.5-2400000.5

        // site2.
        // http://t-sakachan.net/omosirozatugaku/yuriusu2.htm
        // MJD＝int[365.25 * y] + int[y / 400] - int[y / 100]
        // + int[30.59 * (m - 2)] + d - 678912

        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDateTime);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
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
     * @param mjd MJD
     * @return グリニッジ恒星時(θG). 経度０°において、南中している星の赤経
     */
    protected double calculateGreenwichSiderealTime(double mjd) {
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
     * @param greenwichSiderealTime グリニッジ恒星時(θG)
     * @return 地方恒星時(θ). 経度λにおいて南中している星の赤経(α)
     */
    protected double calculateLocalSiderealTime(double greenwichSiderealTime) {
        // θ = θG-λ = 18h 41.8m -(-(135+44/60)/15 ) = 18h 41.8m -(-9h 2.9m ) = 27h 44.7m
        // 27h 44.7m - 24h = 3h 44.7m
        final double DAYS_OF_HOUR = 24;
        double result = greenwichSiderealTime - (-longitude / 15);

        // return result - DAYS_OF_HOUR; // TODO サイトの例題では常に24を引いてるけど、違う気がしたので訂正してみた
        if (result > +DAYS_OF_HOUR) {
            return result - DAYS_OF_HOUR;
        }
        if (result < -DAYS_OF_HOUR) {
            return result + DAYS_OF_HOUR;
        }
        return result;
    }

    /**
     * 時角を算出します.<br/>
     * 
     * @param localSiderealTime 地方恒星時(θ)
     * @param rightAscension 赤経(α)
     * @return 時角(H)
     */
    protected double calculateHourAngle(double localSiderealTime, double rightAscension) {
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
    protected double convertEquatorialCoordinateToHorizontalCoordinate1(double declination, double hourAngle) {
        return -cos(declination) * sin(hourAngle);
    }

    /**
     * 赤道座標→地平座標の変換式(2).<br/>
     * = cosψsinδ-sinψcosδcosH
     * 
     * @param declination 赤緯(δ)
     * @param hourAngle 時角(H)
     * @return cosh cosA
     */
    protected double convertEquatorialCoordinateToHorizontalCoordinate2(double declination, double hourAngle) {
        return cos(latitude) * sin(declination) - sin(latitude) * cos(declination) * cos(hourAngle);
    }

    /**
     * 赤道座標→地平座標の変換式(3).<br/>
     * = sinψsinδ+cosψcosδcosH
     * 
     * @param declination 赤緯(δ)
     * @param hourAngle 時角(H)
     * @return sinh
     */
    protected double convertEquatorialCoordinateToHorizontalCoordinate3(double declination, double hourAngle) {
        return sin(latitude) * sin(declination) + cos(latitude) * cos(declination) * cos(hourAngle);
    }

    /**
     * 方位を算出します.<br/>
     * 
     * @param convertValue1 変換式(1)の値. cosh x sinA
     * @param convertValue2 変換式(2)の値. cosh x cosA
     * @return 方位(A)
     */
    protected double calculateAzimuth(double convertValue1, double convertValue2) {
        // TODO サイトに具体的な数式が書いてなかったので、三角関数のサイトと照らし合わせて想像で書いた
        // http://topicmaps.u-gakugei.ac.jp/phys/matsuura/lecture/dyna/contents/triangle/triangle.asp
        // http://mysteryart.web.fc2.com/library/calsmpl/clcord.html

        // final double angle = atan(convertValue1 / convertValue2);
        // final double result;
        // if (convertValue1 >= 0) {
        // if (convertValue2 >= 0) {
        // result = +angle; // 第１象限
        // } else {
        // result = 90 + (90 + angle); // 第２象限
        // }
        // } else {
        // if (convertValue2 < 0) {
        // result = 180 + angle; // 第３象限
        // } else {
        // result = 270 + (90 + angle); // 第４象限
        // }
        // }
        // return result;

        final double atan = Math.atan(convertValue1 / convertValue2);
        final double result;
        if (convertValue1 >= 0) {
            if (convertValue2 >= 0) {
                result = Math.toDegrees(atan);           // 第１象限
            } else {
                result = Math.toDegrees(atan + Math.PI); // 第２象限
            }
        } else {
            // 0 ... ±180ならこっち
            if (convertValue2 < 0) {
                result = Math.toDegrees(atan - Math.PI); // 第３象限
            } else {
                result = Math.toDegrees(atan);           // 第４象限
            }

            // 0 ... ＋360ならこっち
            // if (convertValue2 < 0) {
            // result = 360 + Math.toDegrees(atan - Math.PI); // 第３象限
            // } else {
            // result = 360 + Math.toDegrees(atan); // 第４象限
            // }
        }

        return result;
    }

    /**
     * 高度を算出します.<br/>
     * 
     * @param convertValue2 変換式(2)の値. cosh x cosA
     * @param convertValue3 変換式(3)の値. sinh
     * @param azimuth 方位(A)
     * @return 高度(h)
     */
    protected double calculateAltitude(double convertValue2, double convertValue3, double azimuth) {
        double angle = atan(convertValue3 / (convertValue2 / cos(azimuth)));
        return angle;
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

    /**
     * ラジアンに対してatanを実行して、その角度を返却します.<br/>
     * 
     * @param radian ラジアン
     * @return 角度
     */
    protected final double atan(double radian) {
        return Math.atan(radian) * 180 / Math.PI;
    }

    // ************************************************************************************************************************
    // setter, getter
    // ************************************************************************************************************************
    /**
     * 観測地点の経度を取得します.<br/>
     * 
     * @return 経度(λ). 東経を - 西経を + とする. -180から+180
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * 観測地点の緯度を取得します.<br/>
     * 
     * @return 緯度(ψ). 北緯を + 南緯を - とする.. +90から -90
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 座標計算基準日時を取得します.<br/>
     * 
     * @return 日時
     */
    public Date getBaseDateTime() {
        return baseDateTime;
    }

    /**
     * 座標計算基準日時における観測地点の地方恒星時を取得します.<br/>
     * 
     * @return 地方恒星時(θ)
     */
    public double getLocalSiderealTime() {
        return localSiderealTime;
    }
}
