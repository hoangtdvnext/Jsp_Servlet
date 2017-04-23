package vn.jsp.dao;

import vn.jsp.model.UserDto;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
public interface UserDao {

    UserDto findUser(Connection conn, String username, String password) throws SQLException;

    UserDto findUser(Connection conn, String username) throws SQLException;
}
