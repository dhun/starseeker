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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.system.model.star.Constellation;
import jp.gr.java_conf.dhun.starseeker.system.model.star.ConstellationPath;
import jp.gr.java_conf.dhun.starseeker.system.model.star.Star;
import jp.gr.java_conf.dhun.starseeker.system.model.star.StarMagnitude;
import jp.gr.java_conf.dhun.starseeker.system.model.star.StarSet;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.ConstellationDataDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.ConstellationPathDataDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.StarDataDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationData;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationPathData;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 星のマネージャ.<br/>
 * 
 * @author jun
 * 
 */
public class StarManager {

    private static final boolean DISPLAY_STAR_LOCATION = false;   // 星の地平座標を表示するかどうか

    private static final boolean APPEND_MOCK_STAR = false;              // モックデータを含めるかどうか. 通常はfalse

    // DAO関連
    private final DatabaseHelper databaseHelper;

    // 星と星座の集合
    private final Map<Integer, Star> byHipNumberExtractStars;           // 抽出した星のマップ. キーはHIP番号
    private final Map<StarMagnitude, StarSet> byMagnitudeExtractStars;  // 抽出した星セットのマップ. キーはおおよその等級
    private final Map<String, Constellation> extractConstellations;     // 抽出した星座のマップ. キーは星座コード(略符)

    // 星の抽出関連
    private float extractUpperStarMagnitude;        // 抽出する等級の上限
    private TargetStarIterator targetStarIterator;

    // 星の配置関連
    private StarLocator starLocator;
    private double observationLocationLongitude;    // 観測地点の経度
    private double observationLocationLatitude;     // 観測地点の緯度
    private Calendar observationCalendar;           // 観測日時を示すカレンダー

    // 自身の振る舞いを制御するフラグ
    private boolean needReextract;      // 星の再抽出が必要かどうか
    private boolean needRelocate;       // 星の再配置が必要かどうか
    private boolean needNewStarLocator; // 星ロケータの再生成が必要かどうか

    @SuppressLint("UseSparseArrays")
    public StarManager(Context context) {
        databaseHelper = new DatabaseHelper(context.getApplicationContext());

        byHipNumberExtractStars = new HashMap<Integer, Star>();
        byMagnitudeExtractStars = new HashMap<StarMagnitude, StarSet>();
        extractConstellations = new HashMap<String, Constellation>();

        extractUpperStarMagnitude = 0.0f;
        targetStarIterator = null;

        starLocator = null;
        observationLocationLongitude = 0.0;
        observationLocationLatitude = 0.0;
        observationCalendar = null;

        needReextract = false;
        needRelocate = false;
        needNewStarLocator = false;
    }

    public void initialize() {
        byMagnitudeExtractStars.clear();

        if (APPEND_MOCK_STAR) {
            addMockStar(+10, +80);
            addMockStar(-10, +75);
            addMockStar(+10, -80);
            addMockStar(-10, -75);
        }
    }

    /**
     * 指定された等級以下の星と星座を抽出します.
     */
    public void extract() {
        if (!needReextract) {
            return;
        }

        final SQLiteDatabase db = databaseHelper.getReadableDatabase();

        try {
            // 指定された等級以下の星を抽出. 不足データを次に追加.
            // ⇒ byMagnitudeExtractStars
            // ⇒ byHipNumberExtractStars
            extractStar(db);

            // 指定された等級以下の星に関連する星を抽出. 不足データを次に追加.
            // ⇒ extractConstellations
            // ⇒ byHipNumberExtractStars
            extractConstellation(db);

            needReextract = false;

        } finally {
            db.close();
        }
    }

    /**
     * 指定された等級以下の星を抽出します.
     * 
     * @param db データベース
     */
    private void extractStar(SQLiteDatabase db) {
        final StarDataDao starDataDao = new StarDataDao(db);

        int extractCount = 0;
        for (StarMagnitude magnitude : StarMagnitude.listRoughMagnitudes(extractUpperStarMagnitude)) {
            StarSet starSet = byMagnitudeExtractStars.get(magnitude);
            if (starSet != null) {
                // この等級は抽出済みのためスキップ
                continue;
            }

            // 星マップに要素を追加. 星がゼロ件でも追加する
            starSet = new StarSet();
            starSet.setLocated(false);
            byMagnitudeExtractStars.put(magnitude, starSet);

            for (StarData entity : starDataDao.findByMagnitudeRange(magnitude)) {
                Star star = findExtractedStar(starDataDao, entity.getHipNumber());
                starSet.add(star);
                if (byHipNumberExtractStars.containsKey(star.getHipNumber()) == false) {
                    byHipNumberExtractStars.put(star.getHipNumber(), star);
                }
                extractCount++;
            }
        }

        LogUtils.i(getClass(), "星を抽出した. count=[" + extractCount + "]");
    }

