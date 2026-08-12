# Graph-Database-Application
Employee skill matching application using Spring Boot, Neo4j/CognoDB, Cypher and a web-based UI.
# Employee Skill Matcher Using Graph Database

## 1. Overview

Employee Skill Matcher is a graph-based employee-to-project matching application built with Java, Spring Boot, Neo4j-compatible graph database technology, Cypher, and a web-based user interface.

The application helps identify employees whose skills best match the requirements of a project. Instead of treating employees, projects, and skills as isolated records, the system models their relationships directly in a graph.

The application contains:

- 350 employees
- 20 projects
- 30 skills
- 10 departments
- Employee-to-skill relationships
- Employee-to-project relationships
- Employee-to-department relationships
- Employee reporting relationships
- Employee mentoring relationships
- Employee collaboration relationships
- Project-to-skill requirements

The main user-facing feature is employee skill matching for a selected project.

---

## 2. Problem Statement

In a software organization, projects require different combinations of technical skills, while employees possess different skill sets and work across different projects and departments.

A useful employee allocation system should be able to answer questions such as:

- Which employees have the skills required by a project?
- Which skills does a particular employee have?
- Which projects require a particular skill?
- Which employees are already working on a project?
- Which employees have the highest overlap with a project's required skills?
- How are employees connected through reporting, mentoring, and collaboration relationships?

These questions are relationship-heavy, making a graph database a natural fit for the use case.

---

## 3. Why a Graph Database?

The central reason for using a graph database is that the application is primarily concerned with relationships.

For example, the employee matching workflow can be represented as:

```text
Project
   |
   | REQUIRES
   v
 Skill
   ^
   | HAS_SKILL
   |
Employee
```

This allows the application to traverse the relationships between projects, skills, and employees directly.

The graph also contains additional employee-to-employee relationships:

```text
Employee ── REPORTS_TO ──> Employee

Employee ── MENTORS ─────> Employee

Employee ── COLLABORATES_WITH ──> Employee
```

These relationships can be used for future features such as team recommendations, mentor discovery, organizational analysis, and collaboration analysis.

### Why this can be awkward in a relational database

In a relational implementation, employee-project-skill matching would typically require several tables and joins:

```text
Employee
EmployeeSkill
Skill
ProjectSkill
Project
```

The graph model represents the same information as connected nodes and relationships:

```text
(Employee)-[:HAS_SKILL]->(Skill)
(Project)-[:REQUIRES]->(Skill)
(Employee)-[:WORKS_ON]->(Project)
```

As relationship-oriented questions become more complex, graph traversal provides a more natural way to express the connections.

---

## 4. Use Case

### Employee-to-Project Skill Matching

A user selects a project from the web application.

The backend then queries the graph to identify employees who possess skills required by that project.

The application calculates:

```text
Match Percentage =
(Matching Skills / Required Skills) × 100
```

For example:

```text
Required Skills: 5
Matching Skills: 4

Match Percentage = (4 / 5) × 100 = 80%
```

The UI displays the matching employees and their matching skills.

---

## 5. Key Features

- Employee management through the graph database
- Project management
- Skill management
- Employee-to-skill relationships
- Employee-to-project relationships
- Employee-to-department relationships
- Reporting hierarchy
- Mentoring relationships
- Collaboration relationships
- Project skill requirements
- Employee skill matching
- Match percentage calculation
- REST API
- Interactive web UI
- Employee search
- Employee result sorting
- Loading state
- Empty state
- Error state
- Parameterized Cypher queries

---

## 6. Technology Stack

### Backend

- Java 21
- Spring Boot
- Spring Web
- Neo4j Java Driver

### Database

- Neo4j-compatible graph database
- CognoDB Cloud

### Frontend

- HTML5
- CSS3
- JavaScript

### Build Tool

- Maven

---

## 7. System Architecture

```text
                  ┌────────────────────────────┐
                  │        Web Browser         │
                  │     HTML / CSS / JS        │
                  └─────────────┬──────────────┘
                                │
                                │ HTTP
                                ▼
                  ┌────────────────────────────┐
                  │    MatchingController      │
                  │       REST API             │
                  └─────────────┬──────────────┘
                                │
                                ▼
                  ┌────────────────────────────┐
                  │      EmployeeService       │
                  │    Application Services     │
                  └─────────────┬──────────────┘
                                │
                ┌───────────────┼────────────────┐
                │               │                │
                ▼               ▼                ▼
        ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
        │   Employee   │ │   Project    │ │    Skill     │
        │ Repository   │ │ Repository   │ │ Repository   │
        └──────┬───────┘ └──────┬───────┘ └──────┬───────┘
               │                │                │
               └────────────────┼────────────────┘
                                │
                                │ Cypher
                                ▼
                  ┌────────────────────────────┐
                  │      CognoDB / Neo4j       │
                  │       Graph Database       │
                  └────────────────────────────┘
```

