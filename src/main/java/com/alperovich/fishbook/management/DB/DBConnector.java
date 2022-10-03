package com.alperovich.fishbook.management.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    static String user = "root";
    static String pass = "root";
    static String url = "jdbc:mysql://localhost:3306/fishbooking_db?serverTimezone=UTC";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection is open :" + conn.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void release(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection is closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
