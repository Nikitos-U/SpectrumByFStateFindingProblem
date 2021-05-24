package ru.mipt.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class FstateRepositoryImpl implements FstateRepository {
    private final JdbcTemplate template;
    private final ObjectMapper mapper;

    private final String insertParticle = "INSERT INTO PARTICLES(id, name, aliases, mass, particle) values (?, ?, ?, ?,?)";
    private final String getParticles = "SELECT * FROM PARTICLES";
    private final String getParticleById = "SELECT * FROM PARTICLES where id = ?";
    private final String getParticleByName = "SELECT * FROM PARTICLES where name = ?";

    private final String insertDecay = "INSERT INTO DECAYS(id, probability, mass, particles_names, mother_particle_name, decay) VALUES (?, ?, ?, ?, ?, ?)";
    private final String getDecays = "SELECT * FROM DECAYS";
    private final String getDecayById = "SELECT * FROM DECAYS where id = ?";
    private final String getDecayByParticles = "SELECT * FROM DECAYS where(particles_names = ?)";

    private final String insertCascade = "INSERT INTO CASCADES(ID, PARTICLE_LIST, HISTORY, CASCADE) values (?, ?, ?, ?)";
    private final String getCascadeById = "SELECT * FROM CASCADES where id = ?";
    private final String getCascadeByName = "SELECT * FROM CASCADES where particle_list = ?";

    private final static RowMapper<String> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString("particle"));
    private final static RowMapper<String> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString("decay"));
    private final static RowMapper<String> CASCADE_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString("cascade"));

    @Override
    public void saveParticle(Particle entry) {
        try {
            template.update(insertParticle, entry.getId().toString(), entry.getName(), entry.getAliases(), entry.getMass(), mapper.writeValueAsString(entry));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Particle getParticleById(String id) {
        return convertToParticle(template.queryForObject(getParticleById, new Object[]{id}, PARTICLE_ENTRY_ROW_MAPPER));
    }

    @Override
    public void saveDecay(Decay entry) {
        try {
            StringBuilder particlesNames = new StringBuilder();
            entry.getParticles()
                    .stream()
                    .map(Particle::getName)
                    .collect(toList())
                    .forEach(particlesNames::append);
            String particles = particlesNames.toString();
            template.update(insertDecay, entry.getId(), entry.getProbability(),
                    entry.getMass(), particles, entry.getMotherParticle().getName(), mapper.writeValueAsString(entry));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCascade(Cascade cascade) {
    }

    @Override
    public List<Particle> getParticles() {
        return template.query(getParticles, PARTICLE_ENTRY_ROW_MAPPER).stream().map(this::convertToParticle).collect(toList());
    }

    @Override
    public List<Decay> getDecays() {
        return template.query(getDecays, DECAY_ENTRY_ROW_MAPPER).stream().map(this::convertToDecay).collect(toList());
    }

    @Override
    public List<Cascade> getCascades() {
        return null;
    }

    private Particle convertToParticle(String fromDb) {
        try {
            return mapper.readValue(fromDb, Particle.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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
