package com.mycompany.project;

import java.sql.*;

public class Customer extends Person implements Manageable {

    private String nationality;
    private String city;
    private String state;

    // Constructor مظبوط على ترتيب Person بتاعك
    public Customer(String fullName, String email, String password, String phone, String city, String nationality) {
        super(fullName, email, password, phone); // الترتيب: اسم، ايميل، باسورد، هاتف
        this.city = city;
        this.state = "N/A"; // قيمة افتراضية
        this.nationality = nationality;
    }

    public String getNationality() { return nationality; }

    // دوال الواجهة (سيبها فاضية حالياً)
    @Override public void showInfo() {}
    @Override public void add() {}
    @Override public void update() {}
    @Override public void delete() {}
    @Override public void display() {}

    // === 1. دالة التسجيل (Register) ===
    public boolean saveToDatabase() {
        if (getIdByEmail(this.getEmail()) != -1) return false; // لو الإيميل موجود نرفض

        String sql = "INSERT INTO Customer (FullName, Email, Password, City, State, Phone, Nationality) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, getFullName());
            ps.setString(2, getEmail());
            ps.setString(3, getPasswordHash()); // دي الدالة اللي عدلناها في Person
            ps.setString(4, city);
            ps.setString(5, state);
            ps.setString(6, getPhoneNumber());
            ps.setString(7, nationality);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === 2. دالة تسجيل الدخول (Login) ===
    public static boolean checkLogin(String email, String password) {
        String sql = "SELECT * FROM Customer WHERE Email = ? AND Password = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // لو رجع نتيجة يبقى صح
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === 3. دالة مساعدة عشان نجيب ID العميل للحجز ===
    public static int getIdByEmail(String email) {
        String sql = "SELECT CusID FROM Customer WHERE Email = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("CusID");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }
}