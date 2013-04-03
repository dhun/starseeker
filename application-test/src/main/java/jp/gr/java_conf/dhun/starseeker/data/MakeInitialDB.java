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
            connection.setAutoCommit(false);

            convertStarData(connection);

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException eRollback) {
                System.out.println(eRollback);
            }
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
            float declination = StarLocationUtil.convertAngleStringToFloat(rs.getString("declination"));

            update.setFloat(1, right_ascension);
            update.setFloat(2, declination);
            update.setInt(3, rs.getInt("hip_num"));
            update.execute();

            System.out.println(rs.getString("hip_num"));
        }
    }
}
