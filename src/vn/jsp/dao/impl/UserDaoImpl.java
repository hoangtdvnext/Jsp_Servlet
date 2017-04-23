package vn.jsp.dao.impl;

import vn.jsp.dao.UserDao;
import vn.jsp.model.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public UserDto findUser(Connection conn, String username, String password) throws SQLException {
        String sql = "Select a.username, a.password from cms_user a "
                + " where a.username = ? and a.password= ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, username);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            UserDto user = new UserDto();
            user.setUsername(username);
            user.setPassword(password);

            return user;
        }
        return null;
    }

    @Override
    public UserDto findUser(Connection conn, String username) throws SQLException {
        String sql = "Select a.username, a.password from cms_user a " + " where a.username = ? ";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, username);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String password = rs.getString("password");

            UserDto user = new UserDto();
            user.setUsername(username);
            user.setPassword(password);

            return user;
        }
        return null;
    }
}
