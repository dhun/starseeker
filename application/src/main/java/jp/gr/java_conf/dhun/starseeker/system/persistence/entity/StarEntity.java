/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import jp.gr.java_conf.dhun.starseeker.model.Star;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;
import android.annotation.SuppressLint;

/**
 * 星のエンティティ.<br/>
 * 精度はfloat. 厳密な値より処理速度を優先させた.
 * 
 * @author jun
 * 
 */
public class StarEntity {

    private Long id;                // ID
    private float rightAscension;   // 赤経(α)の数値表現
    private float declination;      // 赤緯(δ)の数値表現
    private Float magnitude;        // 等級(省略可)
    private String name;            // 名前(省略可)
    private String memo;            // 備考(省略可)

    /**
     * デフォルト・コンストラクタ
     */
    public StarEntity() {
    }

    /**
     * IDを取得します.<br/>
     */
    public Long getId() {
        return id;
    }

    /**
     * IDを設定します.<br/>
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 赤経(α)の数値表現を取得します.<br/>
     */
    public float getRightAscension() {
        return rightAscension;
    }

    /**
     * 赤経(α)の数値表現を設定します.<br/>
     */
    public void setRightAscension(float rightAscension) {
        this.rightAscension = rightAscension;
    }

    /**
     * 赤緯(δ)の数値表現を取得します.<br/>
     */
    public float getDeclination() {
        return declination;
    }

    /**
     * 赤緯(δ)の数値表現を設定します.<br/>
     */
    public void setDeclination(float declination) {
        this.declination = declination;
    }

    /**
     * 等級を取得します.<br/>
     */
    public Float getMagnitude() {
        return magnitude;
    }

    /**
     * 等級を設定します.<br/>
     */
    public void setMagnitude(Float magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * 名前を取得します.<br/>
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を設定します.<br/>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * メモを取得します.<br/>
     */
    public String getMemo() {
        return memo;
    }

    /**
     * メモを設定します.<br/>
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    @SuppressLint("DefaultLocale")
    public String toString() {
        return String.format("赤経(α)=[%s], 赤緯(δ)=[%s], 等級=[%6.2f], 名前=[%s]" //
                , StarLocationUtil.convertAngleFloatToString(getRightAscension()) //
                , StarLocationUtil.convertAngleFloatToString(getDeclination())    //
                , magnitude, getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Float.floatToIntBits(rightAscension);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Float.floatToIntBits(declination);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        if (!(obj instanceof Star)) {
            return false;
        }
        Star other = (Star) obj;
        if (Float.floatToIntBits(rightAscension) != Float.floatToIntBits(other.getRightAscension())) {
            return false;
        }
        if (Float.floatToIntBits(declination) != Float.floatToIntBits(other.getDeclination())) {
            return false;
        }
        return true;
    }
}
