//package ru.mipt.dao;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//import ru.mipt.Decay;
//import ru.mipt.Particle;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//
//@Repository
//@RequiredArgsConstructor
//public class DecayRepository {
//    private final ObjectMapper mapper;
//    private final JdbcTemplate template;
//    private final String create =  "INSERT INTO DECAYS(id, probability, mass, particles_names, mother_particle_name, decay) " +
//            "VALUES (?, ?, ?, ?, ?, ?)";;
//    private final String createRelation = "MATCH (a:PARTICLE), (b:PARTICLE) WHERE a.name=~ ? AND b.name=~ ? CREATE (a)-[r:IS_MOTHER_OF]->(b)";
//    private final String getDecaysFromMotherParticle = "SELECT * FROM DECAYS where(mother_particle_name = ?)";
//    private final String getDecayByParticles = "SELECT * FROM DECAYS where(particles_names = ?)";
//
//    private final static RowMapper<String> DECAY_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString("decay"));
//
//    public void save(Decay entry) {
//        try {
//            StringBuilder particlesNames = new StringBuilder();
//            entry.getParticles()
//                    .stream()
//                    .map(Particle::getName)
//                    .collect(toList())
//                    .forEach(particlesNames::append);
//            String particles = particlesNames.toString();
//            template.update(create, entry.getId().toString(), entry.getProbability(),
//                    entry.getMass(), particlesNames, entry.getMotherParticle().getName(), mapper.writeValueAsString(entry));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<Decay> getMothersAsDecays(Particle particle) {
//        return template.query(getDecaysFromMotherParticle, new Object[]{particle.getName()}, DECAY_ENTRY_ROW_MAPPER)
//                .stream()
//                .map(this::convertToDecay)
//                .collect(toList());
//    }
//
//    public List<Decay> getDecaysByParticles(List<Particle> particles) {
//        StringBuilder particlesNames = new StringBuilder();
//        particles
//                .stream()
//                .map(Particle::getName)
//                .collect(toList())
//                .forEach(particlesNames::append);
//            return template.query(getDecayByParticles, new Object[]{particlesNames.toString()}, DECAY_ENTRY_ROW_MAPPER)
//                    .stream()
//                    .map(this::convertToDecay)
//                    .collect(toList());
//    }
//
//    public void saveRelations(String decayName, String particleName) {
//        template.update(createRelation, decayName, particleName);
//    }
//
//    private Decay convertToDecay(String fromDb) {
//        try {
//            return mapper.readValue(fromDb, Decay.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
