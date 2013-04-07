/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.logic;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.logic.StarLocator;
import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.model.StarApproxMagnitude;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.StarDataDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarEntity;
import android.content.Context;

/**
 * @author jun
 * 
 */
public class StarManager {

    private static final boolean APPEND_MOCK_STAR = false;

    private final DatabaseHelper databaseHelper;
    private final StarDataDao starDataDao;

    private final Map<StarApproxMagnitude, Set<Star>> allStars;   // すべての星データ. おおよその等級別に管理

    private final DecimalFormat angleFormat;

    private ExtractStarIterator extractStarIterator;
    private StarLocator starLocator;

    public StarManager(Context context) {
        databaseHelper = new DatabaseHelper(context);
        starDataDao = new StarDataDao(databaseHelper.getReadableDatabase());

        allStars = new HashMap<StarApproxMagnitude, Set<Star>>();

        angleFormat = new DecimalFormat("0.00");
        angleFormat.setPositivePrefix("+");
        angleFormat.setNegativePrefix("-");
        angleFormat.setRoundingMode(RoundingMode.HALF_UP);

        // extractStarIterator = new ExtractStarIterator(14f); FIXME これは必要？
    }

    public void configure(float extractStarMagnitude) {
        allStars.clear();
        for (StarEntity starEntity : starDataDao.findAll()) {
            Star star = new Star(starEntity);
            addStar(star);
        }

        if (APPEND_MOCK_STAR) {
            addStar(newMock(+10, +80));
            addStar(newMock(-10, +75));
            addStar(newMock(+10, -80));
            addStar(newMock(-10, -75));
        }

        extractStarIterator = new ExtractStarIterator(extractStarMagnitude);
    }

    private void addStar(Star star) {
        StarApproxMagnitude approxMagnitude = new StarApproxMagnitude(star.getMagnitude());
        Set<Star> starSet = allStars.get(approxMagnitude);
        if (starSet == null) {
            starSet = new HashSet<Star>();
            allStars.put(approxMagnitude, starSet);
        }
        starSet.add(star);
    }

    public void relocate(double longitude, double latitude, Calendar baseCalendar) {
        starLocator = new StarLocator(longitude, latitude, baseCalendar.getTime()); // UTC
        for (Star star : iterate()) {
            starLocator.locate(star);
            if (star.getMagnitude() <= 2) {
                // ２等星以上なら画面に名前と位置を表示
                star.setDisplayText(String.format("方位(A)=[%s], 高度(h)=[%s], 名前=[%s]" //
                        , angleFormat.format(star.getAzimuth())  // 方位(A)
                        , angleFormat.format(star.getAltitude()) // 高度(h)
                        , star.getName()));
            } else {
                star.setDisplayText(null);
            }

        }
    }

    public Iterable<Star> iterate() {
        extractStarIterator.reset();

        return new Iterable<Star>() {
            @Override
            public Iterator<Star> iterator() {
                return extractStarIterator;
            }
        };
    }

    private class ExtractStarIterator implements Iterator<Star> {
        private final float extractStarMagnitude;       // 抽出する等級
        private final Set<Set<Star>> extractStars;      // 抽出する等級以下の星の集合. おおよその等級別に管理

        private Iterator<Set<Star>> starSetIterator;    // おおよその等級別の星の集合イテレータ
        private Iterator<Star> starIterator;            // 星のイテレータ
        private Star next;

        public ExtractStarIterator(float extractStarMagnitude) {
            this.extractStarMagnitude = extractStarMagnitude;

            this.extractStars = new HashSet<Set<Star>>();
            for (Entry<StarApproxMagnitude, Set<Star>> e : allStars.entrySet()) {
                if (e.getKey().getApproxMagnitude() <= extractStarMagnitude) {
                    extractStars.add(e.getValue());
                }
            }

            this.starSetIterator = null;
            this.starIterator = null;
            this.next = null;

            reset();
        }

        public void reset() {
            starSetIterator = extractStars.iterator();
            if (starSetIterator.hasNext()) {
                starIterator = starSetIterator.next().iterator();
            } else {
                starIterator = null;
            }
        }

        private void prepareNext() {
            if (null != next) {
                return;
            }

            if (null != starIterator && starIterator.hasNext()) {
                next = starIterator.next();

            } else if (starSetIterator.hasNext()) {
                starIterator = starSetIterator.next().iterator();
                if (starIterator.hasNext()) {
                    next = starIterator.next();
                } else {
                    next = null;
                }

            } else {
                starIterator = null;
                next = null;
            }
        }

        @Override
        public boolean hasNext() {
            if (null == next) {
                prepareNext();
            }

            return null != next;
        }

        @Override
        public Star next() {
            if (null == next) {
                prepareNext();
            }

            if (null != next) {
                Star result = next;
                next = null;
                return result;

            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private Star newMock(final float azimuthFix, final float altitudeFix) { // FIXME モック
        return new Star(azimuthFix, altitudeFix) {

            @Override
            public float getAzimuth() {
                return azimuthFix;
            }

            @Override
            public float getAltitude() {
                return altitudeFix;
            }

            @Override
            public float getMagnitude() {
                return -1;
            }

            @Override
            public String getName() {
                return "モック";
            }
        };
    }
}
