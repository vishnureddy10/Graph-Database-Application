package com.wexa.graph.model;

public class Department {

    private String departmentId;
    private String name;

    public Department(String departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }
}