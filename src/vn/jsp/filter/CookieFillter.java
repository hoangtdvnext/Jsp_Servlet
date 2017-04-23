package vn.jsp.filter;

import vn.jsp.dao.UserDao;
import vn.jsp.dao.impl.UserDaoImpl;
import vn.jsp.model.UserDto;
import vn.jsp.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
//JDBCFilter & CookieFilter có cùng url-pattern =/*, bạn cần phải cấu hình để đảm bảo rằng JDBCFilter được thực thi trước.
// Do đó cần phải khai báo thứ tự trong web.xml (Không có cách nào khai báo thứ tự bằng Annotation).
@WebFilter(filterName = "cookieFilter", urlPatterns = {"/*"})
public class CookieFillter implements Filter {
    UserDao userDao;

    public CookieFillter() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        UserDto userDto = MyUtils.getLoginUser(session);
        // dang login
        if (userDto != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //da duoc tao trong jdbcfilter
        Connection conn = MyUtils.getStoreConnection(servletRequest);

        // co can kiem tra cookie khong
        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        userDao = new UserDaoImpl();
        if (checked == null && conn != null) {
            String username = MyUtils.getUsernameInCookie(request);
            try {
                userDto = userDao.findUser(conn, username);
                // set user to session
                MyUtils.setLoginUser(session, userDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // chuyen tiep
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
