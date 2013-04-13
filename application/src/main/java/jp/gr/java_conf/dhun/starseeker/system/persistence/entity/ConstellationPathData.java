/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

/**
 * 星座パスデータのエンティティ.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author jun
 * 
 */
public class ConstellationPathData {

    private Integer constellationPathId;    // 星座パスID
    private String constellationCode;       // 星座コード(略符)
    private Integer hipNumberFrom;          // HIP番号(起点)
    private Integer hipNumberTo;            // HIP番号(終点)

    /**
     * デフォルト・コンストラクタ
     */
    public ConstellationPathData() {
    }

    /**
     * 星座パスIDを取得します.<br/>
     */
    public Integer getConstellationPathId() {
        return constellationPathId;
    }

    /**
     * 星座パスIDを設定します.<br/>
     */
    public void setConstellationPathId(Integer constellationPathId) {
        this.constellationPathId = constellationPathId;
    }

    /**
     * 星座コード(略符)を取得します.<br/>
     */
    public String getConstellationCode() {
        return constellationCode;
    }

    /**
     * 星座コード(略符)を設定します.<br/>
     */
    public void setConstellationCode(String constellationCode) {
        this.constellationCode = constellationCode;
    }

    /**
     * HIP番号(起点)を取得します.<br/>
     */
    public Integer getHipNumberFrom() {
        return hipNumberFrom;
    }

    /**
     * HIP番号(起点)を設定します.<br/>
     */
    public void setHipNumberFrom(Integer hipNumberFrom) {
        this.hipNumberFrom = hipNumberFrom;
    }

    /**
     * HIP番号(終点)を取得します.<br/>
     */
    public Integer getHipNumberTo() {
        return hipNumberTo;
    }

    /**
     * HIP番号(終点)を設定します.<br/>
     */
    public void setHipNumberTo(Integer hipNumberTo) {
        this.hipNumberTo = hipNumberTo;
    }

    @Override
    public String toString() {
        return getConstellationCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((constellationPathId == null) ? 0 : constellationPathId.hashCode());
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
        if (!(obj instanceof ConstellationPathData)) {
            return false;
        }
        ConstellationPathData other = (ConstellationPathData) obj;
        if (constellationPathId == null) {
            if (other.constellationPathId != null) {
                return false;
            }
        } else if (!constellationPathId.equals(other.constellationPathId)) {
            return false;
        }
        return true;
    }

    public static final String TABLE_NAME = "constellation_path";

    public static class FieldNames {
        public static final String CONSTELLATION_PATH_ID = "constellation_path_id";
        public static final String CONSTELLATION_CODE = "constellation_code";
        public static final String HIP_NUMBER_FROM = "hip_num_fm";
        public static final String HIP_NUMBER_TO = "hip_num_to";

        public static final String[] ALL_COLUMNS = { CONSTELLATION_PATH_ID, CONSTELLATION_CODE, HIP_NUMBER_FROM, HIP_NUMBER_TO };
    }
}
