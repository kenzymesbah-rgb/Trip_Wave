package com.mycompany.project;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

                Connection con = LinkSQL.getConnection(); // استدعاء الدالة اللي عملناها
                if (con != null) {
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT 1"); // Query بسيط جدًا
                        if (rs.next()) {
                            System.out.println("Query executed successfully, value: " + rs.getInt(1));
                        }
                        LinkSQL.closeConnection(con); // قفل الاتصال
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Connection failed!");
                }
            }
        }