---

## 8. Graph Data Model

The application uses four main node types:

```text
Employee
Project
Skill
Department
```

### Relationships

```text
(Employee)-[:HAS_SKILL]->(Skill)

(Employee)-[:WORKS_ON]->(Project)

(Employee)-[:BELONGS_TO]->(Department)

(Employee)-[:REPORTS_TO]->(Employee)

(Employee)-[:MENTORS]->(Employee)

(Employee)-[:COLLABORATES_WITH]->(Employee)

(Project)-[:REQUIRES]->(Skill)
```

### Graph Overview

```text
                         ┌─────────────┐
                         │    Skill    │
                         └──────▲──────┘
                                │
                           HAS_SKILL
                                │
                         ┌──────┴──────┐
                         │   Employee  │
                         └──┬────┬─────┘
                            │    │
                       WORKS_ON  │ BELONGS_TO
                            │    │
                            ▼    ▼
                         Project Department
                            │
                         REQUIRES
                            │
                            ▼
                          Skill

Employee ──REPORTS_TO──────> Employee
Employee ──MENTORS─────────> Employee
Employee ──COLLABORATES_WITH─> Employee
```

---

## 9. Data Model

### Employee

Employee nodes contain:

```text
employeeId
name
email
role
```

### Project

Project nodes contain:

```text
projectId
name
description
```

### Skill

Skill nodes contain:

```text
skillId
name
```

### Department

Department nodes contain:

```text
name
```

---

## 10. Seed Data

The project includes a `DataSeeder` that clears and recreates the graph database with sample data.

The seed process creates:

```text
10 Departments
30 Skills
20 Projects
350 Employees
```

It then creates relationships for:

```text
Employee → Skill
Employee → Project
Employee → Department
Employee → Employee (Reporting)
Employee → Employee (Mentoring)
Employee → Employee (Collaboration)
Project → Skill
```

The employee dataset is generated programmatically, allowing the application to demonstrate the graph queries against a larger dataset rather than only a few manually entered records.

The seeder uses a deterministic Java `Random` instance for generated employee roles and uses Cypher `UNWIND` operations for bulk creation.

---

## 11. Employee Matching Workflow

The matching feature follows this general flow:

```text
1. User selects a project
          ↓
2. Frontend calls /api/match
          ↓
3. MatchingController receives projectName
          ↓
4. EmployeeService delegates matching
          ↓
5. ProjectRepository queries the graph
          ↓
6. Project required skills are identified
          ↓
7. Employee skills are compared
          ↓
8. Matching employees are returned
          ↓
9. Frontend displays ranked results
```

The result contains information such as:

```text
Employee Name
Matching Skills
Matched Skill Count
Required Skill Count
Match Percentage
```

---

# 12. Cypher Queries

The application uses the Neo4j Java Driver and parameterized Cypher queries.

## 12.1 Find an Employee by ID

```cypher
MATCH (e:Employee)
RETURN e.employeeId AS employeeId,
       e.name AS name,
       e.email AS email,
       e.role AS role;
```

---

## 12.2 Find All Employees

```cypher
MATCH (e:Employee)
RETURN e.employeeId AS employeeId,
       e.name AS name,
       e.email AS email,
       e.role AS role
ORDER BY e.name;
```

---

## 12.3 Find Skills of an Employee

This is a one-hop graph traversal:

```cypher
MATCH (e:Employee )
      -[:HAS_SKILL]->(s:Skill)
RETURN s.name AS skill
ORDER BY s.name;
```

---

## 12.4 Find Projects of an Employee

```cypher
MATCH (e:Employee)
      -[:WORKS_ON]->(p:Project)
RETURN p.name AS project
ORDER BY p.name;
```

---

## 12.5 Find Employees with a Particular Skill

```cypher
MATCH (e:Employee)-[:HAS_SKILL]->(s:Skill )
RETURN e.name AS employee
ORDER BY e.name;
```

---

## 12.6 Find Projects Requiring a Particular Skill

```cypher
MATCH (p:Project)-[:REQUIRES]->(s:Skill )
RETURN p.name AS project
ORDER BY p.name;
```

---

## 12.7 Multi-Hop Employee-Project Skill Traversal

A central graph traversal for the matching use case is:

```cypher
MATCH (p:Project)
      -[:REQUIRES]->(required:Skill)

WITH p, collect(required) AS requiredSkills

MATCH (e:Employee)
      -[:HAS_SKILL]->(skill:Skill)

WHERE skill IN requiredSkills

WITH e,
     requiredSkills,
     collect(skill) AS matchingSkills

RETURN e.name AS employee,
       size(matchingSkills) AS matchedSkills,
       size(requiredSkills) AS requiredSkills,
       (100.0 * size(matchingSkills) / size(requiredSkills)) AS matchPercentage
ORDER BY matchPercentage DESC;
```

