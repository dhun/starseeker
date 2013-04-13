/**
 * 
 */
package jp.gr.java_conf.dhun.starseeker.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationData;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * 星座.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author j_hosoya
 * 
 */
public class Constellation {

    private final ConstellationData constellationData;          // 星座データ
    private final Set<ConstellationPath> constellationPaths;    // 星座パスの集合
    private final Set<Star> componentStars;                     // 構成要素となる星の集合

    private final boolean locaated;   // 配置済であるかどうか

    /**
     * コンストラクタ.<br/>
     * 
     * @param constellationData 星座データ
     */
    public Constellation(ConstellationData constellationData) {
        this.constellationData = constellationData;
        this.constellationPaths = new HashSet<ConstellationPath>();
        this.componentStars = new HashSet<Star>();
        this.locaated = false;
    }

    /**
     * 星座パスを追加します.<br/>
     */
    public void addPath(ConstellationPath path) {
        constellationPaths.add(path);
        componentStars.add(path.getFromStar());
        componentStars.add(path.getToStar());
    }

    public Set<Star> getComponentStars() {
        return Collections.unmodifiableSet(componentStars);
    }

    /**
     * 星座コード(略符)を取得します.<br/>
     */
    public String getConstellationCode() {
        return constellationData.getConstellationCode();
    }

    /**
     * 星座名(学名)を取得します.<br/>
     */
    public String getConstellationName() {
        return constellationData.getConstellationName();
    }

    /**
     * 星座名(日本語)を取得します.<br/>
     */
    public String getJapaneseName() {
        return constellationData.getJapaneseName();
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     */
    public float getRightAscension() {
        return constellationData.getRightAscension();
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     */
    public float getDeclination() {
        return constellationData.getDeclination();
    }

    /**
     * 星が配置済であるかどうかを取得します.<br/>
     */
    public boolean isLocated() {
        return locaated;
    }

    @Override
    public String toString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 名前=[%s]" //
                , StarLocationUtil.convertAngleFloatToString(getRightAscension())  //
                , StarLocationUtil.convertAngleFloatToString(getDeclination())     //
                , getJapaneseName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((constellationData == null) ? 0 : constellationData.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Constellation)) {
            return false;
        }
        Constellation other = (Constellation) obj;
        if (constellationData == null) {
            if (other.constellationData != null) {
                return false;
            }
        } else if (!constellationData.equals(other.constellationData)) {
            return false;
        }
        return true;
    }

}
