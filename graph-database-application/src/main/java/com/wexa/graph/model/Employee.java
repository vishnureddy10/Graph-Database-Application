package com.wexa.graph.model;

public class Employee {

    private String employeeId;
    private String name;
    private String email;
    private String role;

    public Employee(String employeeId, String name, String email, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}