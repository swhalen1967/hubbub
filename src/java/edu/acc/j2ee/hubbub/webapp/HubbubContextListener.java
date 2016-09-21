package edu.acc.j2ee.hubbub.webapp;

import edu.acc.j2ee.hubbub.models.HubbubUser;
import edu.acc.j2ee.hubbub.models.HubbubUserDao;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HubbubContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HubbubUserDao users = new HubbubUserDao();
        ServletContext ctx = sce.getServletContext();
        for (int i = 1; i < 3; i++ ) {
            String userName = ctx.getInitParameter(paramName(i,"name"));
            String password = ctx.getInitParameter(paramName(i,"pass"));
            String joinDate = ctx.getInitParameter(paramName(i,"join"));
            HubbubUser user = new HubbubUser(userName, password, joinDate);
            users.addUser(user);
        }
        ctx.setAttribute("userDao", users);
    }
    
    private String paramName(int i, String suffix) {
        return String.format("user.%d.%s", i, suffix);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