The query first finds all skills required by the selected project, then traverses from employees to their skills and retains employees whose skills overlap with the project's required skills.

Conceptually, the matching relationship is:

```text
Project
   │
   │ REQUIRES
   ▼
 Skill
   ▲
   │ HAS_SKILL
   │
Employee
```

This demonstrates traversal across multiple nodes and relationships.

---

## 12.8 Employee → Department → Employee Traversal

The graph also supports organizational traversal.

For example, employees belonging to the same department can be discovered through their department relationship:

```cypher
MATCH (e1:Employee)-[:BELONGS_TO]->(d:Department)
      <-[:BELONGS_TO]-(e2:Employee)
WHERE e1.employeeId <> e2.employeeId
RETURN e1.name AS employee,
       d.name AS department,
       e2.name AS colleague
ORDER BY department, employee;
```

This is a relationship-oriented query that is naturally represented in the graph model.

---

## 12.9 Employee Reporting Chain

The seeded graph also contains `REPORTS_TO` relationships:

```cypher
MATCH (e:Employee)-[:REPORTS_TO]->(manager:Employee)
RETURN e.name AS employee,
       manager.name AS manager
ORDER BY manager.name;
```

---

## 12.10 Mentoring Relationships

```cypher
MATCH (mentor:Employee)-[:MENTORS]->(employee:Employee)
RETURN mentor.name AS mentor,
       employee.name AS employee
ORDER BY mentor.name;
```

---

## 12.11 Collaboration Relationships

```cypher
MATCH (e1:Employee)-[:COLLABORATES_WITH]->(e2:Employee)
RETURN e1.name AS employee1,
       e2.name AS employee2
ORDER BY employee1, employee2;
```

---

## 13. Parameterized Queries

Application queries use parameters instead of concatenating user input into Cypher.

Example:

```java
Values.parameters("employeeId", employeeId)
```

and:

```java
Values.parameters("projectName", projectName)
```

This keeps the Cypher query structure separate from user-supplied values.

---

# 14. REST API

## Employee Skill Matching

### Endpoint

```text
GET /api/match?projectName={projectName}
```

### Example

```text
GET /api/match?projectName=AI%20Recruitment%20System
```

The controller receives the project name and delegates the request to the service layer.

```java
@GetMapping("/api/match")
public List<EmployeeMatch> findEmployeesForProject(
        @RequestParam String projectName) {

    return employeeService.findEmployeesForProject(projectName);
}
```

The response is returned as JSON and consumed by the frontend.

---

# 15. Project Structure

