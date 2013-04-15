/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.intial_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import jp.gr.java_conf.dhun.starseeker.util.FileUtils;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * DB初期化用のダンプファイルを生成するプログラム.<br/>
 * 次の問題点が残っています.<br/>
 * <ul>
 * <li>200-wikipedia_star_name.sql
 * <ul>
 * <li>'γ Cas', 'ツィー'のHIPを-1にねつ造している</li>
 * </ul>
 * </li>
 * <li>210-wikipedia_constellation.sql
 * <ul>
 * <li>'γ Cas', 'ツィー'のHIPを-1にねつ造している</li>
 * <li>おうし座のパスがあやしい</li>
 * </ul>
 * </li>
 * <li>300-nasa_star_data.sql
 * <ul>
 * <li>'γ Cas', 'ツィー'をHIPで-1にねつ造している</li>
 * </ul>
 * </li>
 * <li>130-hoshizora_constellation_name.sql(星座名データ)
 * <ul>
 * <li>horoscope_code='Ser'が重複しており89行目をコメントアウトしているため「'Serpens(Cauda)', 'へび(尾)'」が未定義</li>
 * </ul>
 * </li>
 * <li>110-hoshizora_fk_name.sql(恒星名データ) <b>※このデータは使ってないので悪影響はなし</b>
 * <ul>
 * <li>horoscope_code='Ser'が重複しているため、へび座を構成する星の見分けがつかない</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author jun
 * 
 */
public class MakeInitialDB {

    private static final boolean RECREATE_CONSTELLATION_ONLY = true;     // trueにすると、星座パスの再生性だけを行います

    private static final boolean COPY_TO_APPLICATION_IF_SUCCEED = true;  // trueにすると、初期DBダンプの生成に成功したとき、アプリケーションプロジェクトにコピーする
    private static final boolean REMOVE_TMP_DATABASE_IF_SUCCEED = true;  // trueにすると、初期DBダンプの生成に成功したとき、一時DBを削除する
    private static final boolean REMOVE_TMP_DATABASE_IF_FAILURE = false; // trueにすると、初期DBダンプの生成に失敗したとき、一時DBを削除する

    private static final String SQLITE_PATH_WIN = "D:/dev/_opt/sqlite3/sqlite3.exe";
    private static final String SQLITE_PATH_MAC = "sqlite3";

    private static final String APPLICATION_ASSET_DIR = "../application/assets/sql";

    private static final File ROOT_DIR = new File("initial_data");

    private static final File TMP_DATABASE_FILE = new File(ROOT_DIR, "starseeker.db");
    private static final File INI_DATABASE_DUMP = new File(ROOT_DIR, "starseeker-initial.dump");
    private static final File INI_DATABASE_TIME = new File(ROOT_DIR, "starseeker-initial.timestamp");

    private static final DateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {
        MakeInitialDB instance = new MakeInitialDB();
        instance.make();
    }

    public void make() {
        try {
            System.out.println("begin.");

            Class.forName("org.sqlite.JDBC");
            validateExistsSqlite();

            // 出力ファイルを初期化
            if (!RECREATE_CONSTELLATION_ONLY) {
                FileUtils.delete(TMP_DATABASE_FILE);
                FileUtils.delete(INI_DATABASE_DUMP);
                FileUtils.delete(INI_DATABASE_TIME);

                // SQLスクリプトを実行して一時的なデータベースを作成
                executeSqlFiles(ROOT_DIR + File.separator + "original_data");
                executeSqlFiles(ROOT_DIR + File.separator + "convert_starseeker_database");
            } else {
                // SQLスクリプトを実行して一時的なデータベースを作成
                executeSqlFiles(ROOT_DIR + File.separator + "original_data", Arrays.asList(new String[] { "210-wikipedia_constellation.sql" }));
                executeSqlFiles(ROOT_DIR + File.separator + "convert_starseeker_database");
            }

            // データの補正
            executeConvertSqlStatements();

            // 不要なテーブルを削除
            boolean dropNotUsed = false;
            if (dropNotUsed) {
                dropTables();
            }

            // 初期DBに必要なテーブルをダンプ
            dumpTables();

            // 初期DBのタイムスタンプファイルを作成
            createTimestampFile();

            // 一時的なデータベースを削除
            // if (REMOVE_TMP_DATABASE_IF_SUCCEED ) {
            // FileUtils.delete(TMP_DATABASE_FILE);
            // }

            // アプリケーションプロジェクトにダンプファイルをコピー
            if (COPY_TO_APPLICATION_IF_SUCCEED) {
                File dstDir = new File(APPLICATION_ASSET_DIR);
                FileUtils.copyFile(INI_DATABASE_DUMP, new File(dstDir, INI_DATABASE_DUMP.getName()));
                FileUtils.copyFile(INI_DATABASE_TIME, new File(dstDir, INI_DATABASE_TIME.getName()));
            }

            System.out.println("");
            System.out.println("normal end.");
            System.out.println("");

            StringBuilder message = new StringBuilder();
            message.append("次の問題点が残っています.");
            message.append("\n");
            message.append("\n・200-wikipedia_star_name.sql");
            message.append("\n    ・'γ Cas', 'ツィー'のHIPを-1にねつ造している");
            message.append("\n・210-wikipedia_constellation.sql");
            message.append("\n    ・'γ Cas', 'ツィー'のHIPを-1にねつ造している");
            message.append("\n    ・おうし座のパスがあやしい");
            message.append("\n・300-nasa_star_data.sql");
            message.append("\n    ・'γ Cas', 'ツィー'をHIPで-1にねつ造している");
            message.append("\n・130-hoshizora_constellation_name.sql(星座名データ)");
            message.append("\n    ・horoscope_code='Ser'が重複しており89行目をコメントアウトしているため「'Serpens(Cauda)', 'へび(尾)'」が未定義");
            message.append("\n・110-hoshizora_fk_name.sql(恒星名データ)  ※このデータは使ってないので悪影響はなし");
            message.append("\n    ・horoscope_code='Ser'が重複しているため、へび座を構成する星の見分けがつかない");
            message.append("\n");
            System.out.print(message.toString());

        } catch (Throwable t) {
            if (!RECREATE_CONSTELLATION_ONLY) {
                if (REMOVE_TMP_DATABASE_IF_FAILURE) {
                    // FileUtils.delete(TMP_DATABASE_FILE);
                    FileUtils.delete(INI_DATABASE_DUMP);
                }
            }
            FileUtils.delete(INI_DATABASE_TIME);

            System.out.println("abnormal end.");
            throw new RuntimeException(t);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + TMP_DATABASE_FILE.getPath());
    }

