package com.mycompany.project;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LinkSQL {

    public static Connection getConnection() {
        try {
            // رابط الاتصال مع SQL Server باستخدام SQL Authentication
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Tourism;encrypt=true;trustServerCertificate=true;";
            String user = "sa"; // اسم المستخدم
            String password = "NewStrongPassword123"; // كلمة المرور

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to SQL Server successfully!");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // لو حابة ممكن تضيفي دالة لإغلاق الاتصال بسهولة
    public static void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Connection closed successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}