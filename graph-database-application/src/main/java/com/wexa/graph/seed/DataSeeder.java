package com.wexa.graph.seed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

public class DataSeeder {

    private final Driver driver;

    private final Random random = new Random(42);

    public DataSeeder(Driver driver) {
        this.driver = driver;
    }

    public void seed() {

        System.out.println("====================================");
        System.out.println("      STARTING DATABASE SEEDING");
        System.out.println("====================================");

        clearDatabase();

        createDepartments();
        createSkills();
        createProjects();
        createEmployees();

        createEmployeeSkills();
        createEmployeeProjects();
        createEmployeeDepartments();

        createReportingRelationships();
        createMentorRelationships();
        createCollaborationRelationships();

        createProjectSkills();

        System.out.println("====================================");
        System.out.println("       DATABASE SEEDING COMPLETE");
        System.out.println("====================================");
    }

    // =========================================================
    // CLEAR DATABASE
    // =========================================================

    private void clearDatabase() {

        String query = """
                MATCH (n)
                DETACH DELETE n
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Database cleared.");
    }

    // =========================================================
    // DEPARTMENTS
    // =========================================================

    private void createDepartments() {

        List<String> departments = Arrays.asList(
                "Engineering",
                "Data Science",
                "Artificial Intelligence",
                "Cloud Computing",
                "Cyber Security",
                "DevOps",
                "Product Development",
                "Research",
                "Quality Assurance",
                "Business Analytics"
        );

        String query = """
                UNWIND $departments AS department
                CREATE (:Department {name: department})
                """;

        try (Session session = driver.session()) {

            session.run(
                    query,
                    Values.parameters("departments", departments)
            );
        }

        System.out.println("10 Departments created.");
    }

    // =========================================================
    // SKILLS
    // =========================================================

    private void createSkills() {

        List<String> skills = Arrays.asList(
                "Java",
                "Python",
                "Spring Boot",
                "JavaScript",
                "React",
                "Angular",
                "Node.js",
                "SQL",
                "PostgreSQL",
                "MongoDB",
                "Neo4j",
                "Docker",
                "Kubernetes",
                "AWS",
                "Azure",
                "Git",
                "GitHub",
                "Linux",
                "Machine Learning",
                "Deep Learning",
                "TensorFlow",
                "PyTorch",
                "Data Science",
                "Data Analytics",
                "Power BI",
                "Tableau",
                "Apache Spark",
                "Kafka",
                "Airflow",
                "Cyber Security"
        );

        String query = """
                UNWIND $skills AS skill
                CREATE (:Skill {name: skill})
                """;

        try (Session session = driver.session()) {

            session.run(
                    query,
                    Values.parameters("skills", skills)
            );
        }

        System.out.println("30 Skills created.");
    }

    // =========================================================
    // PROJECTS
    // =========================================================

    private void createProjects() {

        List<Map<String, Object>> projects = new ArrayList<>();

        projects.add(Map.of(
                "id", "P001",
                "name", "Employee Skill Management",
                "description", "Graph based employee skill matching platform"
        ));

        projects.add(Map.of(
                "id", "P002",
                "name", "AI Recruitment System",
                "description", "AI powered recruitment and candidate ranking"
        ));

        projects.add(Map.of(
                "id", "P003",
                "name", "Fraud Detection Platform",
                "description", "Machine learning based fraud detection"
        ));

        projects.add(Map.of(
                "id", "P004",
                "name", "Customer Analytics",
                "description", "Customer behavior and analytics platform"
        ));

        projects.add(Map.of(
                "id", "P005",
                "name", "Cloud Migration Platform",
                "description", "Enterprise cloud migration solution"
        ));

        projects.add(Map.of(
                "id", "P006",
                "name", "Cyber Threat Detection",
                "description", "Real time cyber security monitoring"
        ));

        projects.add(Map.of(
                "id", "P007",
                "name", "Recommendation Engine",
                "description", "Personalized recommendation system"
        ));

        projects.add(Map.of(
                "id", "P008",
                "name", "Smart Banking Platform",
                "description", "Digital banking and transaction platform"
        ));

        projects.add(Map.of(
                "id", "P009",
                "name", "Healthcare Analytics",
                "description", "Healthcare data analytics platform"
        ));

        projects.add(Map.of(
                "id", "P010",
                "name", "E-Commerce Intelligence",
                "description", "E-commerce business intelligence platform"
        ));

        projects.add(Map.of(
                "id", "P011",
                "name", "DevOps Automation",
                "description", "CI/CD and infrastructure automation"
        ));

        projects.add(Map.of(
                "id", "P012",
                "name", "Real Time Data Pipeline",
                "description", "Streaming data processing pipeline"
        ));

        projects.add(Map.of(
                "id", "P013",
                "name", "Social Network Analytics",
                "description", "Graph based social network analytics"
        ));

        projects.add(Map.of(
                "id", "P014",
                "name", "AI Resume Analyzer",
                "description", "AI based resume analysis platform"
        ));

        projects.add(Map.of(
                "id", "P015",
                "name", "Predictive Maintenance",
                "description", "Machine learning based maintenance prediction"
        ));

        projects.add(Map.of(
                "id", "P016",
                "name", "Log Monitoring System",
                "description", "Centralized application log monitoring"
        ));

        projects.add(Map.of(
                "id", "P017",
                "name", "Financial Forecasting",
                "description", "Financial prediction and forecasting"
        ));

        projects.add(Map.of(
                "id", "P018",
                "name", "Smart Inventory System",
                "description", "AI assisted inventory management"
        ));

        projects.add(Map.of(
                "id", "P019",
                "name", "Knowledge Graph Platform",
                "description", "Enterprise knowledge graph"
        ));

        projects.add(Map.of(
                "id", "P020",
                "name", "Enterprise Data Lake",
                "description", "Large scale enterprise data platform"
        ));

        String query = """
                UNWIND $projects AS project

                CREATE (:Project {
                    projectId: project.id,
                    name: project.name,
                    description: project.description
                })
                """;

        try (Session session = driver.session()) {

            session.run(
                    query,
                    Values.parameters("projects", projects)
            );
        }

        System.out.println("20 Projects created.");
    }

    // =========================================================
    // 350 EMPLOYEES
    // =========================================================

    private void createEmployees() {

        String[] firstNames = {
                "Vishnu", "Priya", "Arjun", "Rahul", "Ananya",
                "Karthik", "Sneha", "Rohit", "Neha", "Aditya",
                "Pooja", "Sanjay", "Akhil", "Divya", "Varun",
                "Swathi", "Nikhil", "Meghana", "Harsha", "Keerthi",
                "Sai", "Manoj", "Kiran", "Shreya", "Ravi",
                "Deepak", "Anjali", "Vivek", "Lakshmi", "Surya"
        };

        String[] lastNames = {
                "Reddy", "Sharma", "Kumar", "Verma", "Patel",
                "Rao", "Gupta", "Singh", "Nair", "Iyer",
                "Das", "Mehta", "Joshi", "Mishra", "Kapoor",
                "Naidu", "Varma", "Menon", "Bhat", "Chowdary"
        };

        String[] roles = {
                "Software Engineer",
                "Java Developer",
                "Backend Developer",
                "Frontend Developer",
                "Full Stack Developer",
                "Data Analyst",
                "Data Scientist",
                "Machine Learning Engineer",
                "DevOps Engineer",
                "Cloud Engineer",
                "AI Engineer",
                "Cyber Security Engineer",
                "QA Engineer",
                "Software Architect",
                "Technical Lead"
        };

        List<Map<String, Object>> employees = new ArrayList<>();

        for (int i = 1; i <= 350; i++) {

            String firstName = firstNames[(i - 1) % firstNames.length];

            String lastName =
                    lastNames[((i - 1) / firstNames.length) % lastNames.length];

            String name = firstName + " " + lastName;

            String employeeId =
                    String.format("E%03d", i);

            String email =
                    firstName.toLowerCase()
                    + "."
                    + lastName.toLowerCase()
                    + i
                    + "@wexa.com";

            String role = roles[random.nextInt(roles.length)];

            employees.add(
                    Map.of(
                            "employeeId", employeeId,
                            "name", name,
                            "email", email,
                            "role", role
                    )
            );
        }

        String query = """
                UNWIND $employees AS employee

                CREATE (:Employee {
                    employeeId: employee.employeeId,
                    name: employee.name,
                    email: employee.email,
                    role: employee.role
                })
                """;

        try (Session session = driver.session()) {

            session.run(
                    query,
                    Values.parameters("employees", employees)
            );
        }

        System.out.println("350 Employees created.");
    }

    // =========================================================
    // EMPLOYEE -> SKILL
    // =========================================================

    private void createEmployeeSkills() {

        String query = """
                MATCH (e:Employee)
                WITH e

                MATCH (s:Skill)

                WITH e, collect(s) AS skills

                UNWIND range(0, 2 + toInteger(rand() * 3)) AS index

                WITH e, skills[index % size(skills)] AS skill

                MERGE (e)-[:HAS_SKILL]->(skill)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Employee-Skill relationships created.");
    }

