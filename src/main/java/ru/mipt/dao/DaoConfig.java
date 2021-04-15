package ru.mipt.dao;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DaoConfig {
    @Getter
    private final JdbcTemplate jdbcTemplate;
//    private static final String url = "jdbc:neo4j:http://localhost:7687/New_Fstate";
//    private static final String user = "sa";
//    private static final String password = "";

    public DaoConfig() {
        String NEO4J_URL = System.getenv("NEO4J_URL");
        if (NEO4J_URL == null) NEO4J_URL = System.getProperty("NEO4J_URL", "jdbc:neo4j:http://localhost:7474/");
        DriverManagerDataSource dataSource = new DriverManagerDataSource(NEO4J_URL);
        jdbcTemplate = new JdbcTemplate(dataSource);
//        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
