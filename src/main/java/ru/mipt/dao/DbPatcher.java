package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class DbPatcher {
    private final DaoConfig  config = new DaoConfig();
    private final JdbcTemplate template = config.getJdbcTemplate();

    public void createTables() {
        template.update("CREATE TABLE PARTICLES (" +
                "  id VARCHAR2(250), " +
                "  name VARCHAR2(250)," +
                "  aliases CLOB, " +
                "  mass DOUBLE," +
                "  particle VARCHAR2(5000)" +
                ");");
        template.update("CREATE TABLE DECAYS (" +
                "  id VARCHAR2(250), " +
                "  probability INT NOT NULL," +
                "  mass DOUBLE," +
                "particles_names VARCHAR2(500), " +
                "mother_particle_name VARCHAR2(250)," +
                "decay VARCHAR2(5000)" +
                ");");
    }
}