    /**
     * 指定された等級以下の星に関連する星座を抽出します.
     * 
     * @param db データベース
     */
    private void extractConstellation(SQLiteDatabase db) {
        final StarDataDao starDataDao = new StarDataDao(db);
        final ConstellationDataDao constellationDataDao = new ConstellationDataDao(db);
        final ConstellationPathDataDao constellationPathDataDao = new ConstellationPathDataDao(db);

        // 指定された等級以下の星を含む星座を抽出
        StarMagnitude magnitude = new StarMagnitude(extractUpperStarMagnitude);
        List<ConstellationData> constellationDatas = constellationDataDao.findLessThanUpperMagnitude(magnitude);

        int extractCount = 0;
        for (ConstellationData constellationData : constellationDatas) {
            // 抽出済みの星座ならスキップ
            if (this.extractConstellations.containsKey(constellationData.getConstellationCode())) {
                continue;
            }

            // 星座を構築
            Constellation constellation = new Constellation(constellationData);
            extractConstellations.put(constellation.getConstellationCode(), constellation);

            // 星座を構成するパスを抽出
            List<ConstellationPathData> constellationPathDatas = constellationPathDataDao.findByConstellationCode(constellationData.getConstellationCode());
            for (ConstellationPathData pathData : constellationPathDatas) {
                // 星座パスに星を割り当てる. 該当がなければ星データも抽出する
                Star fmStar = findExtractedStar(starDataDao, pathData.getHipNumberFrom());
                Star toStar = findExtractedStar(starDataDao, pathData.getHipNumberTo());

                fmStar.addRelatedConstellation(constellation);
                toStar.addRelatedConstellation(constellation);

                ConstellationPath path = new ConstellationPath(pathData, fmStar, toStar);
                constellation.addPath(path);
            }

            extractCount++;
        }

        LogUtils.i(getClass(), "星座を抽出した. count=[" + extractCount + "]");
    }

    private Star findExtractedStar(StarDataDao starDataDao, Integer hipNumber) {
        Star star = byHipNumberExtractStars.get(hipNumber);
        if (star == null) {
            StarData starData = starDataDao.findByPk(hipNumber);
            star = new Star(starData);
            byHipNumberExtractStars.put(hipNumber, star);
        }
        return star;
    }

    /**
     * 指定された等級以下の星を配置します.
     */
    public void locate() {
        if (!needRelocate) {
            return;
        }

        // 観測条件が変更されていれば、星ロケータを再生成
        // ⇒ 星ロケータは「観測地点と観測日時」に対してインスタンスが必要になる
        if (needNewStarLocator) {
            if (null != observationCalendar) { // FIXME 本当は不要. ノイズ
                needNewStarLocator = false;
                starLocator = new StarLocator(observationLocationLongitude, observationLocationLatitude, observationCalendar.getTime()); // UTC
            }
        }

        // フォーマッタ. インスタンスフィールドにしても構わないけど、大した負荷じゃないのでメソッドローカルにした
        DecimalFormat angleFormat = new DecimalFormat("0.00");
        angleFormat.setPositivePrefix("+");
        angleFormat.setNegativePrefix("-");
        angleFormat.setRoundingMode(RoundingMode.HALF_UP);

        // FIXME 本当は不要. ノイズ
        if (null == starLocator) {
            return;
        }

        TargetStarIterator targetStarIterator = new TargetStarIterator(extractUpperStarMagnitude);

        int locateCount = 0;
        for (Star star : targetStarIterator) {
            starLocator.locate(star);

            if (star.getMagnitude() <= StarData.MAGNITUDE_FOR_DISPLAY_UPPER && null != star.getName()) {
                // 星の等級がしきい値以下で、かつ名前が設定されていれば画面にデータを表示
                if (null != star.getMemo()) {
                    if (DISPLAY_STAR_LOCATION) {
                        star.setDisplayText(String.format("方位(A)=[%s], 高度(h)=[%s], 名前=[%s], 備考=[%s]" //
                                , angleFormat.format(star.getAzimuth())  // 方位(A)
                                , angleFormat.format(star.getAltitude()) // 高度(h)
                                , star.getName()
                                , star.getMemo()));
                    } else {
                        star.setDisplayText(String.format("名前=[%s], 備考=[%s]" //
                                , star.getName()
                                , star.getMemo()));
                    }
                } else {
                    if (DISPLAY_STAR_LOCATION) {
                        star.setDisplayText(String.format("方位(A)=[%s], 高度(h)=[%s], 名前=[%s]" //
                                , angleFormat.format(star.getAzimuth())  // 方位(A)
                                , angleFormat.format(star.getAltitude()) // 高度(h)
                                , star.getName()));
                    } else {
                        star.setDisplayText(String.format("名前=[%s]" //
                                , star.getName()));
                    }
                }

            } else {
                // 上記以外は、画面に星データを表示しない
                star.setDisplayText(null);
            }

            locateCount++;
        }

        needRelocate = false;
        LogUtils.i(getClass(), "星の地平座標を配置した. count=[" + locateCount + "]");
    }

