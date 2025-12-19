/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;
import java.sql.*;

public class Transport {

    private String TransType;
    private String SupplierName;
    private int AvailableSeat;
    private double PricePerSeat;
    private String DepartTime;
    private String ArrTime;

    // Constructor with validation
    public Transport( String TransType, String SupplierName,
                      int AvailableSeat, double PricePerSeat,
                      String DepartTime, String ArrTime) {

        if (TransType == null || TransType.isBlank()) {
            throw new IllegalArgumentException("Transport type cannot be empty.");
        }

        if (SupplierName == null || SupplierName.isBlank()) {
            throw new IllegalArgumentException("Supplier name cannot be empty.");
        }

        if (AvailableSeat < 0) {
            throw new IllegalArgumentException("Seats available cannot be negative.");
        }

        if (PricePerSeat <= 0) {
            throw new IllegalArgumentException("Price per seat must be positive.");
        }

        if (DepartTime == null || DepartTime.isBlank()) {
            throw new IllegalArgumentException("Departure time cannot be empty.");
        }

        if (ArrTime == null || ArrTime.isBlank()) {
            throw new IllegalArgumentException("Arrival time cannot be empty.");
        }

        this.TransType = TransType;
        this.SupplierName = SupplierName;
        this.AvailableSeat = AvailableSeat;
        this.PricePerSeat = PricePerSeat;
        this.DepartTime = DepartTime;
        this.ArrTime = ArrTime;
    }

    // Getters
    public String getTransType() { return TransType; }
    public String getSupplierName() { return SupplierName; }
    public int getAvailableSeat() { return AvailableSeat; }
    public double getPricePerSeat() { return PricePerSeat; }
    public String getDepartTime() { return DepartTime; }
    public String getArrTime() { return ArrTime; }

    // Setters with validation
    public void setTransType(String TransType) {
        if (TransType == null || TransType.isBlank()) throw new IllegalArgumentException("Transport type cannot be empty.");
        this.TransType = TransType;
    }
    public void setSupplierName(String SupplierName) {
        if (SupplierName == null || SupplierName.isBlank()) throw new IllegalArgumentException("Supplier name cannot be empty.");
        this.SupplierName = SupplierName;
    }
    public void setAvailableSeat(int AvailableSeat) {
        if (AvailableSeat < 0) throw new IllegalArgumentException("Seats available cannot be negative.");
        this.AvailableSeat = AvailableSeat;
    }
    public void setPricePerSeat(double PricePerSeat) {
        if (PricePerSeat <= 0) throw new IllegalArgumentException("Price per seat must be positive.");
        this.PricePerSeat = PricePerSeat;
    }
    public void setDepartTime(String DepartTime) {
        if (DepartTime == null || DepartTime.isBlank()) throw new IllegalArgumentException("Departure time cannot be empty.");
        this.DepartTime = DepartTime;
    }
    public void setArrTime(String ArrTime) {
        if (ArrTime == null || ArrTime.isBlank()) throw new IllegalArgumentException("Arrival time cannot be empty.");
        this.ArrTime = ArrTime;
    }

    // Display method
    public void display() {
        System.out.println("Transport Type: " + TransType);
        System.out.println("Supplier Name: " + SupplierName);
        System.out.println("Available Seats: " + AvailableSeat);
        System.out.println("Price Per Seat: " + PricePerSeat);
        System.out.println("Departure Time: " + DepartTime);
        System.out.println("Arrival Time: " + ArrTime);
    }

    // Save to database
    public void saveToDatabase() {
        try (Connection con = LinkSQL.getConnection()) {
            String sql = "INSERT INTO Transport (TransType, SupplierName, AvailableSeat, PricePerSeat, DepartTime, ArrTime) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.TransType);
            ps.setString(2, this.SupplierName);
            ps.setInt(3, this.AvailableSeat);
            ps.setDouble(4, this.PricePerSeat);
            ps.setString(5, this.DepartTime);
            ps.setString(6, this.ArrTime);

            ps.executeUpdate();
            System.out.println("Transport saved to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
