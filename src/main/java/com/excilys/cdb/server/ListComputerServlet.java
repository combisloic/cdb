package com.excilys.cdb.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.persistence.PersistenceFacade;

@WebServlet(name = "List Computers", urlPatterns = "/list-computers")
public class ListComputerServlet extends HttpServlet {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 4009417829257782424L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int page, itemPerPage;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            itemPerPage = Integer.parseInt(request.getParameter("itemPerPage"));

        } catch (NumberFormatException nfe) {
            page = 0;
            itemPerPage = 10;
        }

        request.setAttribute("computers", PersistenceFacade.getInstance().listComputers(page, itemPerPage));
        request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet " + this.getServletName() + " has started");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet " + this.getServletName() + " has stopped");
    }


}