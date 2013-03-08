/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.model;

/**
 * 座標
 * 
 * @author jun
 * @deprecated
 */
@Deprecated
public class Coordinates {

    public double longitude; // 経度(λ). 東経を - 西経を + とする. -180から+180
    public double latitude;  // 緯度(ψ). 北緯を + 南緯を - とする. +90から-90
}
