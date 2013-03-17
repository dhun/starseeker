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
        STARS.add(newStar("ポラリス", "こぐま", "02h 31.8m", "+89°16'", "ご存知北極星。赤緯９０°ではありません。"));
        // STARS.add(newStar("アルデバラン", "おうし", "04h 35m", "9°+16 31'"));
        // STARS.add(newStar("リゲル", "オリオン", "05h 14.5m", "-08°12'"));
        // STARS.add(newStar("カペラ", "ぎょしゃ", "05h 16.7m", "+46°00'"));
        // STARS.add(newStar("ベテルギウス", "オリオン", "05h 55.2m", "+07°24'"));
        // STARS.add(newStar("カノープス", "りゅうこつ", "06h 24.0m", "-52°42'"));
        // STARS.add(newStar("シリウス", "おおいぬ", "06h 45.1m", "-16°43'"));
        // STARS.add(newStar("プロキオン", "こいぬ", "07h 39.3m", "+05°14'"));
        // STARS.add(newStar("レグルス", "わし", "10h 08.4m", "+11°58'"));
        // STARS.add(newStar("スピカ", "おとめ", "13h 25.2m", "-11°10'"));
        // STARS.add(newStar("アンタレス", "さそり", "16h 29.4m", "-26°26'"));
        // STARS.add(newStar("べガ", "こと", "18h 36.9m", "+38°47'"));
        // STARS.add(newStar("デネブ", "はくちょう", "20h 41.4m", "+45°17'"));
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
