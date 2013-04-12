/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 星のDAO.<br/>
 * 
 * @author jun
 * 
 */
@Deprecated
public class StarDao {

    private static final Set<StarData> STARS;

    static {
        // http://seiza-zukan.com/first.html
        STARS = new HashSet<StarData>();
        STARS.add(newStar("02h 31.8m", "+89°16'", "+2.00", "ポラリス", "こぐま", "ご存知北極星。赤緯９０°ではありません。変光星らしいが２等級固定にした"));
        STARS.add(newStar("04h 35.9m", "+16°31'", "+0.85", "アルデバラン", "おうし"));
        STARS.add(newStar("05h 14.5m", "-08°12'", "+0.12", "リゲル", "オリオン"));
        STARS.add(newStar("05h 16.7m", "+46°00'", "+0.08", "カペラ", "ぎょしゃ"));
        STARS.add(newStar("05h 55.2m", "+07°24'", "+0.40", "ベテルギウス", "オリオン"));
        STARS.add(newStar("06h 24.0m", "-52°42'", "-0.72", "カノープス", "りゅうこつ"));
        STARS.add(newStar("06h 45.1m", "-16°43'", "-1.46", "シリウス", "おおいぬ"));
        STARS.add(newStar("07h 39.3m", "+05°14'", "+0.37", "プロキオン", "こいぬ"));
        STARS.add(newStar("10h 08.4m", "+11°58'", "+1.35", "レグルス", "わし"));
        STARS.add(newStar("13h 25.2m", "-11°10'", "+0.98", "スピカ", "おとめ"));
        STARS.add(newStar("16h 29.4m", "-26°26'", "+0.96", "アンタレス", "さそり"));
        STARS.add(newStar("18h 36.9m", "+38°47'", "+0.03", "べガ", "こと"));
        STARS.add(newStar("20h 41.4m", "+45°17'", "+1.25", "デネブ", "はくちょう"));
    }

    private static StarData newStar(String rightAscension, String declination, String magnitude, String name, String starSignName, String memo) {
        StarData entity = new StarData();
        entity.setRightAscension(StarLocationUtil.convertHourStringToFloat(rightAscension));
        entity.setDeclination(StarLocationUtil.convertAngleStringToFloat(declination));
        entity.setMagnitude(Float.valueOf(magnitude));
        entity.setName(name);
        entity.setMemo(memo);
        return entity;
    }

    private static StarData newStar(String rightAscension, String declination, String magnitude, String name, String starSignName) {
        return newStar(rightAscension, declination, magnitude, name, starSignName, null);
    }

    public Collection<StarData> findAll() {
        return Collections.unmodifiableSet(STARS);
    }
}
