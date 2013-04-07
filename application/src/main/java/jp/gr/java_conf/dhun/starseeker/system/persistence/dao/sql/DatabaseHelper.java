/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerRuntimeException;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author jun
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_FILE = "starseeker.db";
    private static final int DB_VERSION = 2;

    private static final File INITIAL_DATA_DUMP = new File("sql/starseeker-initial.dump");

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_FILE, (CursorFactory) null, DB_VERSION);
        this.context = context;

        context.getDatabasePath(DB_FILE).delete();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        executeSqlFile(context, database, INITIAL_DATA_DUMP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onCreate(database);
    }

    private void executeSqlFile(Context context, SQLiteDatabase database, File sqlFile) {
        System.out.println("---- executeSqlFile: " + sqlFile + " ----");

        InputStream is = null;
        try {
            String[] files = context.getAssets().list(sqlFile.getParent());
            if (!Arrays.asList(files).contains(sqlFile.getName())) {
                LogUtils.w(getClass(), "SQLファイルが見つかりません. path=[" + sqlFile.getPath() + "]");
                return;
            }

            is = context.getAssets().open(sqlFile.getPath(), Context.MODE_PRIVATE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sqlbuBuilder = new StringBuilder();
            String line;

            while (null != (line = bufferedReader.readLine())) {
                line = line.replaceAll("--(.*)$", "/* \1 */");
                sqlbuBuilder.append(line);
                if (line.equals("BEGIN TRANSACTION;")) {
                    database.beginTransaction();
                    sqlbuBuilder.setLength(0);
                } else if (line.contains(";")) {
                    String sql = sqlbuBuilder.toString();
                    sql = sql.replaceAll("\r\n", "");
                    sql = sql.replaceAll("\r", "");
                    sql = sql.replaceAll("\n", "");
                    database.execSQL(sql);
                    sqlbuBuilder.setLength(0);
                }
            }

        } catch (IOException e) {
            LogUtils.e(getClass(), "SQLファイルの実行で例外が発生しました. path=[" + sqlFile.getPath() + "]", e);
            throw new StarSeekerRuntimeException(e);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtils.w(getClass(), "SQLファイルのクローズに失敗した. 処理は継続する. path=[" + sqlFile.getPath() + "]");
                }
            }
        }
    }
}
