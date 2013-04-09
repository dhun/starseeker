/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerRuntimeException;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.DatabaseMeta;
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

    private static final String INIT_ASSETS_DB_FILE = "sql/starseeker-initial.db";  // DBファイルの名前
    private static final String APPLICATION_DB_FILE = "starseeker.db";  // DBファイルの名前
    private static final int DB_VERSION = 1;                // DBのバージョン

    private static final String SQL_FILE_ENCODE = "UTF-8";
    private static final File INITIAL_DATA_DUMP = new File("sql/starseeker-initial.dump");

    public static void setupInitialDatabaseFileIfNotNeed(Context context) {
        if (!needSetupInitialDatabaseFileNeed(context)) {
            return;
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(context.getAssets().open("sql/starseeker-initial.db", Context.MODE_PRIVATE));
            bos = new BufferedOutputStream(new FileOutputStream(context.getDatabasePath(APPLICATION_DB_FILE)));

            byte[] buffer = new byte[1024 * 10];
            int length = 0;
            while (0 != (length = bis.read(buffer))) {
                bos.write(buffer, 0, length);
            }

        } catch (IOException e) {
            throw new StarSeekerRuntimeException("ＤＢファイルの初期化に失敗した.", e);

        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    LogUtils.w(DatabaseHelper.class, "初期ファイルのクローズに失敗した. 処理は継続する", e);
                }
            }
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    LogUtils.w(DatabaseHelper.class, "ＤＢファイルのクローズに失敗した. 処理は継続する", e);
                }
            }
        }
    }

    private static boolean needSetupInitialDatabaseFileNeed(Context context) {
        // DBファイルが存在しなければ、再作成が必要
        if (!context.getDatabasePath(APPLICATION_DB_FILE).exists()) {
            return true;
        }

        // DBファイルがイニシャルファイルより古ければ、再作成が必要
        SQLiteDatabase initializedDb = context.openOrCreateDatabase(INIT_ASSETS_DB_FILE, Context.MODE_PRIVATE, null);
        DatabaseMeta initializedDbMeta = new DatabaseMetaDao(initializedDb).find();

        SQLiteDatabase applicationDb = context.openOrCreateDatabase(APPLICATION_DB_FILE, Context.MODE_PRIVATE, null);
        DatabaseMeta applicationDbMeta = new DatabaseMetaDao(applicationDb).find();

        if (initializedDbMeta.getRegistTimestamp().after(applicationDbMeta.getRegistTimestamp())) {
            context.deleteDatabase(APPLICATION_DB_FILE);
            return true;
        } else {
            return false;
        }
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public DatabaseHelper(Context context) {
        super(context, APPLICATION_DB_FILE, (CursorFactory) null, DB_VERSION);

        if (RECREATE_DB_FILE) {
            context.getDatabasePath(APPLICATION_DB_FILE).delete();
            SQLiteDatabase database = context.openOrCreateDatabase(APPLICATION_DB_FILE, Context.MODE_PRIVATE, null);
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

            database.beginTransaction();

            while (null != (line = bufferedReader.readLine())) {
                line = line.replaceAll("--(.*)$", "/* \1 */");
                sqlbuBuilder.append(line);
                if (line.equals("BEGIN TRANSACTION;")) {
                    // トランザクション処理はスキップ
                    // Caused by: android.database.sqlite.SQLiteException: Can't upgrade read-only database from version 0 to 2: /data/data/jp.gr.java_conf.dhun.starseeker/databases/starseeker.db
                    LogUtils.d(getClass(), "skipped : " + line);
                    sqlbuBuilder.setLength(0);

                } else if (line.equals("COMMIT;")) {
                    // トランザクション処理はスキップ
                    LogUtils.d(getClass(), "skipped : " + line);
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
            database.setTransactionSuccessful();

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
