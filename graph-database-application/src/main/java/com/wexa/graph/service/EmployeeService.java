package com.wexa.graph.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wexa.graph.model.Employee;
import com.wexa.graph.model.EmployeeMatch;
import com.wexa.graph.model.Project;
import com.wexa.graph.model.Skill;
import com.wexa.graph.repository.EmployeeRepository;
import com.wexa.graph.repository.ProjectRepository;
import com.wexa.graph.repository.SkillRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            SkillRepository skillRepository) {

        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    // =========================
    // EMPLOYEE OPERATIONS
    // =========================

    // Create employee
    public void createEmployee(Employee employee) {
        employeeRepository.createEmployee(employee);
    }

    // Find employee by ID
    public Employee getEmployee(String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    // Find all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Find skills of an employee
    public List<String> getEmployeeSkills(String employeeId) {
        return employeeRepository.findEmployeeSkills(employeeId);
    }

    // Find projects of an employee
    public List<String> getEmployeeProjects(String employeeId) {
        return employeeRepository.findEmployeeProjects(employeeId);
    }


    // =========================
    // PROJECT OPERATIONS
    // =========================

    // Create project
    public void createProject(Project project) {
        projectRepository.createProject(project);
    }

    // Find project by ID
    public Project getProject(String projectId) {
        return projectRepository.findById(projectId);
    }

    // Find all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Find employees working on a project
    public List<String> getProjectEmployees(String projectId) {
        return projectRepository.findProjectEmployees(projectId);
    }

    // Find skills required by a project
    public List<String> getProjectSkills(String projectId) {
        return projectRepository.findProjectSkills(projectId);
    }


    // =========================
    // SKILL OPERATIONS
    // =========================

    // Create skill
    public void createSkill(Skill skill) {
        skillRepository.createSkill(skill);
    }

    // Find skill by ID
    public Skill getSkill(String skillId) {
        return skillRepository.findById(skillId);
    }

    // Find all skills
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // Find employees having a particular skill
    public List<String> getEmployeesWithSkill(String skillId) {
        return skillRepository.findEmployeesWithSkill(skillId);
    }

    // Find projects requiring a particular skill
    public List<String> getProjectsUsingSkill(String skillId) {
        return skillRepository.findProjectsUsingSkill(skillId);
    }


    // =========================
    // EMPLOYEE-PROJECT MATCHING
    // =========================

    // Find employees for a project based on required skills
    public List<EmployeeMatch> findEmployeesForProject(String projectName) {
        return projectRepository.findEmployeesForProject(projectName);
    }
}