/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.model;

import java.util.HashSet;

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
}
