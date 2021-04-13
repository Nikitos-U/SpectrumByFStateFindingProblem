package ru.mipt.newAlgoEffort;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.mipt.dao.DecayRepository;
import ru.mipt.dao.ParticleRepository;

@Configuration
public class NewDaoConfig {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return mapper;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        String NEO4J_URL = System.getenv("NEO4J_URL");
        if (NEO4J_URL == null) NEO4J_URL = System.getProperty("NEO4J_URL", "jdbc:neo4j:http://localhost:7474/");
        DriverManagerDataSource dataSource = new DriverManagerDataSource(NEO4J_URL);
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    public ParticleRepository particleRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        return new ParticleRepository(mapper, jdbcTemplate);
    }

    @Bean
    public DecayRepository dRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        return new DecayRepository(mapper, jdbcTemplate);
    }
}
