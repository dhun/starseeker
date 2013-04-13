/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.model.Star;

/**
 * @author jun
 * 
 */
public class StarSet extends HashSet<Star> {

    private boolean located;

    public StarSet() {
        located = false;
    }

    public boolean isLocated() {
        return located;
    }

    public void setLocated(boolean located) {
        this.located = located;
    }

    /**
     * 関連する星座コードの集合を取得します.<br/>
     * 
     * @return 星座コード(略符)の集合
     */
    public Set<String> getRelatedConstellationCodes() {
        Set<String> results = new HashSet<String>();
        for (Star star : this) {
            results.addAll(star.getRelatedConstellationCodeSet());
        }
        return Collections.unmodifiableSet(results);
    }
}
