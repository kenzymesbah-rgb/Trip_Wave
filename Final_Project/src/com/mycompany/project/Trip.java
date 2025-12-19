package com.mycompany.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private int tripID;
    private String tripName;
    private double price;
    private int availableSeats;
    // متغيرات للعرض فقط
    private String destName;
    private String transType;

    // Constructor للقراءة من الداتا بيز
    public Trip(int tripID, String tripName, double price, int availableSeats, String destName, String transType) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.price = price;
        this.availableSeats = availableSeats;
        this.destName = destName;
        this.transType = transType;
    }

    // Getters عشان الـ GUI يستخدمهم
    public int getTripID() { return tripID; }
    public String getTripName() { return tripName; }
    public double getPrice() { return price; }
    public String getDestName() { return destName; }
    public String getTransType() { return transType; }
    public int getAvailableSeats() { return availableSeats; }

    // === الدالة السحرية لجلب الرحلات ===
    public static List<Trip> getAllTripsFromDB() {
        List<Trip> list = new ArrayList<>();
        // جملة SQL بتجيب بيانات الرحلة + اسم المدينة والدولة + نوع المواصلات
        String sql = "SELECT T.TripID, T.TripName, T.PricePerPerson, T.AvailSeats, D.City, D.Country, Tr.TransType " +
                "FROM Trip T " +
                "JOIN Destination D ON T.DesID = D.DesID " +
                "JOIN Transport Tr ON T.TransID = Tr.TransID " +
                "WHERE T.AvailSeats > 0";

        try (Connection con = LinkSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String fullDest = rs.getString("City") + ", " + rs.getString("Country");
                list.add(new Trip(
                        rs.getInt("TripID"),
                        rs.getString("TripName"),
                        rs.getDouble("PricePerPerson"),
                        rs.getInt("AvailSeats"),
                        fullDest,
                        rs.getString("TransType")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}