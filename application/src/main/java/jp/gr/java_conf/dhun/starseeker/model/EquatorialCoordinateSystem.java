/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jun
 * 
 */
public class EquatorialCoordinateSystem {

    public static final Set<Star> STARS;

    static {
        STARS = new HashSet<Star>();
        STARS.add(newStar("02h 31.8m", "+89°16'", "ポラリス", "こぐま", "ご存知北極星。赤緯９０°ではありません。"));
        STARS.add(newStar("04h 35.0m", "+09°31'", "アルデバラン", "おうし")); // XXX 値を確認する
        STARS.add(newStar("05h 14.5m", "-08°12'", "リゲル", "オリオン"));
        STARS.add(newStar("05h 16.7m", "+46°00'", "カペラ", "ぎょしゃ"));
        STARS.add(newStar("05h 55.2m", "+07°24'", "ベテルギウス", "オリオン"));
        STARS.add(newStar("06h 24.0m", "-52°42'", "カノープス", "りゅうこつ"));
        STARS.add(newStar("06h 45.1m", "-16°43'", "シリウス", "おおいぬ"));
        STARS.add(newStar("07h 39.3m", "+05°14'", "プロキオン", "こいぬ"));
        STARS.add(newStar("10h 08.4m", "+11°58'", "レグルス", "わし"));
        STARS.add(newStar("13h 25.2m", "-11°10'", "スピカ", "おとめ"));
        STARS.add(newStar("16h 29.4m", "-26°26'", "アンタレス", "さそり"));
        STARS.add(newStar("18h 36.9m", "+38°47'", "べガ", "こと"));
        STARS.add(newStar("20h 41.4m", "+45°17'", "デネブ", "はくちょう"));
    }

    private static Star newStar(String rightAscension, String declination, String name, String starSignName, String memo) {
        Star star = new Star(rightAscension, declination);
        star.setName(name);
        // star.setMemo(memo); // XXX 後で実装する
        return star;
    }

    private static Star newStar(String rightAscension, String declination, String name, String starSignName) {
        return newStar(rightAscension, declination, name, starSignName, null);
    }
}
