package edu.acc.j2ee.hubbub.controllers;

import edu.acc.j2ee.hubbub.models.HubbubRegisterBean;
import edu.acc.j2ee.hubbub.models.HubbubUser;
import edu.acc.j2ee.hubbub.models.HubbubDao;
import edu.acc.j2ee.hubbub.models.HubbubPost;
import edu.acc.j2ee.hubbub.models.HubbubModel;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HubbubController extends HttpServlet {
    private String viewPath;
    private HubbubDao dao;
    private int postsPerPage;
    
    @Override
    public void init() throws ServletException {
        viewPath = this.getServletConfig().getInitParameter("view.path");
        dao = (HubbubDao)this.getServletContext().getAttribute("dao");
        String ppp = this.getServletConfig().getInitParameter("posts.perpage");
        postsPerPage = Integer.parseInt(ppp);
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
            case "post": destination = post(request); break;
            case "timeline":
            default: destination = timeline(request);
        }
        request.getRequestDispatcher(viewPath + File.separator + destination +
                ".jsp").forward(request, response);
    }

    private String timeline(HttpServletRequest request) {
        HubbubModel<List<HubbubPost>> model;
        try {
            if (request.getSession().getAttribute("user") == null)
                model = dao.getSomePosts(postsPerPage);
            else
                model = dao.getPostsByPostDateDescending(postsPerPage);
            if (model.hasErrors())
                request.setAttribute("errors", model.getErrors());
            else if (model.getModel() == null)
                request.setAttribute("errors", Arrays.asList("Unexplained error"));
            else
                request.setAttribute("posts", model.getModel());
        } catch (SQLException sqle) {
            request.setAttribute("errors", Arrays.asList(sqle.getMessage()));
        }
        return "timeline";
    }
    
    private String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return timeline(request);
    }
    
    private String login(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null)
            return timeline(request);
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "login";
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        try {
            HubbubModel<HubbubUser> model = dao.authenticate(userName, password);
            if (model.hasErrors())
                request.setAttribute("errors", model.getErrors());
            else if (model.getModel() == null)
                request.setAttribute("errors", Arrays.asList("Access Denied"));
            else {
                request.getSession().setAttribute("user", model.getModel());
                return timeline(request);
            } 
        } catch (SQLException sqle) {
            request.setAttribute("errors", Arrays.asList(sqle.getMessage()));
        }
        return "login";
    }
    
    public String register(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null)
            return timeline(request);
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "register";
        String user = request.getParameter("user");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");
        HubbubRegisterBean bean = new HubbubRegisterBean(user, pass1, pass2);
        try {
            HubbubModel<HubbubUser> model = dao.register(bean);
            if (model.hasErrors())
                request.setAttribute("errors", model.getErrors());
            else if (model.getModel() == null)
                request.setAttribute("errors", Arrays.asList("Unexplained error"));
            else {
                request.getSession().setAttribute("user", model.getModel());
                return timeline(request);
            }
        } catch (SQLException sqle) {
            request.setAttribute("errors", Arrays.asList(sqle.getMessage()));
        }
        return "register";
     }
    
    public String post(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null)
            return timeline(request);
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "post";
        String content = request.getParameter("content");
        HubbubUser user = (HubbubUser)
                request.getSession().getAttribute("user");
        try {
            HubbubModel<HubbubPost> model = dao.addPost(content, user);
            if (model.hasErrors())
                request.setAttribute("errors", model.getErrors());
            else
                return timeline(request);                
        } catch (SQLException sqle) {
            request.setAttribute("errors", Arrays.asList(sqle.getMessage()));
        }
        return "post";       
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
