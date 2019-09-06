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

@WebServlet(name = "Filterall", urlPatterns = {"/filterall"})
public class ShowFilteredServlet extends HttpServlet {

    private AbstractHandler handlerMySQL;

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
        String district = "district LIKE '%"+req.getParameter("district")+"%'";
        String street = "street LIKE '%"+req.getParameter("street")+"%'";
        String space = "space >= "+req.getParameter("space");
        String roomsNumber = "roomsNumber"+req.getParameter("roomsNumber");
        String price = "price >= "+req.getParameter("price");
        String[] phoneNumbers = req.getParameterValues("phoneNumber");
        String phoneNumber = (phoneNumbers != null && phoneNumbers.length > 0) ? "phoneNumber IS NOT NULL" : "phoneNumber LIKE '%%'";

        List<Object[]> flats = null;
        try {
            flats = handlerMySQL.selectFiltered(Flat.class, district, street, space, roomsNumber, price, phoneNumber);
        } catch (SQLException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        req.setAttribute("flatslist", flats);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/flatslist.jsp");
        dispatcher.forward(req, resp);
    }


}