    /**
     * 抽出する等級の上限値を設定します.
     * 
     * @param magnitude 抽出する等級の上限
     */
    public void setExtractUpperStarMagnitude(float magnitude) {
        if (extractUpperStarMagnitude == magnitude) {
            return;
        }

        extractUpperStarMagnitude = magnitude;
        needReextract = true;
        needRelocate = true;
    }

    /**
     * 観測地点を設定します.
     * 
     * @param longitude 経度
     * @param latitude 緯度
     */
    public void setObservationLocation(double longitude, double latitude) {
        if (observationLocationLongitude == longitude && observationLocationLatitude == latitude) {
            return;
        }

        observationLocationLongitude = longitude;
        observationLocationLatitude = latitude;
        needRelocate = true;
        needNewStarLocator = true;
    }

    /**
     * 観測日時を設定します.
     * 
     * @param calendar 観測日時を示すカレンダー
     */
    public void setObservationDatetime(Calendar calendar) {
        if (null != observationCalendar && observationCalendar.equals(calendar)) {
            return;
        }

        observationCalendar = calendar;
        needRelocate = true;
        needNewStarLocator = true;
    }

    @Deprecated
    public void prepare() {
        // // 必要であれば星を抽出
        // if (needReextract) {
        // extract();
        // }
        //
        // // 観測条件が変更されていれば、星ロケータを再生成
        // // ⇒ 星ロケータは「観測地点と観測日時」に対してインスタンスが必要になる
        // if (needNewStarLocator) {
        // if (null != observationCalendar) { // FIXME 本当は不要. ノイズ
        // needNewStarLocator = false;
        // starLocator = new StarLocator(observationLocationLongitude, observationLocationLatitude, observationCalendar.getTime()); // UTC
        // }
        // }
        //
        // // 必要であれば星を再配置
        // if (needRelocate) {
        // locate();
        // }

        targetStarIterator = new TargetStarIterator(extractUpperStarMagnitude);
    }

    // public void extract(float extractStarMagnitude) {
    // extractOnly(extractStarMagnitude);
    // if (null != starLocator) {
    // relocateOnly(extractStarMagnitude);
    // }
    //
    // extractStarIterator = new ExtractStarIterator(extractStarMagnitude);
    // }
    //
    // public void relocate(double longitude, double latitude, Calendar baseCalendar) {
    // // 星ロケータは、観測地点と観測日時に対してインスタンスが必要になる
    // starLocator = new StarLocator(longitude, latitude, baseCalendar.getTime()); // UTC
    //
    // // 星イテレータはスレッドセーフじゃないので、再配置用のインスタンスを生成
    // ExtractStarIterator extractStarIterator = new ExtractStarIterator(this.extractStarIterator.getExtractStarMagnitude());
    //
    // relocateOnly(extractStarIterator.getExtractStarMagnitude());
    // }

    public Iterable<Star> provideTargetStars() {
        return targetStarIterator.provideTargetStars();
    }

