package ru.mipt.dao;

import lombok.Getter;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoConfig {
    @Getter
    private final JdbcTemplate jdbcTemplate;

    public DaoConfig() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
