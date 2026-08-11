package com.wexa.graph.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

import com.wexa.graph.model.Skill;
@Repository
public class SkillRepository {

    private final Driver driver;

    public SkillRepository(Driver driver) {
        this.driver = driver;
    }

    // Create Skill
    public void createSkill(Skill skill) {

        String query = """
                CREATE (s:Skill {
                    skillId: $skillId,
                    name: $name
                })
                """;

        try (Session session = driver.session()) {

            session.run(
                query,
                Values.parameters(
                    "skillId", skill.getSkillId(),
                    "name", skill.getName()
                )
            );
        }
    }

    // Find Skill by ID
    public Skill findById(String skillId) {

        String query = """
                MATCH (s:Skill {skillId: $skillId})
                RETURN s.skillId AS skillId,
                       s.name AS name
                """;

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("skillId", skillId)
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.next();

            return new Skill(
                record.get("skillId").asString(),
                record.get("name").asString()
            );
        }
    }

    // Find all skills
    public List<Skill> findAll() {

        String query = """
                MATCH (s:Skill)
                RETURN s.skillId AS skillId,
                       s.name AS name
                ORDER BY s.name
                """;

        List<Skill> skills = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(query);

            while (result.hasNext()) {

                Record record = result.next();

                skills.add(
                    new Skill(
                        record.get("skillId").asString(),
                        record.get("name").asString()
                    )
                );
            }
        }

        return skills;
    }

    // Find employees who have a particular skill
    public List<String> findEmployeesWithSkill(String skillId) {

        String query = """
                MATCH (e:Employee)-[:HAS_SKILL]->(s:Skill {
                    skillId: $skillId
                })
                RETURN e.name AS employee
                ORDER BY e.name
                """;

        List<String> employees = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("skillId", skillId)
            );

            while (result.hasNext()) {

                employees.add(
                    result.next().get("employee").asString()
                );
            }
        }

        return employees;
    }

    // Find projects requiring a particular skill
    public List<String> findProjectsUsingSkill(String skillId) {

        String query = """
                MATCH (p:Project)-[:REQUIRES]->(s:Skill {
                    skillId: $skillId
                })
                RETURN p.name AS project
                ORDER BY p.name
                """;

        List<String> projects = new ArrayList<>();

        try (Session session = driver.session()) {

            var result = session.run(
                query,
                Values.parameters("skillId", skillId)
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