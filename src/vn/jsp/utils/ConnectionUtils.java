package vn.jsp.utils;

import vn.jsp.dbconnect.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
public class ConnectionUtils {

    // get connection
    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {

        return DBConnection.getConnection();
    }

    // closed connection
    public static void closedConnect(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {

        }
    }

    // rollback data when crud db fail
    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {

        }
    }
}
