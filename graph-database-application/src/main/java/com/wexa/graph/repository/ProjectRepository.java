package com.wexa.graph.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

import com.wexa.graph.model.EmployeeMatch;
import com.wexa.graph.model.Project;
@Repository
public class ProjectRepository {

    private final Driver driver;

    public ProjectRepository(Driver driver) {
        this.driver = driver;
    }

    // ---------------------------------------------------------
    // Create Project
    // ---------------------------------------------------------
    public void createProject(Project project) {

        String query = """
                CREATE (p:Project {
                    projectId: $projectId,
                    name: $name,
                    description: $description
                })
                """;

        try (Session session = driver.session()) {

            session.run(
                query,
                Values.parameters(
                    "projectId", project.getProjectId(),
                    "name", project.getName(),
                    "description", project.getDescription()
                )
            );
        }
    }


    // ---------------------------------------------------------
    // Find Project by ID
    // ---------------------------------------------------------
    public Project findById(String projectId) {

        String query = """
                MATCH (p:Project {projectId: $projectId})
                RETURN p.projectId AS projectId,
                       p.name AS name,
                       p.description AS description
                """;

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("projectId", projectId)
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.next();

            return new Project(
                record.get("projectId").asString(),
                record.get("name").asString(),
                record.get("description").asString()
            );
        }
    }


    // ---------------------------------------------------------
    // Find all Projects
    // ---------------------------------------------------------
    public List<Project> findAll() {

        String query = """
                MATCH (p:Project)
                RETURN p.projectId AS projectId,
                       p.name AS name,
                       p.description AS description
                ORDER BY p.name
                """;

        List<Project> projects = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(query);

            while (result.hasNext()) {

                Record record = result.next();

                projects.add(
                    new Project(
                        record.get("projectId").asString(),
                        record.get("name").asString(),
                        record.get("description").asString()
                    )
                );
            }
        }

        return projects;
    }


    // ---------------------------------------------------------
    // Find employees working on a project
    // ---------------------------------------------------------
    public List<String> findProjectEmployees(String projectId) {

        String query = """
                MATCH (e:Employee)-[:WORKS_ON]->(p:Project {
                    projectId: $projectId
                })
                RETURN e.name AS employee
                ORDER BY e.name
                """;

        List<String> employees = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("projectId", projectId)
            );

            while (result.hasNext()) {

                employees.add(
                    result.next()
                          .get("employee")
                          .asString()
                );
            }
        }

        return employees;
    }


    // ---------------------------------------------------------
    // Find skills required by a project
    // ---------------------------------------------------------
    public List<String> findProjectSkills(String projectId) {

        String query = """
                MATCH (p:Project {projectId: $projectId})
                      -[:REQUIRES]->(s:Skill)
                RETURN s.name AS skill
                ORDER BY s.name
                """;

        List<String> skills = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("projectId", projectId)
            );

            while (result.hasNext()) {

                skills.add(
                    result.next()
                          .get("skill")
                          .asString()
                );
            }
        }

        return skills;
    }


    // ---------------------------------------------------------
    // FINAL FEATURE:
    // Find employees matching a project's required skills
    // ---------------------------------------------------------
    public List<EmployeeMatch> findEmployeesForProject(String projectName) {

        String query = """
                MATCH (p:Project {name: $projectName})
                      -[:REQUIRES]->(required:Skill)

                WITH p, collect(required) AS requiredSkills

                MATCH (e:Employee)
                      -[:HAS_SKILL]->(skill:Skill)

                WITH e,
                     requiredSkills,
                     collect(skill) AS employeeSkills

                WITH e,
                     requiredSkills,
                     [s IN employeeSkills
                      WHERE s IN requiredSkills] AS matchingSkills

                WITH e,
                     requiredSkills,
                     matchingSkills

                WHERE size(matchingSkills) > 0

                RETURN e.name AS employee,

                       [s IN matchingSkills |
                        s.name] AS matchingSkills,

                       size(matchingSkills) AS matched,

                       size(requiredSkills) AS required,

                       100.0 * size(matchingSkills)
                       / size(requiredSkills) AS matchPercentage

                ORDER BY matchPercentage DESC
                """;


        List<EmployeeMatch> matches = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("projectName", projectName)
            );

            while (result.hasNext()) {

                Record record = result.next();

                List<String> matchingSkills =
                    record.get("matchingSkills")
                          .asList(value -> value.asString());

                int matched =
                    record.get("matched")
                          .asInt();

                int required =
                    record.get("required")
                          .asInt();

                double percentage =
                    record.get("matchPercentage")
                          .asDouble();

                matches.add(
                    new EmployeeMatch(
                        record.get("employee").asString(),
                        matchingSkills,
                        matched,
                        required,
                        percentage
                    )
                );
            }
        }

        return matches;
    }
}