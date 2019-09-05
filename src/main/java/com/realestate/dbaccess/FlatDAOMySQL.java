package com.realestate.dbaccess;

import com.realestate.dbabstract.AbstractDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FlatDAOMySQL extends AbstractDAO<Flat> {

    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/realestate?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    static final String DB_USER = "root";
    static final String USER_PASSWORD = "admin77";
    static final String TABLE = "Flat";
    private static Connection CONNECTION = null;

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver was loaded successful");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CONNECTION = getConnection();
    }

    private static final FlatDAOMySQL flatDbHandler = new FlatDAOMySQL(CONNECTION, TABLE);

    private FlatDAOMySQL(Connection conn, String table) {
        super(conn, table);
    }

    public static synchronized FlatDAOMySQL getInstance(){
        if (CONNECTION == null){
            CONNECTION = getConnection();
        }
        return flatDbHandler;
    }

    public static void closeInstance(){
        if (CONNECTION != null){
            try {
                CONNECTION.close();
                CONNECTION = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, USER_PASSWORD);
            return conn;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