    private void executeSqlFiles(String dirPath) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
        executeSqlFiles(dirPath, Arrays.asList(new String[] {}));
    }

    private void executeSqlFiles(String dirPath, final List<String> includeFiles) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            throw new IOException("no such directory. path=[" + dirPath + "]");
        }

        File[] sqlFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".sql")) {
                    if (includeFiles.isEmpty()) {
                        return true;
                    } else {
                        return includeFiles.contains(name);
                    }
                } else {
                    return false;
                }
            }
        });

        Arrays.sort(sqlFiles);

        for (File sqlFile : sqlFiles) {
            executeSqlFile(sqlFile);
        }
    }

    private boolean isWindows() {
        Pattern pattern = Pattern.compile("windows", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(System.getProperty("os.name")).find();
    }

    private void validateExistsSqlite() throws IOException, InterruptedException {
        // SQLITEのコマンドラインを構築
        List<String> commands = new ArrayList<String>();
        commands.add(SQLITE_PATH_MAC);
        commands.add("--version");

        // SQLITEを利用したダンプ処理
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        int result = process.waitFor();
        if (result != 0) {
            throw new RuntimeException("SQLITEが見つからない. path=[" + SQLITE_PATH_MAC + "]");
        }
    }

    private int executeSqlFile(File sqlFile) throws IOException, InterruptedException {
        System.out.println("---- executeSqlFile: " + sqlFile.getPath() + " ----");

        // SQLITEのコマンドラインを構築
        List<String> commands = new ArrayList<String>();
        if (isWindows()) {
            // builder = new ProcessBuilder("cmd", "/c", SQLITE_PATH_WIN, "-batch", "-bail", TMP_DATABASE_FILE.getAbsolutePath(), ".dump " + tableName, ">>", databaseDump.getAbsolutePath());
            commands.add("cmd");
            commands.add("/c");
            commands.add(SQLITE_PATH_WIN);
            commands.add("-batch");
            commands.add("-bail");
            commands.add("-echo");
            commands.add(TMP_DATABASE_FILE.getAbsolutePath());
            commands.add("<");
            commands.add(sqlFile.getPath());
        } else {
            // builder = new ProcessBuilder(SQLITE_PATH_MAC, "-batch", "-bail", "-echo", TMP_DATABASE_FILE.getAbsolutePath(), ".read " + sqlFile.getPath());
            commands.add(SQLITE_PATH_MAC);
            commands.add("-batch");
            commands.add("-bail");
            commands.add("-echo");
            commands.add(TMP_DATABASE_FILE.getAbsolutePath());
            commands.add(".read " + sqlFile.getPath());
        }

        // SQLITEを利用したSQLスクリプトファイルの実行
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        BufferedReader standardIn = null;
        try {
            standardIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;
            while (null != (line = standardIn.readLine())) {
                System.out.println(line);
            }
            int result = process.waitFor();
            if (result != 0) {
                throw new RuntimeException("SQLファイルの実行中に例外が発生した");
            }
            return result;

        } finally {
            if (standardIn != null) {
                standardIn.close();
            }
        }
    }

    private void executeConvertSqlStatements() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            convertStarData(connection);
            convertConstellationData(connection);

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void convertStarData(Connection connection) throws SQLException {
        System.out.println("---- convert : star_data ----");

        Statement select = connection.createStatement();
        PreparedStatement update = connection.prepareStatement("update star_data set right_ascension=?, declination=? where hip_num=?");
        ResultSet rs = select.executeQuery("select * from star_data");
        while (rs.next()) {
            float right_ascension = StarLocationUtil.convertHourStringToFloat(rs.getString("right_ascension"));
            float declination = StarLocationUtil.convertAngleStringToFloat(rs.getString("declination"));

            update.setFloat(1, right_ascension);
            update.setFloat(2, declination);
            update.setInt(3, rs.getInt("hip_num"));
            update.execute();
        }
    }

    private void convertConstellationData(Connection connection) throws SQLException {
        System.out.println("---- convert : constellation_data ----");

        Statement select = connection.createStatement();
        PreparedStatement update = connection.prepareStatement("update constellation_data set right_ascension=?, declination=? where constellation_code=?");
        ResultSet rs = select.executeQuery("select * from constellation_data");
        while (rs.next()) {
            float right_ascension = StarLocationUtil.convertHourStringToFloat(rs.getString("right_ascension"));
            float declination = StarLocationUtil.convertAngleStringToFloat(rs.getString("declination"));

            update.setFloat(1, right_ascension);
            update.setFloat(2, declination);
            update.setString(3, rs.getString("constellation_code"));
            update.execute();
        }
    }

    private void dropTables() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            // connection.setAutoCommit(false);

            dropTable(connection, "constellation");
            dropTable(connection, "constellation_name");
            dropTable(connection, "custom_name");
            dropTable(connection, "fk_data");
            dropTable(connection, "fk_name");

            // connection.commit();

        } catch (SQLException e) {
            // connection.rollback();
            throw e;

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void dropTable(Connection connection, String tableName) throws SQLException {
        System.out.println("---- drop table : " + tableName + " ----");

        PreparedStatement statement = connection.prepareStatement("drop table " + tableName);
        statement.execute();
    }

    private void dumpTables() throws IOException, InterruptedException {
        List<File> dumpFiles = new ArrayList<File>();

        try {
            dumpFiles.add(dumpTable("star_data"));
            dumpFiles.add(dumpTable("constellation_data"));
            dumpFiles.add(dumpTable("constellation_path"));

            FileUtils.delete(INI_DATABASE_DUMP);
            for (File dumpFile : dumpFiles) {
                FileUtils.copyFile(dumpFile, INI_DATABASE_DUMP, true/* append */);
            }

        } finally {
            for (File dumpFile : dumpFiles) {
                FileUtils.delete(dumpFile);
            }
        }
    }

    private File dumpTable(String tableName) throws IOException, InterruptedException {
        System.out.println("---- dump table : " + tableName + " ----");

        // ダンプファイルを作成
        File dumpFile = File.createTempFile("dump", ".dmp", ROOT_DIR);

        // 設定ファイルを作成
        List<String> settings = new ArrayList<String>();
        settings.add(".output " + dumpFile.getAbsolutePath());

        File iniFile = FileUtils.createTempFileAndWriteLines(ROOT_DIR, settings);

        // SQLITEのコマンドラインを構築
        List<String> commands = new ArrayList<String>();
        if (isWindows()) {
            // builder = new ProcessBuilder("cmd", "/c", SQLITE_PATH_WIN, "-batch", "-bail", TMP_DATABASE_FILE.getAbsolutePath(), ".dump " + tableName, ">>", databaseDump.getAbsolutePath());
            commands.add("cmd");
            commands.add("/");
            commands.add(SQLITE_PATH_WIN);
            commands.add("-batch");
            commands.add("-bail");
            // commands.add("-init");
            // commands.add(iniFile.getPath());
            commands.add(TMP_DATABASE_FILE.getAbsolutePath());
            commands.add(".dump " + tableName);
            commands.add(">>");
            commands.add(INI_DATABASE_DUMP.getAbsolutePath());
        } else {
            commands.add(SQLITE_PATH_MAC);
            commands.add("-batch");
            commands.add("-bail");
            commands.add("-init");
            commands.add(iniFile.getPath());
            commands.add(TMP_DATABASE_FILE.getAbsolutePath());
            commands.add(".dump " + tableName);
        }

        // SQLITEを利用したダンプ処理
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        BufferedReader standardIn = null;
        try {
            standardIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;
            while (null != (line = standardIn.readLine())) {
                System.out.println(line);
            }
            int result = process.waitFor();
            if (result != 0) {
                throw new RuntimeException("データベースファイルのダンプ中に例外が発生した");
            }
            return dumpFile;

        } catch (IOException e) {
            FileUtils.delete(dumpFile);
            throw e;

        } catch (InterruptedException e) {
            FileUtils.delete(dumpFile);
            throw e;

        } finally {
            FileUtils.delete(iniFile);
            FileUtils.closeIgnoreIOException(standardIn);
        }
    }

    private static void createTimestampFile() throws IOException {
        String timestamp = timestampFormat.format(new Date());
        FileUtils.writeLines(INI_DATABASE_TIME, Arrays.asList(timestamp));
    }
}
