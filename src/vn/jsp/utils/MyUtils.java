package vn.jsp.utils;

import vn.jsp.model.UserDto;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
public class MyUtils {

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

    public static final String ATT_NAME_USERNAME = "ATTRIBUTE_FOR_USERNAME";

    // lưu trữ connection vào một thuộc tính request
    // thông tin được lưu trữ chỉ tồn tại trong thời gian yêu cầu req
    // cho tới khi dữ liệu trả về người dùng
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }

    //get connection in request attribute
    public static Connection getStoreConnection(ServletRequest request) {
        return (Connection) request.getAttribute(ATT_NAME_CONNECTION);
    }

    // set user to session
    public static void setLoginUser(HttpSession session, UserDto userDto) {
        session.setAttribute(ATT_NAME_USERNAME, userDto);
    }

    public static UserDto getLoginUser(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(ATT_NAME_USERNAME);

        return userDto;
    }

    // save data user to cookie
    public static void storeUserCookie(HttpServletResponse response, UserDto userDto) {
        Cookie cookieUsername = new Cookie(ATT_NAME_USERNAME, userDto.getUsername());
        // set remember 1 day
        cookieUsername.setMaxAge(24 * 60 * 60);

        response.addCookie(cookieUsername);
    }

    // get username cookie
    public static String getUsernameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USERNAME.equals(cookie.getName())) {
                    // get value: username by key
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    // delete cookie user using data
    public static void deleteCookie(HttpServletResponse response) {
        Cookie cookieUsername = new Cookie(ATT_NAME_USERNAME, null);
        // set max age 0
        cookieUsername.setMaxAge(0);
        //replace cookie
        response.addCookie(cookieUsername);
    }
}
