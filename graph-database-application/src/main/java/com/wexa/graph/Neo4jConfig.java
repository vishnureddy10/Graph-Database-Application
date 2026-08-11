package com.wexa.graph;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {

    private final String URI = System.getenv("COGNODB_URi");
    private final String USERNAME = System.getenv("COGNODB_USERNAME");
    private final String PASSWORD = System.getenv("COGNODB_PASSWORD");

    @Bean
    public Driver neo4jDriver() {

        return GraphDatabase.driver(
                URI,
                AuthTokens.basic(USERNAME, PASSWORD)
        );
    }
}