```text
graph-database-application/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/wexa/graph/
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── MatchingController.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── Employee.java
│   │   │       │   ├── EmployeeMatch.java
│   │   │       │   ├── Project.java
│   │   │       │   ├── Skill.java
│   │   │       │   └── Department.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── EmployeeRepository.java
│   │   │       │   ├── ProjectRepository.java
│   │   │       │   └── SkillRepository.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── EmployeeService.java
│   │   │       │
│   │   │       ├── seed/
│   │   │       │   └── DataSeeder.java
│   │   │       │
│   │   │       ├── GraphDatabaseApplication.java
│   │   │       └── Neo4jConfig.java
│   │   │
│   │   └── resources/
│   │       └── static/
│   │           ├── index.html
│   │           ├── style.css
│   │           └── script.js
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

---

# 16. Configuration

The application uses environment variables for the CognoDB connection.

The connection configuration reads:

```text
COGNODB_URi
COGNODB_USERNAME
COGNODB_PASSWORD
```

The actual values must be configured locally and must not be committed to GitHub.

The Neo4j Java Driver is initialized using:

```java
GraphDatabase.driver(
    URI,
    AuthTokens.basic(USERNAME, PASSWORD)
);
```

> Note: `COGNODB_URi` is the exact environment-variable name currently used by `Neo4jConfig.java`. If the variable is renamed to `COGNODB_URI`, the Java configuration and environment variable must use the same name.

---

# 17. Running the Application

## Prerequisites

Install:

- Java 21
- Maven
- Access to the CognoDB/Neo4j database
- A configured database URI, username, and password

## Configure Environment Variables

Set:

```text
COGNODB_URi=<your database URI>
COGNODB_USERNAME=<your username>
COGNODB_PASSWORD=<your password>
```

Do not place the real password in the source code.

## Build

```bash
mvn clean package
```

## Run

```bash
mvn spring-boot:run
```

The Spring Boot application starts on:

```text
http://localhost:8080
```

Open the address in a web browser to use the Employee Skill Matcher.

---

# 18. Web Application / UI

The frontend is located under:

```text
src/main/resources/static/
```

Files:

```text
index.html
style.css
script.js
```

The UI allows a non-technical user to:

1. Select a project.
2. Request employee matches.
3. View matching employees.
4. View match percentages.
5. View matching skills.
6. Search employees.
7. Sort matching employees.

The UI also includes loading, empty, and error states.

---
# 19. UI Screenshots

## Main Application

[![Main Application](Screenshots/UI/Screenshot%20(486).png)](Screenshots/UI/Screenshot%20(486).png)

## Project Selection

[![Project Selection](Screenshots/UI/Screenshot%20(487).png)](Screenshots/UI/Screenshot%20(487).png)

## Employee Matching Results

[![Employee Matching Results](Screenshots/UI/Screenshot%20(488).png)](Screenshots/UI/Screenshot%20(488).png)

## Employee Search and Sorting

[![Employee Search and Sorting](Screenshots/UI/Screenshot%20(489).png)](Screenshots/UI/Screenshot%20(489).png)

---

# 20. Graph Database Screenshots

## Graph Data Model

[![Graph Data Model](Screenshots/Graphdatagraph/Screenshot%20(497).png)](Screenshots/Graphdatagraph/Screenshot%20(497).png)

## Employee-Skill Relationships

[![Employee-Skill Relationships](Screenshots/Graphdatagraph/Screenshot%20(498).png)](Screenshots/Graphdatagraph/Screenshot%20(498).png)

## Employee-Project Relationships

[![Employee-Project Relationships](Screenshots/Graphdatagraph/Screenshot%20(499).png)](Screenshots/Graphdatagraph/Screenshot%20(499).png)

## Project-Skill Relationships

[![Project-Skill Relationships](Screenshots/Graphdatagraph/Screenshot%20(500).png)](Screenshots/Graphdatagraph/Screenshot%20(500).png)

---

# 21. Cypher Query Results
[![Employee Skills Query](Screenshots/cyphequeries/Screenshot%20(491).png)](Screenshots/cyphequeries/Screenshot%20(491).png)

[![Employee Projects Query](Screenshots/cyphequeries/Screenshot%20(492).png)](Screenshots/cyphequeries/Screenshot%20(492).png)

[![Project Skills Query](Screenshots/cyphequeries/Screenshot%20(493).png)](Screenshots/cyphequeries/Screenshot%20(493).png)

[![Multi-Hop Graph Traversal](Screenshots/cyphequeries/Screenshot%20(494).png)](Screenshots/cyphequeries/Screenshot%20(494).png)

[![Employee Skill Matching Query](Screenshots/cyphequeries/Screenshot%20(495).png)](Screenshots/cyphequeries/Screenshot%20(495).png)

The recording will demonstrate:

1. Starting the application.
2. Opening the web application.
3. Selecting a project.
4. Running employee matching.
5. Viewing matching employees.
6. Viewing match percentages and skills.
7. Demonstrating the graph database/Cypher query.

---

# 23. Error Handling

The application provides basic handling for common UI/API situations.

### Empty Project Selection

If the user does not select a project, the UI asks the user to select one.

### No Matching Employees

If the API returns no employees, the UI displays an appropriate empty state.

### API / Server Error

If the backend request fails, the UI displays an error message and allows the user to retry.

### Loading State

While the API request is being processed, the UI displays a loading indicator.

---

# 24. Design Decisions

### Graph Database

A graph database was selected because the primary use case involves relationships among employees, skills, projects, departments, and employee-to-employee connections.

### Repository Layer

Database access is separated into repository classes:

```text
EmployeeRepository
ProjectRepository
SkillRepository
```

### Service Layer

`EmployeeService` coordinates employee, project, skill, and matching operations.

### Controller Layer

`MatchingController` exposes the employee-project matching REST endpoint.

### Parameterized Cypher

Cypher parameters are used instead of concatenating input directly into query strings.

### Seeded Dataset

A generated dataset of 350 employees provides enough data to demonstrate graph traversal and matching behavior beyond a minimal sample.

---

# 25. Future Improvements

Potential improvements include:

- Employee profile pages
- Skill-gap analysis
- Project recommendations for employees
- Team recommendations
- Department-based filtering
- Experience-based matching
- Weighted skill matching
- Graph visualization inside the application
- Authentication and authorization
- Pagination for large employee datasets
- More advanced employee collaboration analysis

---

# 26. Author

**Vishnu Reddy**

B.Tech – Computer Science Engineering  
Specialization – Artificial Intelligence and Machine Learning
