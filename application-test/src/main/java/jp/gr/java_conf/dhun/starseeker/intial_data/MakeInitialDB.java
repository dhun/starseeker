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
import java.util.Arrays;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * @author jun
 * 
 */
public class MakeInitialDB {

    private static final String SQLITE_PATH = "D:/dev/_opt/sqlite3/sqlite3.exe";
    private static final String SQL_ROOT_DIR = "initial_data";

    private final File databaseFile = new File("initial_data", "starseeker.db");
    private final File databaseDump = new File("initial_data", "starseeker.dump");

    public static void main(String[] args) {
        MakeInitialDB instance = new MakeInitialDB();
        instance.make();
    }

    public void make() {
        try {
            System.out.println("begin,");

            Class.forName("org.sqlite.JDBC");

            if (databaseFile.exists()) {
                databaseFile.delete();
            }

            executeSqlFiles(SQL_ROOT_DIR + File.separator + "original_data");
            executeSqlFiles(SQL_ROOT_DIR + File.separator + "convert_starseeker_database");

            executeConvertSqlStatements();

            dropTables();

            dumpDatabase();

            System.out.println("normal end,");

        } catch (Throwable t) {
            if (databaseFile.exists()) {
                databaseFile.delete();
            }
            System.out.println("abnormal end,");
            throw new RuntimeException(t);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
    }

    private void executeSqlFiles(String dirPath) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            throw new IOException("no such directory. path=[" + dirPath + "]");
        }

        File[] sqlFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".sql");
            }
        });

        Arrays.sort(sqlFiles);

        for (File sqlFile : sqlFiles) {
            executeSqlFile(sqlFile);
        }
    }

    private int executeSqlFile(File sqlFile) throws IOException, InterruptedException, SQLException {
        System.out.println("---- executeSqlFile: " + sqlFile.getPath() + " ----");

        ProcessBuilder builder;
        builder = new ProcessBuilder("cmd", "/c", SQLITE_PATH, "-batch", "-bail", "-echo", databaseFile.getAbsolutePath(), "<", sqlFile.getPath());
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
                throw new SQLException("SQLファイルの実行中に例外が発生した");
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
            // create a database connection
            connection = getConnection();
            connection.setAutoCommit(false);

            convertStarData(connection);
            convertHoroscopeData(connection);

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

    private void convertHoroscopeData(Connection connection) throws SQLException {
        System.out.println("---- convert : horoscope_data ----");

        Statement select = connection.createStatement();
        PreparedStatement update = connection.prepareStatement("update horoscope_data set right_ascension=?, declination=? where horoscope_id=?");
        ResultSet rs = select.executeQuery("select * from horoscope_data");
        while (rs.next()) {
            float right_ascension = StarLocationUtil.convertHourStringToFloat(rs.getString("right_ascension"));
            float declination = StarLocationUtil.convertAngleStringToFloat(rs.getString("declination"));

            update.setFloat(1, right_ascension);
            update.setFloat(2, declination);
            update.setInt(3, rs.getInt("horoscope_id"));
            update.execute();
        }
    }

    private void dropTables() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            // create a database connection
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

    private void dumpDatabase() throws IOException, InterruptedException, SQLException {
        System.out.println("---- dump database ----");

        ProcessBuilder builder;
        builder = new ProcessBuilder("cmd", "/c", SQLITE_PATH, "-batch", "-bail", databaseFile.getAbsolutePath(), ".dump", ">", databaseDump.getAbsolutePath());
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
                throw new SQLException("データベースファイルのダンプ中に例外が発生した");
            }
            return;

        } finally {
            if (standardIn != null) {
                standardIn.close();
            }
        }
    }
}
