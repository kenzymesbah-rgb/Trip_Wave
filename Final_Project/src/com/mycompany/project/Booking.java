package com.mycompany.project;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Date;
import java.sql.*;

public class Booking {

    private final int CusID;
    private final int tripID;
    private final Date BookDate;
    private int NumOfPeople;  // مهم: الاسم مطابق للـgetter
    private double Price;
    private String paymentStatus;

    public Booking( int customerID, int tripID, Date bookingDate,
                    int NumOfPeople, String paymentStatus) {

        if (bookingDate == null) throw new IllegalArgumentException("project.Booking date cannot be null.");
        if (NumOfPeople <= 0) throw new IllegalArgumentException("Number of people must be at least 1.");
        if (paymentStatus == null || paymentStatus.isBlank()) throw new IllegalArgumentException("Payment status cannot be empty.");


        this.CusID = customerID;
        this.tripID = tripID;
        this.BookDate = bookingDate;
        this.NumOfPeople = NumOfPeople;
        this.paymentStatus = paymentStatus;
        this.Price = 0;
    }


    public int getCustomerID() { return CusID; }
    public int getTripID() { return tripID; }
    public Date getBookingDate() { return BookDate; }
    public int getNumOfPeople() { return NumOfPeople; }  // هنا الـgetter

    public double getTotalPrice() { return Price; }
    public String getPaymentStatus() { return paymentStatus; }

    public void setNumOfPeople(int NumOfPeople) {
        if (NumOfPeople <= 0) throw new IllegalArgumentException("Number of people must be positive.");
        this.NumOfPeople = NumOfPeople;
    }

    public void setPaymentStatus(String paymentStatus) {
        if (paymentStatus == null || paymentStatus.isBlank()) throw new IllegalArgumentException("Payment status cannot be empty.");
        this.paymentStatus = paymentStatus;
    }

    public double calculatePrice(double pricePerPerson) {
        if (pricePerPerson <= 0) throw new IllegalArgumentException("Price per person must be positive.");
        Price = NumOfPeople * pricePerPerson;
        return Price;
    }

    public boolean processPayment(double amount) {
        if (amount >= Price) {
            paymentStatus = "Paid";
            return true;
        } else {
            paymentStatus = "Pending";
            return false;
        }
    }

    @Override
    public String toString() {
        return "project.Booking{" +
                ", customerID=" + CusID +
                ", tripID=" + tripID +
                ", bookingDate=" + BookDate +
                ", NumOfPeople=" + NumOfPeople +
                ", totalPrice=" + Price +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }

    public void saveToDatabase() {
        try (Connection con = com.mycompany.project.LinkSQL.getConnection()) {
            String sql = "INSERT INTO project.Booking (NumOfPeople, BookDate, CusID, TripID, totalPrice, PaymentStatus) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, this.NumOfPeople);
            ps.setDate(2, new java.sql.Date(this.BookDate.getTime()));
            ps.setInt(3, this.CusID);
            ps.setInt(4, this.tripID);
            ps.setDouble(5, this.Price);
            ps.setString(6, this.paymentStatus);

            ps.executeUpdate();
            System.out.println("project.Booking saved to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
