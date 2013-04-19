/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model.star;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


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
     * 関連する星座の集合を取得します.<br/>
     * 
     * @return 星座の集合
     */
    public Set<Constellation> getRelatedConstellations() {
        Set<Constellation> results = new HashSet<Constellation>();
        for (Star star : this) {
            results.addAll(star.getRelatedConstellations());
        }
        return Collections.unmodifiableSet(results);
    }
}
