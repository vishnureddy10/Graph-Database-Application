package com.wexa.graph.repository;

import java.util.ArrayList;
import java.util.List;
import com.wexa.graph.model.Employee;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final Driver driver;

    public EmployeeRepository(Driver driver) {
        this.driver = driver;
    }

    // Create Employee
    public void createEmployee(Employee employee) {

        String query = """
                CREATE (e:Employee {
                    employeeId: $employeeId,
                    name: $name,
                    email: $email,
                    role: $role
                })
                """;

        try (Session session = driver.session()) {

            session.run(
                query,
                Values.parameters(
                    "employeeId", employee.getEmployeeId(),
                    "name", employee.getName(),
                    "email", employee.getEmail(),
                    "role", employee.getRole()
                )
            );
        }
    }

    // Find Employee by ID
    public Employee findById(String employeeId) {

        String query = """
                MATCH (e:Employee {employeeId: $employeeId})
                RETURN e.employeeId AS employeeId,
                       e.name AS name,
                       e.email AS email,
                       e.role AS role
                """;

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("employeeId", employeeId)
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.next();

            return new Employee(
                record.get("employeeId").asString(),
                record.get("name").asString(),
                record.get("email").asString(),
                record.get("role").asString()
            );
        }
    }

    // Find all Employees
    public List<Employee> findAll() {

        String query = """
                MATCH (e:Employee)
                RETURN e.employeeId AS employeeId,
                       e.name AS name,
                       e.email AS email,
                       e.role AS role
                ORDER BY e.name
                """;

        List<Employee> employees = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(query);

            while (result.hasNext()) {

                Record record = result.next();

                employees.add(
                    new Employee(
                        record.get("employeeId").asString(),
                        record.get("name").asString(),
                        record.get("email").asString(),
                        record.get("role").asString()
                    )
                );
            }
        }

        return employees;
    }

    // Find skills of an employee
    public List<String> findEmployeeSkills(String employeeId) {

        String query = """
                MATCH (e:Employee {employeeId: $employeeId})
                      -[:HAS_SKILL]->(s:Skill)
                RETURN s.name AS skill
                ORDER BY s.name
                """;

        List<String> skills = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("employeeId", employeeId)
            );

            while (result.hasNext()) {
                skills.add(
                    result.next().get("skill").asString()
                );
            }
        }

        return skills;
    }

    // Find projects of an employee
    public List<String> findEmployeeProjects(String employeeId) {

        String query = """
                MATCH (e:Employee {employeeId: $employeeId})
                      -[:WORKS_ON]->(p:Project)
                RETURN p.name AS project
                ORDER BY p.name
                """;

        List<String> projects = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("employeeId", employeeId)
            );

            while (result.hasNext()) {
                projects.add(
                    result.next().get("project").asString()
                );
            }
        }

        return projects;
    }
}