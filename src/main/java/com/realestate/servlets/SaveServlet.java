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

@WebServlet(name = "addflat", urlPatterns = {"/addflat"})
public class SaveServlet extends HttpServlet {

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

        String district=req.getParameter("district");
        String street=req.getParameter("street");
        double space= Double.parseDouble(req.getParameter("space"));
        int roomsNumber= Integer.parseInt(req.getParameter("roomsNumber"));
        double price=Double.parseDouble(req.getParameter("price"));
        String phoneNumber=req.getParameter("phoneNumber");


        try {
            handlerMySQL.add(new Flat(0,  district,  street,  space,  roomsNumber,  price,  phoneNumber));
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/realestate.html");
        dispatcher.forward(req, resp);

    }
}
