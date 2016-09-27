package edu.acc.j2ee.hubbub.webapp;

import edu.acc.j2ee.hubbub.models.HubbubPost;
import edu.acc.j2ee.hubbub.models.HubbubPostDao;
import edu.acc.j2ee.hubbub.models.HubbubUser;
import edu.acc.j2ee.hubbub.models.HubbubUserDao;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HubbubContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        HubbubUserDao users = new HubbubUserDao();
        final String PARAM = "%s.%d.%s";
        for (int i = 1; i < 3; i++) {
            String userName = ctx.getInitParameter(String.format(PARAM,"user",i,"name"));
            String password = ctx.getInitParameter(String.format(PARAM,"user",i,"pass"));
            String joinDate = ctx.getInitParameter(String.format(PARAM,"user",i,"join"));
            HubbubUser user = new HubbubUser(userName, password, joinDate);
            users.addUser(user);
        }
        ctx.setAttribute("userDao", users);
        
        HubbubPostDao posts = new HubbubPostDao();
        for (int i = 1; i <= 3; i++) {
            String content = ctx.getInitParameter(String.format(PARAM,"post",i,"content"));
            String postDate = ctx.getInitParameter(String.format(PARAM,"post",i,"date"));
            String user = ctx.getInitParameter(String.format(PARAM,"post",i,"user"));
            HubbubUser postOwner = users.getUserByUserName(user);
            HubbubPost post = new HubbubPost(content, postDate, postOwner);
            posts.addPost(post);
        }
        ctx.setAttribute("postDao", posts);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
