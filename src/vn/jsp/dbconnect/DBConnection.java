package vn.jsp.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
public class DBConnection {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // hostName, port, username, password, dbName width mysql
        String username = "hoangtd";
        String password = "123456";
        String dbName = "cms_dashboard";
        String hostName = "localhost";
        Integer port = 3306;

        return getMysqlConnection(hostName, port, dbName, username, password);
    }

    public static Connection getMysqlConnection(String hostName, Integer port, String dbName, String username, String password)
            throws SQLException, ClassNotFoundException {
        //class driver sql
        Class.forName("com.mysql.jdbc.Driver");

        // url connection = jdbc:mysql://localhost:3306/dbName?characterEncoding=utf-8
        String connectionUrl = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
        Connection conn = DriverManager.getConnection(connectionUrl, username, password);

        // get connection
        return conn;
    }
}
