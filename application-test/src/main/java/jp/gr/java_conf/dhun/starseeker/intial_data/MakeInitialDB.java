/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.intial_data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    private File starseekerDatabase;

    public static void main(String[] args) {
        MakeInitialDB instance = new MakeInitialDB();
        instance.make();
    }

    public void make() {
        try {
            Class.forName("org.sqlite.JDBC");
            starseekerDatabase = new File("tmp" + File.separator + "starseeker.db");

            executeSqlFiles("initial_data" + File.separator + "original_data");
            executeConvertSqlStatements();

            // convertStarData(connection);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
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

    private int executeSqlFile(File sqlFile) throws IOException, InterruptedException {
        BufferedInputStream sqlInputStream = null;
        try {
            sqlInputStream = new BufferedInputStream(new FileInputStream(sqlFile));

            ProcessBuilder builder = new ProcessBuilder(SQLITE_PATH, starseekerDatabase.getAbsolutePath());
            Process process = builder.start();
            OutputStream defaultOs = process.getOutputStream();
            InputStream defaultIs = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(defaultIs));

            // byte[] buf = new byte[1024];
            // int len;
            // while (-1 != (len = sqlInputStream.read(buf))) {
            // defaultOs.write(buf, 0, len);
            // // for (;;) {
            // // String line = br.readLine();
            // // if (line == null) {
            // // break;
            // // }
            // // System.out.println(line);
            // // }
            // }

            int result = process.waitFor();
            return result;

        } finally {
            if (sqlInputStream != null) {
                sqlInputStream.close();
            }
        }
    }

    private void executeConvertSqlStatements() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:tmp\\starseeker.db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + starseekerDatabase.getPath());
            connection.setAutoCommit(false);

            convertStarData(connection);

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

            System.out.println(rs.getString("hip_num"));
        }
    }
}
