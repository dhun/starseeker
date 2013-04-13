/**
 * 
 */
package jp.gr.java_conf.dhun.starseeker.model;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationPathData;

/**
 * 星座パス.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author j_hosoya
 * 
 */
public class ConstellationPath {

    private final ConstellationPathData constellationPathData;    // 星座パスデータ
    private final Star fromStar;    // パスの起点となる星
    private final Star toStar;      // パスの終点となる星

    private final boolean locaated; // 配置済であるかどうか

    /**
     * コンストラクタ.<br/>
     * 
     * @param constellationPathData 星座パスデータ
     * @param fromStar
     * @param toStar
     */
    public ConstellationPath(ConstellationPathData constellationPathData, Star fromStar, Star toStar) {
        this.constellationPathData = constellationPathData;
        this.fromStar = fromStar;
        this.toStar = toStar;
        this.locaated = false;
    }

    /**
     * 星座パスIDを取得します.<br/>
     */
    public Integer getConstellationPathId() {
        return constellationPathData.getConstellationPathId();
    }

    /**
     * 星座コード(略符)を取得します.<br/>
     */
    public String getConstellationCode() {
        return constellationPathData.getConstellationCode();
    }

    /**
     * パスの起点となる星を取得します
     */
    public Star getFromStar() {
        return fromStar;
    }

    /**
     * パスの終点となる星を取得します
     */
    public Star getToStar() {
        return toStar;
    }

    /**
     * 星が配置済であるかどうかを取得します.<br/>
     */
    public boolean isLocated() {
        return locaated;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((constellationPathData == null) ? 0 : constellationPathData.hashCode());
        result = prime * result + ((fromStar == null) ? 0 : fromStar.hashCode());
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
        if (!(obj instanceof ConstellationPath)) {
            return false;
        }
        ConstellationPath other = (ConstellationPath) obj;
        if (constellationPathData == null) {
            if (other.constellationPathData != null) {
                return false;
            }
        } else if (!constellationPathData.equals(other.constellationPathData)) {
            return false;
        }
        if (fromStar == null) {
            if (other.fromStar != null) {
                return false;
            }
        } else if (!fromStar.equals(other.fromStar)) {
            return false;
        }
        return true;
    }

}