    public Iterable<Constellation> provideTargetConstellations() {
        return targetStarIterator.provideTargetConstellations();
    }

    /**
     * 星のイテレータ.<br/>
     */
    private class TargetStarIterator implements Iterator<Star>, Iterable<Star> {
        private final Map<StarMagnitude, StarSet> targetStars;  // 処理対象とする星のマップ. キーはおおよその等級
        private final Set<Constellation> targetConstellations;  // 処理対象とする星座セット.

        private Iterator<StarSet> starSetIterator;      // おおよその等級別の星の集合イテレータ
        private Iterator<Star> starIterator;            // 星のイテレータ
        private Star next;

        /**
         * コンストラクタ
         * 
         * @param extractStarMagnitude 抽出する星の等級
         */
        public TargetStarIterator(float extractStarMagnitude) {
            this(new StarMagnitude(extractStarMagnitude));
        }

        /**
         * コンストラクタ
         * 
         * @param extractStarMagnitude 抽出する星の等級
         */
        public TargetStarIterator(StarMagnitude extractStarMagnitude) {
            this.targetStars = new HashMap<StarMagnitude, StarSet>();
            this.targetConstellations = new HashSet<Constellation>();

            // 指定された等級以下の星を抽出
            for (Entry<StarMagnitude, StarSet> e : byMagnitudeExtractStars.entrySet()) {
                StarMagnitude magnitude = e.getKey();
                if (magnitude.getRoughMagnitude() <= extractStarMagnitude.getRoughMagnitude()) {
                    StarSet starSet = e.getValue();
                    if (!starSet.isEmpty()) {
                        targetStars.put(magnitude, starSet);
                        targetConstellations.addAll(starSet.getRelatedConstellations());
                    }
                }
            }

            // 指定された等級以下の星に関連する星座を構成する星も抽出
            for (Constellation relatedConstellation : targetConstellations) {
                for (Star componentStar : relatedConstellation.getComponentStars()) {
                    StarMagnitude magnitude = new StarMagnitude(componentStar.getMagnitude());
                    if (magnitude.getRoughMagnitude() <= extractStarMagnitude.getRoughMagnitude()) {
                        // 「指定された等級以下の星を抽出」で抽出済のためスキップ
                        continue;
                    }

                    StarSet starSet = targetStars.get(magnitude);
                    if (starSet == null) {
                        starSet = new StarSet();
                        targetStars.put(magnitude, starSet);
                    }
                    starSet.add(componentStar);
                }
            }

            this.starSetIterator = null;
            this.starIterator = null;
            this.next = null;

            reset();
        }

        public void reset() {
            starSetIterator = targetStars.values().iterator();
            if (starSetIterator.hasNext()) {
                starIterator = starSetIterator.next().iterator();
            } else {
                starIterator = null;
            }
        }

        public Iterable<Star> provideTargetStars() {
            reset();
            return this;
        }

        public Iterable<Constellation> provideTargetConstellations() {
            return targetConstellations;
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

        // ========================================
        // Iterator and Iterable implements
        // ========================================

        @Override
        public Iterator<Star> iterator() {
            return this;
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

    private void addMockStar(final float azimuthFix, final float altitudeFix) {
        StarData entity = new StarData();
        entity.setHipNumber((int) (azimuthFix * 100 + altitudeFix));    // HIP番号は嘘情報
        entity.setRightAscension(azimuthFix);   // 赤径はうそ情報
        entity.setDeclination(altitudeFix);     // 赤緯はうそ情報
        entity.setMagnitude(-1);
        entity.setName("モック");
        Star mockStar = new Star(entity) {
            @Override
            public float getAzimuth() {
                return azimuthFix;  // 方位は固定. 観測条件の影響はうけない
            }

            @Override
            public float getAltitude() {
                return altitudeFix; // 高度は固定. 観測条件の影響はうけない
            }
        };

        StarMagnitude magnitude = new StarMagnitude(mockStar.getMagnitude());
        StarSet starSet = byMagnitudeExtractStars.get(magnitude);
        if (starSet == null) {
            starSet = new StarSet();
            byMagnitudeExtractStars.put(magnitude, starSet);
            byHipNumberExtractStars.put(mockStar.getHipNumber(), mockStar);
        }
        starSet.add(mockStar);
    }
}
