/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.entity;

import java.util.Date;

/**
 * DB作成日時のエンティティ.<br/>
 * 
 * @author jun
 * 
 */
public class DatabaseMeta {

    public static final String TABLE_NAME = "database_info";

    public static class FieldNames {
        public static final String ID = "id";
        public static final String TIMESTAMP = "timestamp";

        public static final String[] ALL_COLUMNS = { TIMESTAMP };
    }

    private final Integer id = 0;   // ID
    private Date registTimestamp;   // 作成日時

    /**
     * デフォルト・コンストラクタ
     */
    public DatabaseMeta() {
    }

    /**
     * IDを取得します.<br/>
     */
    public Integer getId() {
        return id;
    }

    /**
     * 作成日時を取得します.<br/>
     */
    public Date getRegistTimestamp() {
        return registTimestamp;
    }

    /**
     * 作成日時を設定します.<br/>
     */
    public void setRegistTimestamp(Date registTimestamp) {
        this.registTimestamp = registTimestamp;
    }
}
