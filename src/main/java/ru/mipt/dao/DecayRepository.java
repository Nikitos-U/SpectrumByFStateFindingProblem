package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.mipt.Particle;

import java.util.List;

import static ru.mipt.dao.DecayColumns.*;


@RequiredArgsConstructor
public class DecayRepository {
    private final NamedParameterJdbcTemplate template;
    private final String insert = "INSERT INTO DECAYS(mother_particle, probability, mass, particles)" +
            " VALUES (:mother_particle, :probability, :mass, :particles)";
    private final String get = "SELECT * FROM DECAYS where(PARTICLES = :particles)";

    private final static RowMapper<DecayEntry> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) ->
            new DecayEntry(rs.getString(PARTICLES.column()),
                    rs.getString(MOTHER_PARTICLE.column()), rs.getDouble(PROBABILITY.column()), rs.getDouble(MASS.column())));

    public void save(DecayEntry entry) {
        MapSqlParameterSource params = new MapSqlParameterSource(MOTHER_PARTICLE.param(), entry.getMotherParticle())
                .addValue(MASS.param(), entry.getMass())
                .addValue(PARTICLES.param(), entry.getParticles())
                .addValue(PROBABILITY.param(), entry.getProbability());
        template.update(insert, params);
    }

    public List<DecayEntry> findByParticles(List<Particle> entry) {
        return template.query(get, new MapSqlParameterSource(PARTICLES.param(), entry.toString()), DECAY_ENTRY_ROW_MAPPER);
    }
}
