package vn.jsp.filter;

import vn.jsp.utils.ConnectionUtils;
import vn.jsp.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Hoang Ta Duy on 4/23/2017.
 */
@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {
    public JDBCFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // Kiểm tra xem request hiện tại là 1 Servlet?
    private boolean needJDBC(HttpServletRequest request) {
        // get path
        String servletPath = request.getServletPath();

        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }

        //key value servlet
        //value: servletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();

        // tập hợp tất cả các servlet trong webapp của bạn
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        //
        // Chỉ mở kết nối đối với các request có đường dẫn đặc biệt cần
        // connection. (Chẳng hạn đường dẫn tới các servlet, jsp, ..)
        //
        // Tránh tình trạng mở connection với các yêu cầu thông thường
        // (chẳng hạn image, css, javascript,... )
        //
        if (this.needJDBC(req)) {
            Connection conn = null;
            try {
                // create connection
                conn = ConnectionUtils.getConnection();

                // set autocommit  = false, de chu dong dieu khien
                conn.setAutoCommit(false);

                // set attribute
                MyUtils.storeConnection(req, conn);

                // cho phep request tiep tuc
                filterChain.doFilter(servletRequest, servletResponse);

                //goi commit de commit  giao dich voi db
                conn.commit();
                ;
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtils.rollbackQuietly(conn);
                throw new ServletException();
            } finally {
                ConnectionUtils.closedConnect(conn);
            }
        }

        // voi cac requet thong thuong
        // khong can mo connection
        else {
            // cho phep request di tiep
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    //JDBCFilter với khai báo url-pattern = /*, điều đó có nghĩa là mọi request của người dùng đều phải đi qua filter này.
    // JDBCFilter sẽ kiểm tra request để đảm bảo chỉ mở kết nối JDBC cho các request cần thiết,
    //chẳng hạn cho Servlet, tránh mở kết nối JDBC đối với các request thông thường như image, css, js, html.


}

