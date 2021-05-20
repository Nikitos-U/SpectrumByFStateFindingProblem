package ru.mipt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
    public DataSource dataSource() {
        return  new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    @Bean
//    public ParticleRepository particleRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
//        return new ParticleRepository(mapper, jdbcTemplate);
//    }
//
//    @Bean
//    public DecayRepository dRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
//        return new DecayRepository(mapper, jdbcTemplate);
//    }

    @Bean
    public DecayParser decayParser(HashMap<String, Particle> parsedParticles) throws FileNotFoundException {
        return new DecayParser(parsedParticles);
    }

    @Bean
    public ParticleParser particleParser() throws FileNotFoundException {
        return new ParticleParser();
    }

    @Bean
    public HashMap<String, Particle> parsedParticles(ParticleParser particleParser) {
        return particleParser.parse();
    }

    @Bean
    public MultiValuedMap<List<Particle>, Decay> parsedDecays(DecayParser decayParser) throws IOException {
        return decayParser.parse();
    }
}
