package ru.mipt.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.mipt.Particle;

import java.util.List;

import static ru.mipt.dao.DecayColumns.*;


@RequiredArgsConstructor
public class DecayRepository {
    private final JdbcTemplate template;
    private final String create = "CREATE (n:DECAY {mother_particle:?, probability:?, mass:?, particles:?})";
    private final String get = "SELECT * FROM DECAYS where(PARTICLES = :particles)";
    private final ObjectMapper mapper = new ObjectMapper();

    private final static RowMapper<DecayEntry> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) ->
            new DecayEntry(rs.getString(PARTICLES.column()),
                    rs.getString(MOTHER_PARTICLE.column()), rs.getDouble(PROBABILITY.column()), rs.getDouble(MASS.column())));

    public void save(DecayEntry entry) {
        template.update(create, entry.getMotherParticle(), entry.getProbability(), entry.getMass(), entry.getParticles());
    }

//    public List<DecayEntry> findByParticles(List<Particle> entry) {
//        return template.query(get, new MapSqlParameterSource(PARTICLES.param(), entry.toString()), DECAY_ENTRY_ROW_MAPPER);
//    }
}
