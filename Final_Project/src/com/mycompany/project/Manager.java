package com.mycompany.project;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

public class Manager {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();

    // إدارة أي project.Manageable من نوع project.Customer
    public void addCustomer(Customer c) {
        customers.add(c);
        System.out.println("project.Customer added: " + c.getFullName());
    }

    public void removeCustomer(Customer c) {
        customers.remove(c);
        System.out.println("project.Customer removed: " + c.getFullName());
    }

    public void displayAllCustomers() {
        for (Customer c : customers) c.display(); // display من project.Manageable
    }

    // نفس الكلام للموظفين
    public void addEmployee(Employee e) {
        employees.add(e);
        System.out.println("project.Employee added: " + e.getFullName());
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
        System.out.println("project.Employee removed: " + e.getFullName());
    }

    public void displayAllEmployees() {
        for (Employee e : employees) e.display(); // display من project.Manageable
    }

    public void saveAllEmployeesToDatabase() {
        for (Employee e : employees) {
            e.saveToDatabase();
        }
    }
}