    // =========================================================
    // EMPLOYEE -> PROJECT
    // =========================================================

    private void createEmployeeProjects() {

        String query = """
                MATCH (e:Employee)
                MATCH (p:Project)

                WITH e, collect(p) AS projects

                UNWIND range(0, 1 + toInteger(rand() * 2)) AS index

                WITH e, projects[index % size(projects)] AS project

                MERGE (e)-[:WORKS_ON]->(project)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Employee-Project relationships created.");
    }

    // =========================================================
    // EMPLOYEE -> DEPARTMENT
    // =========================================================

    private void createEmployeeDepartments() {

        String query = """
                MATCH (e:Employee)
                MATCH (d:Department)

                WITH e, collect(d) AS departments

                WITH e,
                     departments[
                         toInteger(rand() * size(departments))
                     ] AS department

                MERGE (e)-[:BELONGS_TO]->(department)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Employee-Department relationships created.");
    }

    // =========================================================
    // REPORTING STRUCTURE
    // =========================================================

    private void createReportingRelationships() {

        String query = """
                MATCH (e:Employee)
                WHERE e.employeeId <> 'E001'

                WITH e,
                     toInteger(
                         1 + rand() * 349
                     ) AS managerNumber

                MATCH (manager:Employee {
                    employeeId:
                        'E' + right('000' + toString(managerNumber), 3)
                })

                WHERE e <> manager

                MERGE (e)-[:REPORTS_TO]->(manager)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Reporting relationships created.");
    }

    // =========================================================
    // MENTORING
    // =========================================================

    private void createMentorRelationships() {

        String query = """
                MATCH (e:Employee)
                WHERE e.employeeId <> 'E001'

                WITH e

                MATCH (mentor:Employee)
                WHERE mentor.employeeId <> e.employeeId

                WITH e, collect(mentor) AS mentors

                WITH e,
                     mentors[
                         toInteger(rand() * size(mentors))
                     ] AS mentor

                MERGE (mentor)-[:MENTORS]->(e)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Mentoring relationships created.");
    }

    // =========================================================
    // COLLABORATION
    // =========================================================

    private void createCollaborationRelationships() {

        String query = """
                MATCH (e1:Employee)
                MATCH (e2:Employee)

                WHERE e1.employeeId < e2.employeeId
                  AND rand() < 0.008

                MERGE (e1)-[:COLLABORATES_WITH]->(e2)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Collaboration relationships created.");
    }

    // =========================================================
    // PROJECT -> SKILL
    // =========================================================

    private void createProjectSkills() {

        String query = """
                MATCH (p:Project)
                MATCH (s:Skill)

                WITH p, collect(s) AS skills

                UNWIND range(0, 2 + toInteger(rand() * 3)) AS index

                WITH p, skills[index % size(skills)] AS skill

                MERGE (p)-[:REQUIRES]->(skill)
                """;

        try (Session session = driver.session()) {
            session.run(query);
        }

        System.out.println("Project-Skill relationships created.");
    }
}