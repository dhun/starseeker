/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.logic;

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

    public StarManager() {
        this.starDao = new StarDao();
        this.stars = new HashSet<Star>();
    }

    public void configure(float magnitude) {
        stars.clear();
        for (StarEntity starEntity : starDao.findAll()) {
            Star star = new Star(starEntity);
            stars.add(star);
        }
    }

    public void relocate(double longitude, double latitude, Calendar baseCalendar) {
        starLocator = new StarLocator(longitude, latitude, baseCalendar.getTime()); // FIXME UTC? localtime?
        for (Star star : stars) {
            starLocator.locate(star);
        }
    }

    public Iterable<Star> iterate() {
        return stars;
    }
}
