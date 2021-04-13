package ru.mipt.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class DecayRepository {
    private final ObjectMapper mapper;
    private final JdbcTemplate template;
    private final String create = "CREATE (n:DECAY {mother_particle:?, probability:?, mass:?, particles:?})";
    private final String createRelation = "MATCH (a:PARTICLE), (b:PARTICLE) WHERE a.name=~ ? AND b.name=~ ? CREATE (a)-[r:IS_MOTHER_OF]->(b)";
    private final String getDecaysFromMotherParticle = "MATCH (n:DECAY)  where n.mother_particle =~ ? RETURN n";
    private final String getDecayByParticles = "MATCH (n:DECAY)  where n.particles =~ ? RETURN n";

    private final String get = "SELECT * FROM DECAYS where(PARTICLES = :particles)";

    private final static RowMapper<String> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString(1));

    public void save(DecayEntry entry) {
        template.update(create, entry.getMotherParticle(), entry.getProbability(), entry.getMass(), entry.getParticles());
    }

    public List<Decay> getMothersAsDecays(Particle particle) {
        String motherString = null;
        try {
            motherString = mapper.writeValueAsString(particle);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        motherString = "\"\\" + motherString.substring(0, motherString.length() - 1) + "\\}\"";

        return template.query(getDecaysFromMotherParticle, new Object[]{motherString}, DECAY_ENTRY_ROW_MAPPER)
                .stream()
                .map(this::convertToDecay)
                .collect(toList());
    }

    public List<Decay> getDecaysByParticles(List<Particle> particles) {
        return template.query(getDecayByParticles, new Object[]{particles}, DECAY_ENTRY_ROW_MAPPER)
                .stream()
                .map(this::convertToDecay)
                .collect(toList());
    }

    public void saveRelations(String decayName, String particleName) {
        template.update(createRelation, decayName, particleName);
    }

    private Decay convertToDecay(String fromDb) {
        try {
            return mapper.readValue(fromDb, Decay.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
