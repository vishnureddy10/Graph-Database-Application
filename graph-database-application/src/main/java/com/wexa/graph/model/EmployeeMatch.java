package com.wexa.graph.model;

import java.util.List;

public class EmployeeMatch {

    private String employeeName;
    private List<String> matchingSkills;
    private int matchedSkills;
    private int requiredSkills;
    private double matchPercentage;

    public EmployeeMatch(String employeeName,
                         List<String> matchingSkills,
                         int matchedSkills,
                         int requiredSkills,
                         double matchPercentage) {

        this.employeeName = employeeName;
        this.matchingSkills = matchingSkills;
        this.matchedSkills = matchedSkills;
        this.requiredSkills = requiredSkills;
        this.matchPercentage = matchPercentage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public List<String> getMatchingSkills() {
        return matchingSkills;
    }

    public int getMatchedSkills() {
        return matchedSkills;
    }

    public int getRequiredSkills() {
        return requiredSkills;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    @Override
    public String toString() {
        return "EmployeeMatch{" +
                "employeeName='" + employeeName + '\'' +
                ", matchingSkills=" + matchingSkills +
                ", matchedSkills=" + matchedSkills +
                ", requiredSkills=" + requiredSkills +
                ", matchPercentage=" + matchPercentage +
                '}';
    }
}