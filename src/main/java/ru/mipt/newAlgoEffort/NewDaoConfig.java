package ru.mipt.newAlgoEffort;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.mipt.DecaysFinder;
import ru.mipt.ParticleCombinator;
import ru.mipt.ProbableParticlesMaker;
import ru.mipt.dao.DbPatcher;
import ru.mipt.dao.DecayRepository;
import ru.mipt.dao.ParticleRepository;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;
import ru.mipt.starters.DBStarter;

import javax.sql.DataSource;
import java.io.FileNotFoundException;

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

    @Bean
    public DecayParser decayParser(DecayRepository repository) throws FileNotFoundException {
        return new DecayParser(repository);
    }

    @Bean
    public ParticleParser particleParser(ParticleRepository repository) throws FileNotFoundException {
        return new ParticleParser(repository);
    }

    @Bean
    public DbPatcher patcher(JdbcTemplate template) {
        return new DbPatcher(template);
    }

    @Bean
    public DBStarter dbStarter(DecayParser parser, ParticleParser particleParser, DbPatcher patcher) {
        return new DBStarter(parser, particleParser, patcher);
    }
}
