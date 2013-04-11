/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.gr.java_conf.dhun.starseeker.system.exception.StarSeekerRuntimeException;
import jp.gr.java_conf.dhun.starseeker.util.AssetsUtils;
import jp.gr.java_conf.dhun.starseeker.util.LogUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

    private static final String INIT_ASSETS_DB_TIME = "sql/starseeker-initial.timestamp";  // 初期DBのタイムスタンプファイルの名前
    private static final String INIT_ASSETS_DB_FILE = "sql/starseeker-initial.db";  // 初期DBファイルの名前
    private static final String APPLICATION_DB_FILE = "starseeker.db";  // DBファイルの名前
    private static final int DB_VERSION = 1;                // DBのバージョン

    private static final String SQL_FILE_ENCODE = "UTF-8";
    private static final File INITIAL_DATA_DUMP = new File("sql/starseeker-initial.dump");

    private static final File initDbTimestampFile = new File(INIT_ASSETS_DB_TIME);

    private static final DateFormat INIT_DB_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN);

    private static final String PREFERENCES_FILE = DatabaseHelper.class.getSimpleName();
    private static final String PREFERENCES_KEY_CREATE_TIMESTAMP = "CREATE_TIMESTAMP";

    public static void setupInitialDatabaseFileIfNotNeed(Context context) {
        if (!needSetupInitialDatabaseFileNeed(context)) {
            return;
        }

        context.deleteDatabase(APPLICATION_DB_FILE);
        SQLiteDatabase db = context.openOrCreateDatabase(APPLICATION_DB_FILE, Context.MODE_PRIVATE, null);
        db.close();

        // DBファイルを初期DBファイルで上書き
        context.getDatabasePath(APPLICATION_DB_FILE).delete();
        SQLiteDatabase database = context.openOrCreateDatabase(APPLICATION_DB_FILE, Context.MODE_PRIVATE, null);
        executeSqlFileInternal(context, database, INITIAL_DATA_DUMP);
        database.close();

        // InputStream iniDb = null;
        // OutputStream appDb = null;
        // try {
        // iniDb = context.getAssets().open("sql/starseeker-initial.db", Context.MODE_PRIVATE);
        // appDb = new FileOutputStream(context.getDatabasePath(APPLICATION_DB_FILE));
        // FileUtils.copyStream(context, iniDb, appDb);
        //
        // } catch (IOException e) {
        // throw new StarSeekerRuntimeException("DBファイルの作成に失敗した.", e);
        //
        // } finally {
        // FileUtils.closeStreamIgnoreIOException(iniDb);
        // FileUtils.closeStreamIgnoreIOException(appDb);
        // }

        // DBファイルのタイムスタンプを更新
        storeApplicationDbTimestamp(context, new Date());
    }

    private static boolean needSetupInitialDatabaseFileNeed(Context context) {
        // DBファイルが存在しなければ、再作成が必要
        if (!context.getDatabasePath(APPLICATION_DB_FILE).exists()) {
            return true;
        }

        // 初期DBタイムスタンプファイルが存在しなければ、システム障害
        if (!AssetsUtils.exists(context, initDbTimestampFile)) {
            throw new StarSeekerRuntimeException("初期DBタイムスタンプファイルが存在しない. path=[" + initDbTimestampFile + "]");
        }

        // DBファイルのタイムスタンプ＜初期DBファイルのタイムスタンプであれば、再作成が必要
        Date iniDbTimestamp = getInitialDbTimestamp(context);
        Date appDbTimestamp = getApplicationDbTimestamp(context, new Date(0));
        LogUtils.i(DatabaseHelper.class, String.format("iniDbTimestamp=[%s], appDbTimestamp=[%s]" //
                , INIT_DB_TIMESTAMP_FORMAT.format(iniDbTimestamp)
                , INIT_DB_TIMESTAMP_FORMAT.format(appDbTimestamp)));

        return iniDbTimestamp.after(appDbTimestamp);
        //
        // // DBファイルがイニシャルファイルより古ければ、再作成が必要
        // SQLiteDatabase initializedDb = context.openOrCreateDatabase(INIT_ASSETS_DB_FILE, Context.MODE_PRIVATE, null);
        // DatabaseMeta initializedDbMeta = new DatabaseMetaDao(initializedDb).find();
        //
        // SQLiteDatabase applicationDb = context.openOrCreateDatabase(APPLICATION_DB_FILE, Context.MODE_PRIVATE, null);
        // DatabaseMeta applicationDbMeta = new DatabaseMetaDao(applicationDb).find();
        //
        // if (initializedDbMeta.getRegistTimestamp().after(applicationDbMeta.getRegistTimestamp())) {
        // context.deleteDatabase(APPLICATION_DB_FILE);
        // return true;
        // } else {
        // return false;
        // }
    }

    private static Date getInitialDbTimestamp(Context context) {
        InputStream is = null;
        BufferedReader bufferedReader = null;
        String timestamp = null;

        try {
            is = context.getAssets().open(initDbTimestampFile.getPath(), Context.MODE_PRIVATE);
            bufferedReader = new BufferedReader(new InputStreamReader(is, SQL_FILE_ENCODE));
            timestamp = bufferedReader.readLine();
            return INIT_DB_TIMESTAMP_FORMAT.parse(timestamp);

        } catch (IOException e) {
            throw new StarSeekerRuntimeException("初期DBタイムスタンプファイルでI/O例外が発生した. path=[" + initDbTimestampFile + "]", e);

        } catch (ParseException e) {
            throw new StarSeekerRuntimeException("初期DBタイムスタンプファイルの解析に失敗した. path=[" + initDbTimestampFile + "]", e);

        } catch (NullPointerException e) {
            throw new StarSeekerRuntimeException("初期DBタイムスタンプファイルにタイムスタンプ文字列が設定されていない. path=[" + initDbTimestampFile + "]", e);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtils.w(DatabaseHelper.class, "初期DBのタイムスタンプファイルのクローズに失敗した. 処理は継続する. path=[" + initDbTimestampFile.getPath() + "]");
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LogUtils.w(DatabaseHelper.class, "初期DBのタイムスタンプファイルのクローズに失敗した. 処理は継続する. path=[" + initDbTimestampFile.getPath() + "]");
                }
            }
        }
    }

    private static Date getApplicationDbTimestamp(Context context, Date defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return new Date(sharedPreferences.getLong(PREFERENCES_KEY_CREATE_TIMESTAMP, defaultValue.getTime()));
    }

    private static void storeApplicationDbTimestamp(Context context, Date value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(PREFERENCES_KEY_CREATE_TIMESTAMP, value.getTime());
        editor.commit();
    }

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public DatabaseHelper(Context context) {
        super(context, APPLICATION_DB_FILE, (CursorFactory) null, DB_VERSION);

        // if (RECREATE_DB_FILE) {
        // context.getDatabasePath(APPLICATION_DB_FILE).delete();
        // SQLiteDatabase database = context.openOrCreateDatabase(APPLICATION_DB_FILE, Context.MODE_PRIVATE, null);
        // executeSqlFileIfNewer(context, database, INITIAL_DATA_DUMP);
        // database.close();
        // }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        int i = 0;
        i = i + 1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        int i = 0;
        i = i + 1;
    }

    // /**
    // * 指定されたSQLファイルを実行します.<br/>
    // * ただしSQLファイルのタイムスタンプが前回実行したときから更新されていなければ何もしません.<br/>
    // * としたかったけど、<b>Assetリソースのタイムスタンプが取得できない...</b>
    // *
    // * @param context Androidコンテキスト
    // * @param database データベース
    // * @param sqlFile SQLファイル
    // */
    // protected void executeSqlFileIfNewer(Context context, SQLiteDatabase database, File sqlFile) {
    // System.out.println("---- executeSqlFileIfNewer: " + sqlFile + " ----");
    //
    // if (!AssetsUtils.exists(context, sqlFile)) {
    // return;
    // }
    //
    // executeSqlFileInternal(context, database, sqlFile);
    // }
    //
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

    protected static void executeSqlFileInternal(Context context, SQLiteDatabase database, File sqlFile) {
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
                    LogUtils.d(DatabaseHelper.class, "skipped : " + line);
                    sqlbuBuilder.setLength(0);

                } else if (line.equals("COMMIT;")) {
                    // トランザクション処理はスキップ
                    LogUtils.d(DatabaseHelper.class, "skipped : " + line);
                    sqlbuBuilder.setLength(0);

                } else if (line.contains(";")) {
                    String sql = sqlbuBuilder.toString();
                    sql = sql.replaceAll("\r\n", "");
                    sql = sql.replaceAll("\r", "");
                    sql = sql.replaceAll("\n", "");

                    LogUtils.d(DatabaseHelper.class, sql);
                    database.execSQL(sql);
                    sqlbuBuilder.setLength(0);
                }
            }
            database.setTransactionSuccessful();

        } catch (IOException e) {
            LogUtils.e(DatabaseHelper.class, "SQLファイルの実行で例外が発生しました. path=[" + sqlFile.getPath() + "]", e);
            throw new StarSeekerRuntimeException(e);

        } finally {
            database.endTransaction();

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtils.w(DatabaseHelper.class, "SQLファイルのクローズに失敗した. 処理は継続する. path=[" + sqlFile.getPath() + "]");
                }
            }
        }
    }
}
