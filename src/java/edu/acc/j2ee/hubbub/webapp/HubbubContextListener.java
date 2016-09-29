package edu.acc.j2ee.hubbub.webapp;

import edu.acc.j2ee.hubbub.models.HubbubDao;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HubbubContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String jdbcProtocol = ctx.getInitParameter("jdbc.protocol");
        String jdbcHost = ctx.getInitParameter("jdbc.host");
        String jdbcPort = ctx.getInitParameter("jdbc.port");
        String jdbcDbName = ctx.getInitParameter("jdbc.dbname");
        String jdbcUser = ctx.getInitParameter("jdbc.user");
        String jdbcPass = ctx.getInitParameter("jdbc.pass");
        String jdbcUrl = String.format(
            "%s://%s:%s/%s;user=%s;password=%s",
            jdbcProtocol, jdbcHost, jdbcPort, jdbcDbName, jdbcUser, jdbcPass
        );
        try {
            HubbubDao dao = new HubbubDao(jdbcUrl);
            ctx.setAttribute("dao", dao);
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        HubbubDao dao = (HubbubDao)ctx.getAttribute("dao");
        dao.close();
    }
}
