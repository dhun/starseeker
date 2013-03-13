/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.model.AstronomicalTheater.Rect;

/**
 * 天体シアターのパネル.<br/>
 * 
 * @author jun
 * 
 */
public class AstronomicalTheaterPanel {

    public final Rect theaterRect = new Rect(); // 天体シアターの座標
    public final Rect displayRect = new Rect(); // ディスプレイの座標
}
