package ru.mipt.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.dao.DecayColumns.*;

@Repository
@RequiredArgsConstructor
public class DecayRepository {
    private final JdbcTemplate template;
    private final String create = "CREATE (n:DECAY {mother_particle:?, probability:?, mass:?, particles:?})";
    private final String createRelation = "MATCH (a:PARTICLE), (b:PARTICLE) WHERE a.name=~ ? AND b.name=~ ? CREATE (a)-[r:IS_MOTHER_OF]->(b)";
    private final String getDecaysFromMotherParticle = "MATCH (n:DECAY)  where n.mother_particle =~ ? RETURN n";
    private final String getDecayByPArticles = "MATCH (n:DECAY)  where n.particles =~ ? RETURN n";

    private final String get = "SELECT * FROM DECAYS where(PARTICLES = :particles)";
    private final ObjectMapper mapper = new ObjectMapper();

    private final static RowMapper<Decay> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) -> {
        ArrayList<Particle> particles = new ArrayList<>((List<Particle>) rs.getObject(PARTICLES.column()));
        return new Decay((Particle) rs.getObject(MOTHER_PARTICLE.column()), particles, rs.getDouble(PROBABILITY.column()));
    });

    public void save(DecayEntry entry) {
        template.update(create, entry.getMotherParticle(), entry.getProbability(), entry.getMass(), entry.getParticles());
    }

    public List<Decay> getMothersAsDecays(Particle particle) {
        return template.query(getDecaysFromMotherParticle, new Object[]{particle.getName()}, DECAY_ENTRY_ROW_MAPPER);
    }

    public List<Decay> getDecaysByParticles(List<Particle> particles) {
        return template.query(getDecaysFromMotherParticle, new Object[]{particles}, DECAY_ENTRY_ROW_MAPPER);
    }

    public void saveRelations(String decayName, String particleName) {
        template.update(createRelation, decayName, particleName);
    }

//    public List<DecayEntry> findByParticles(List<Particle> entry) {
//        return template.query(get, new MapSqlParameterSource(PARTICLES.param(), entry.toString()), DECAY_ENTRY_ROW_MAPPER);
//    }
}
