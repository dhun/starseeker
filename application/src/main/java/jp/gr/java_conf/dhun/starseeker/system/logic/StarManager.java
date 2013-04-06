/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.logic;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.logic.StarLocator;
import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.StarDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarEntity;

/**
 * @author jun
 * 
 */
public class StarManager {

    private final StarDao starDao;
    private final Set<Star> stars;

    private StarLocator starLocator;

    DecimalFormat angleFormat;

    public StarManager() {
        this.starDao = new StarDao();
        this.stars = new HashSet<Star>();

        angleFormat = new DecimalFormat("0.00");
        angleFormat.setPositivePrefix("+");
        angleFormat.setNegativePrefix("-");
        angleFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public void configure(float magnitude) {
        stars.clear();
        for (StarEntity starEntity : starDao.findAll()) {
            Star star = new Star(starEntity);
            stars.add(star);
        }
        // stars.add(newMock(+10, +80)); // FIXME モック
        // stars.add(newMock(-10, +75));
        // stars.add(newMock(+10, -80));
        // stars.add(newMock(-10, -75));
    }

    // private Star newMock(final float azimuthFix, final float altitudeFix) { // FIXME モック
    // return new Star(azimuthFix, altitudeFix) {
    //
    // @Override
    // public float getAzimuth() {
    // return azimuthFix;
    // }
    //
    // @Override
    // public float getAltitude() {
    // return altitudeFix;
    // }
    //
    // @Override
    // public float getMagnitude() {
    // return -1;
    // }
    //
    // @Override
    // public String getName() {
    // return "モック";
    // }
    // };
    // }

    public void relocate(double longitude, double latitude, Calendar baseCalendar) {
        starLocator = new StarLocator(longitude, latitude, baseCalendar.getTime()); // FIXME UTC? localtime?
        for (Star star : stars) {
            starLocator.locate(star);
            star.setDisplayText(String.format("方位(A)=[%s], 高度(h)=[%s], 名前=[%s]" //
                    , angleFormat.format(star.getAzimuth())  // 方位(A)
                    , angleFormat.format(star.getAltitude()) // 高度(h)
                    , star.getName()));

        }
    }

    public Iterable<Star> iterate() {
        return stars;
    }
}
