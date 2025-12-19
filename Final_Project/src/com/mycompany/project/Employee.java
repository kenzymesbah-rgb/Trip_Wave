package com.mycompany.project;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.*;
import java.util.ArrayList;

public class Employee extends Person implements Manageable {

    private String Position;
    private double Salary;
    private ArrayList<Trip> managedTrips = new ArrayList<>();
    private ArrayList<Booking> managedBookings = new ArrayList<>();

    public Employee(String fullName, String email, String password_hash, String phoneNumber, String position, double salary) {
        super(fullName, email, password_hash, phoneNumber);
        this.Position = position;
        this.Salary = salary;
    }


    public String getPosition() {
        return Position;
    }

    public double getSalary() {
        return Salary;
    }

    public void setPosition(String position) {
        this.Position = position;
    }

    public void setSalary(double salary) {
        this.Salary = salary;
    }

    @Override
    public void showInfo() {
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Position: " + Position);
        System.out.println("Salary: " + Salary);
    }

    @Override
    public void add() {
        System.out.println("Use project.Manager class to add employees.");
    }

    @Override
    public void update() {
        System.out.println("Use project.Manager class to update employees.");
    }

    @Override
    public void delete() {
        System.out.println("Use project.Manager class to delete employees.");
    }

    @Override
    public void display() {
        this.showInfo();
    }

    public void updateBookingStatus(int bookingID, String status) {
        System.out.println("project.Booking " + bookingID + " updated to: " + status);
    }

    public void updateTripStatus(int tripID, String status) {
        System.out.println("Trip " + tripID + " updated to: " + status);
    }

    public void saveToDatabase() {
        try (Connection con = com.mycompany.project.LinkSQL.getConnection()) {
            String sql = "INSERT INTO project.Employee (FullName, Email, Phone, Position, Salary) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.getFullName());
            ps.setString(2, this.getEmail());
            ps.setString(3, this.getPhoneNumber());
            ps.setString(4, this.Position);
            ps.setDouble(5, this.Salary);

            ps.executeUpdate();
            System.out.println("project.Employee saved to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
