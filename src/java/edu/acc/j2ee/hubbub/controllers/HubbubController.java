package edu.acc.j2ee.hubbub.controllers;

import edu.acc.j2ee.hubbub.models.HubbubRegisterBean;
import edu.acc.j2ee.hubbub.models.HubbubUser;
import edu.acc.j2ee.hubbub.models.HubbubUserDao;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HubbubController extends HttpServlet {
    private String viewPath;
    
    @Override
    public void init() throws ServletException {
        viewPath = this.getServletConfig().getInitParameter("view.path");
    }    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.length() < 1)
            action = "timeline";
        String destination;
        switch (action) {
            case "register": destination = register(request); break;
            case "logout": destination = logout(request); break;
            case "login": destination = login(request); break;
            case "timeline":
            default: destination = timeline(request);
        }
        request.getRequestDispatcher(viewPath + File.separator + destination + ".jsp")
            .forward(request, response);
    }

    private String timeline(HttpServletRequest request) {
        HubbubUserDao dao = (HubbubUserDao)this.getServletContext().getAttribute("userDao");
        request.setAttribute("users", dao.getUsersByUserNameAscending());
        return "timeline";
    }
    
    private String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return timeline(request);
    }
    
    private String login(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "login";
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        HubbubUserDao dao = (HubbubUserDao)request.getServletContext()
                .getAttribute("userDao");
        HubbubUser user = dao.authenticate(userName, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            return timeline(request);
        } else {
            request.setAttribute("flash", "Access Denied");
            return "login";
        }
    }
    
    public String register(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "register";
        String user = request.getParameter("user");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");
        HubbubRegisterBean bean = new HubbubRegisterBean(user, pass1, pass2);
        HubbubUserDao dao = (HubbubUserDao)this.getServletContext().
                getAttribute("userDao");
        List<String> errors = dao.register(bean);
        if (errors.isEmpty()) {
            request.getSession().setAttribute("user", dao.getUserByUserName(user));
            return timeline(request);
        } else {
            request.setAttribute("flash", "Registration Unsuccessful");
            request.setAttribute("errors", errors);
            return "register";
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
