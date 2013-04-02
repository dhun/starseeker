/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;

/**
 * @author jun
 * 
 */
public class MakeInitialDB {

    /**
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:tmp\\starseeker.db");
            convertStarData(connection);

            // Statement statement = connection.createStatement();
            // statement.setQueryTimeout(30); // set timeout to 30 sec.
            //
            // statement.executeUpdate("drop table if exists person");
            // statement.executeUpdate("create table person (id integer, name string)");
            // statement.executeUpdate("insert into person values(1, 'leo')");
            // statement.executeUpdate("insert into person values(2, 'yui')");
            // ResultSet rs = statement.executeQuery("select * from person");
            // while (rs.next()) {
            // // read the result set
            // System.out.println("name = " + rs.getString("name"));
            // System.out.println("id = " + rs.getInt("id"));
            // }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    private static void convertStarData(Connection connection) throws SQLException {
        Statement select = connection.createStatement();
        PreparedStatement update = connection.prepareStatement("update star_data set right_ascension=?, declination=? where hip_num=?");
        ResultSet rs = select.executeQuery("select * from star_data");
        while (rs.next()) {
            float right_ascension = StarLocationUtil.convertHourStringToFloat(rs.getString("right_ascension"));
            float declination = StarLocationUtil.convertHourStringToFloat(rs.getString("declination"));

            update.setFloat(0, right_ascension);
            update.setFloat(1, declination);
            update.setInt(2, rs.getInt("hip_num"));
            update.execute();
        }
    }
}
