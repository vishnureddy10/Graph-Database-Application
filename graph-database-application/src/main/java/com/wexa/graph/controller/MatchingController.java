package com.wexa.graph.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wexa.graph.model.EmployeeMatch;
import com.wexa.graph.service.EmployeeService;

@RestController
public class MatchingController {

    private final EmployeeService employeeService;

    public MatchingController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ==============================
    // EMPLOYEE-PROJECT SKILL MATCH
    // ==============================

    @GetMapping("/api/match")
    public List<EmployeeMatch> findEmployeesForProject(
            @RequestParam String projectName) {

        return employeeService.findEmployeesForProject(projectName);
    }
}