package ru.mipt.newAlgoEffort;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.mipt.DecaysFinder;
import ru.mipt.ParticleCombinator;
import ru.mipt.ProbableParticlesMaker;
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
    public JdbcDataSource dataSource() {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:~/test");
            dataSource.setUser("sa");
            dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(JdbcDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ParticleRepository particleRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        return new ParticleRepository(mapper, jdbcTemplate);
    }

    @Bean
    public DecayRepository dRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        return new DecayRepository(mapper, jdbcTemplate);
    }

    @Bean
    public ProbableParticlesMaker maker(DecayRepository decayRepository) {
        return new ProbableParticlesMaker(decayRepository);
    }

    @Bean
    public ParticleCombinator combinator(ParticleRepository particleRepository) {
        return new ParticleCombinator(particleRepository);
    }

    @Bean
    public DecaysFinder finder(ParticleCombinator combinator, ProbableParticlesMaker maker) {
        return new DecaysFinder(combinator, maker);
    }
}
