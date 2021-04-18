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
    private final String create = "CREATE (n:DECAY {probability:?, mass:?, particles:?, mother_particle_name:?, decay:?})";
    private final String createRelation = "MATCH (a:PARTICLE), (b:PARTICLE) WHERE a.name=~ ? AND b.name=~ ? CREATE (a)-[r:IS_MOTHER_OF]->(b)";
    private final String getDecaysFromMotherParticle = "MATCH (n:DECAY)  where n.mother_particle_name =~ ? RETURN n.decay";
    private final String getDecayByParticles = "MATCH (n:DECAY)  where n.particles =~ ? RETURN n.decay";

    private final String get = "SELECT * FROM DECAYS where(PARTICLES = :particles)";

    private final static RowMapper<String> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString(1));

    public void save(Decay entry) {
        try {
            template.update(create, entry.getProbability(),
                    entry.getMass(), entry.getParticles().stream().map(Particle::getName).collect(toList()), entry.getMotherParticle().getName(), mapper.writeValueAsString(entry));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Decay> getMothersAsDecays(Particle particle) {
        return template.query(getDecaysFromMotherParticle, new Object[]{particle.getName()}, DECAY_ENTRY_ROW_MAPPER)
                .stream()
                .map(this::convertToDecay)
                .collect(toList());
    }

    public List<Decay> getDecaysByParticles(List<Particle> particles) {
        try {
            String param = mapper.writeValueAsString(particles);
            return template.query(getDecayByParticles, new Object[]{param}, DECAY_ENTRY_ROW_MAPPER)
                    .stream()
                    .map(this::convertToDecay)
                    .collect(toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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
