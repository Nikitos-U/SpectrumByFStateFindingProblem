package ru.mipt.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class JdbcConfig {

    public JdbcTemplate configure() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        createTables(jdbcTemplate);
        return jdbcTemplate;
    }

    private void createTables(JdbcTemplate jdbcTemplate) {
        System.out.println("Creating tables");
        jdbcTemplate.execute("drop table particles if exists");
        jdbcTemplate.execute("create table particles(" +
                "id serial, name varchar(255), alias varchar(255))");
        jdbcTemplate.execute("drop table decays if exists");
        jdbcTemplate.execute("create table decays(" +
                "id serial, MotherParticle varchar(255), probability double, mass double)");
    }
}
