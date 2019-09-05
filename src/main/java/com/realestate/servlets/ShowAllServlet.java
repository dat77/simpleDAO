package com.realestate.servlets;


import com.realestate.dbabstract.AbstractHandler;
import com.realestate.dbaccess.Flat;
import com.realestate.dbaccess.FlatDAOMySQL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Showall", urlPatterns = {"/showall"})
public class ShowAllServlet extends HttpServlet {

    private  AbstractHandler handlerMySQL;

    @Override
    public void init() throws ServletException {
        super.init();
        handlerMySQL = FlatDAOMySQL.getInstance();
    }

    @Override
    public void destroy() {
        FlatDAOMySQL.closeInstance();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] fields = req.getParameterValues("Fields");

        List<Object[]> flats = null;
        handlerMySQL = FlatDAOMySQL.getInstance();
        try {
            flats = handlerMySQL.selectAll(Flat.class, fields);
        } catch (SQLException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        req.setAttribute("flatslist", flats);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/flatslist.jsp");
        dispatcher.forward(req, resp);
    }

}
