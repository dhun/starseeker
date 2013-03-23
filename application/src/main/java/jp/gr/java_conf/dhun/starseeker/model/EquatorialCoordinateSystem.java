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
        // http://seiza-zukan.com/first.html
        STARS = new HashSet<Star>();
        STARS.add(newStar("02h 31.8m", "+89°16'", "+2.00", "ポラリス", "こぐま", "ご存知北極星。赤緯９０°ではありません。変光星らしいが２等星固定にした"));
        STARS.add(newStar("04h 35.9m", "+16°31'", "+0.85", "アルデバラン", "おうし")); // XXX 値を確認する
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

        STARS.add(new Star("00h 00.0m", "+00°00'") {
            @Override
            public String getName() {
                return "モック星";
            }

            @Override
            public float getAzimuth() {
                return -120;
            }

            @Override
            public float getAltitude() {
                return +80;
            }
        });

        STARS.add(new Star("00h 00.1m", "+00°00'") {
            @Override
            public String getName() {
                return "モック星";
            }

            @Override
            public float getAzimuth() {
                return +120;
            }

            @Override
            public float getAltitude() {
                return +80;
            }
        });
    }

    private static Star newStar(String rightAscension, String declination, String magnitude, String name, String starSignName, String memo) {
        Star star = new Star(rightAscension, declination);
        star.setName(name);
        // star.setMagnitude(magnitude); // XXX 後で実装する
        // star.setMemo(memo); // XXX 後で実装する
        return star;
    }

    private static Star newStar(String rightAscension, String declination, String magnitude, String name, String starSignName) {
        return newStar(rightAscension, declination, magnitude, name, starSignName, null);
    }
}
