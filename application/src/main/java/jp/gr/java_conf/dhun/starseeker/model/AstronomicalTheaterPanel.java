/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

import android.graphics.PointF;

public class AstronomicalTheaterPanel {

    private int width;
    private int height;

    public final PointF pointLT = new PointF(); // 左上の座標
    public final PointF pointLB = new PointF(); // 左下の座標
    public final PointF pointRT = new PointF(); // 右上の座標
    public final PointF pointRB = new PointF(); // 右下の座標
}
