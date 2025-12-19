package com.mycompany.project;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;

public class Destination {


    private String DesName;
    private String Country;
    private String City;
    private String Description;
    private double Cost;

    // Constructor with validation
    public Destination(String DesName, String Country,
                       String City, String Description, double Cost) {

        if (DesName == null || DesName.isBlank()) {
            throw new IllegalArgumentException("project.Destination name cannot be empty.");
        }

        if (Country == null || Country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }

        if (City == null || City.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty.");
        }

        if (Description == null || Description.length() < 5) {
            throw new IllegalArgumentException("Description must be at least 5 characters.");
        }

        if (Cost <= 0) {
            throw new IllegalArgumentException("Cost must be positive.");
        }

        this.DesName = DesName;
        this.Country = Country;
        this.City = City;
        this.Description = Description;
        this.Cost = Cost;
    }


    public String getDesName() {
        return DesName;
    }

    public void setDesName(String DesName) {
        if (DesName == null || DesName.isBlank()) {
            throw new IllegalArgumentException("project.Destination name cannot be empty.");
        }
        this.DesName = DesName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        if (Country == null || Country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }
        this.Country = Country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        if (City == null || City.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty.");
        }
        this.City = City;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        if (Description == null || Description.length() < 5) {
            throw new IllegalArgumentException("Description must be at least 5 characters.");
        }
        this.Description = Description;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        if (Cost <= 0) {
            throw new IllegalArgumentException("Cost must be positive.");
        }
        this.Cost = Cost;
    }

    @Override
    public String toString() {
        return "project.Destination{" +
                ", DesName='" + DesName + '\'' +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                ", Description='" + Description + '\'' +
                ", Cost=" + Cost +
                '}';
    }

    public void displayInfo() {
        System.out.println("Name: " + DesName);
        System.out.println("Country: " + Country);
        System.out.println("City: " + City);
        System.out.println("Description: " + Description);
        System.out.println("Cost: " + Cost);
    }

    public void saveToDatabase() {
        try {
            Connection con = com.mycompany.project.LinkSQL.getConnection();
            String sql = "INSERT INTO project.Destination ( DesName, City, Country, Cost, Description) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.DesName);
            ps.setString(2, this.City);
            ps.setString(3, this.Country);
            ps.setDouble(4, this.Cost);
            ps.setString(5, this.Description);

            ps.executeUpdate();
            con.close();

            System.out.println("project.Destination saved to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
