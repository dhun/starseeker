/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerRuntimeException;
import jp.gr.java_conf.dhun.starseeker.util.AssetsUtils;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベースヘルパー.<bbr/>
 * 
 * @author jun
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /** DBファイルを再作成するかどうか. trueにすると「starseeker-initial.dump」の内容で初期化します */
    private static final boolean RECREATE_DB_FILE = false;

    private static final String DB_FILE = "starseeker.db";  // DBファイルの名前
    private static final int DB_VERSION = 1;                // DBのバージョン

    private static final String SQL_FILE_ENCODE = "UTF-8";
    private static final File INITIAL_DATA_DUMP = new File("sql/starseeker-initial.dump");

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public DatabaseHelper(Context context) {
        super(context, DB_FILE, (CursorFactory) null, DB_VERSION);

        if (RECREATE_DB_FILE) {
            context.getDatabasePath(DB_FILE).delete();
            SQLiteDatabase database = context.openOrCreateDatabase(DB_FILE, Context.MODE_PRIVATE, null);
            executeSqlFileIfNewer(context, database, INITIAL_DATA_DUMP);
            database.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }

    /**
     * 指定されたSQLファイルを実行します.<br/>
     * ただしSQLファイルのタイムスタンプが前回実行したときから更新されていなければ何もしません.<br/>
     * としたかったけど、<b>Assetリソースのタイムスタンプが取得できない...</b>
     * 
     * @param context Androidコンテキスト
     * @param database データベース
     * @param sqlFile SQLファイル
     */
    protected void executeSqlFileIfNewer(Context context, SQLiteDatabase database, File sqlFile) {
        System.out.println("---- executeSqlFileIfNewer: " + sqlFile + " ----");

        if (!AssetsUtils.exists(context, sqlFile)) {
            return;
        }

        executeSqlFileInternal(context, database, sqlFile);
    }

    /**
     * 指定されたSQLファイルを実行します.<br/>
     * 
     * @param context Androidコンテキスト
     * @param database データベース
     * @param sqlFile SQLファイル
     */
    protected void executeSqlFile(Context context, SQLiteDatabase database, File sqlFile) {
        System.out.println("---- executeSqlFile: " + sqlFile + " ----");

        if (!AssetsUtils.exists(context, sqlFile)) {
            return;
        }

        executeSqlFileInternal(context, database, sqlFile);
    }

    protected void executeSqlFileInternal(Context context, SQLiteDatabase database, File sqlFile) {
        InputStream is = null;
        try {
            is = context.getAssets().open(sqlFile.getPath(), Context.MODE_PRIVATE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, SQL_FILE_ENCODE));

            StringBuilder sqlbuBuilder = new StringBuilder();
            String line;

            while (null != (line = bufferedReader.readLine())) {
                line = line.replaceAll("--(.*)$", "/* \1 */");
                sqlbuBuilder.append(line);
                if (line.equals("BEGIN TRANSACTION;")) {
                    // トランザクションの開始をDCLで実行すると、次のエラーが発生した
                    // Caused by: android.database.sqlite.SQLiteException: Can't upgrade read-only database from version 0 to 2: /data/data/jp.gr.java_conf.dhun.starseeker/databases/starseeker.db
                    LogUtils.d(getClass(), line);
                    database.beginTransaction();
                    sqlbuBuilder.setLength(0);

                } else if (line.equals("COMMIT;")) {
                    // コミットはDCLのままでも例外は発生しなかったが、トランザクション開始をメソッド呼び出しにしたためこちらも併せた.
                    LogUtils.d(getClass(), line);
                    database.setTransactionSuccessful();
                    sqlbuBuilder.setLength(0);

                } else if (line.contains(";")) {
                    String sql = sqlbuBuilder.toString();
                    sql = sql.replaceAll("\r\n", "");
                    sql = sql.replaceAll("\r", "");
                    sql = sql.replaceAll("\n", "");

                    LogUtils.d(getClass(), sql);
                    database.execSQL(sql);
                    sqlbuBuilder.setLength(0);
                }
            }

        } catch (IOException e) {
            LogUtils.e(getClass(), "SQLファイルの実行で例外が発生しました. path=[" + sqlFile.getPath() + "]", e);
            throw new StarSeekerRuntimeException(e);

        } finally {
            database.endTransaction();

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
