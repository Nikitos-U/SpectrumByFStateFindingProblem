package ru.mipt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.mipt.dao.FstateRepository;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public JdbcDataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DecayParser decayParser(Map<String, Particle> parsedParticles, FstateRepository fstateRepository) throws FileNotFoundException {
        return new DecayParser(parsedParticles, fstateRepository);
    }

    @Bean
    public Map<String, Particle> parsedParticles(FstateRepository repository, ParticleParser particleParser) {
        List<Particle> parsedParticles = repository.getParticles();
        return parsedParticles.isEmpty() ? particleParser.parse() : parsedParticles.stream().collect(Collectors.toMap(Particle::getName, Function.identity()));
    }

    @Bean
    public MultiValuedMap<List<Particle>, Decay> parsedDecays(FstateRepository repository, DecayParser decayParser) throws IOException {
        List<Decay> parsedDecays = repository.getDecays();
        if (!parsedDecays.isEmpty()) {
            MultiValuedMap<List<Particle>, Decay> result = new ArrayListValuedHashMap<>();
            parsedDecays.forEach(decay -> result.put(decay.getParticles(), decay));
            return result;
        } else {
            return decayParser.parse();
        }
    }
}
