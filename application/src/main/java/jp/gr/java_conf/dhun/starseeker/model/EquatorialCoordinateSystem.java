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
        STARS.add(newStar("ポラリス", "こぐま", "02 31.8", "+89 16", "ご存知北極星。赤緯９０°ではありません。"));
        STARS.add(newStar("アルデバラン", "おうし", "04 35", "9 +16 31"));
        STARS.add(newStar("リゲル", "オリオン", "05 14.5", "-08 12"));
        STARS.add(newStar("カペラ", "ぎょしゃ", "05 16.7", "+46 00"));
        STARS.add(newStar("ベテルギウス", "オリオン", "05 55.2", "+07 24"));
        STARS.add(newStar("カノープス", "りゅうこつ", "06 24.0", "-52 42"));
        STARS.add(newStar("シリウス", "おおいぬ", "06 45.1", "-16 43"));
        STARS.add(newStar("プロキオン", "こいぬ", "07 39.3", "+05 14"));
        STARS.add(newStar("レグルス", "わし", "10 08.4", "+11 58"));
        STARS.add(newStar("スピカ", "おとめ", "13 25.2", "-11 10"));
        STARS.add(newStar("アンタレス", "さそり", "16 29.4", "-26 26"));
        STARS.add(newStar("べガ", "こと", "18 36.9", "+38 47"));
        STARS.add(newStar("デネブ", "はくちょう", "20 41.4", "+45 17"));
    }

    private static Star newStar(String name, String starSignName, String rightAscension, String declination, String memo) {
        return newStar(name, starSignName, rightAscension, declination);

    }

    private static Star newStar(String name, String starSignName, String rightAscension, String declination) {
        Star star = new Star(rightAscension, declination);
        star.setName(name);
        return star;
    }
}
