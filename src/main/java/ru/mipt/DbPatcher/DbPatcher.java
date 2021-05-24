package ru.mipt.DbPatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbPatcher {

    public static void createTables(JdbcTemplate template) {
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
        template.update("CREATE TABLE CASCADES (" +
                "  FSTATE VARCHAR(1000), " +
                " HISTORY CLOB" +
                ");");
    }
}
