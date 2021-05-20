//package ru.mipt.dao;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//import ru.mipt.Particle;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//
//@Repository
//@RequiredArgsConstructor
//public class ParticleRepository {
//    private final ObjectMapper mapper;
//    private final JdbcTemplate template;
//    private final String insert = "INSERT INTO PARTICLES(id, name, aliases, mass, particle) values (?, ?, ?, ?,?)";
//    private final String getMothers = "MATCH (:PARTICLE {name: ?})<-[IS_MOTHER_OF]-(PARTICLE) RETURN PARTICLE";
//    private final String getParticleByName = "SELECT * FROM PARTICLES where name = ?";
//    private final String getParticleById = "SELECT * FROM PARTICLES where id = ?";
//    private final static RowMapper<String> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) -> rs.getString("particle"));
//
//    public void saveParticle(Particle entry) {
//        try {
//            template.update(insert, entry.getId().toString(), entry.getName(), entry.getAliases(), entry.getMass(), mapper.writeValueAsString(entry));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Particle getParticleByName(String name) {
//        return convertToParticle(template.queryForObject(getParticleByName, new Object[]{name}, PARTICLE_ENTRY_ROW_MAPPER));
//    }
//
//    public Particle getParticleById(String id) {
//        return convertToParticle(template.queryForObject(getParticleById, new Object[]{id}, PARTICLE_ENTRY_ROW_MAPPER));
//    }
//
//    public List<Particle> getMothers(Particle particle) {
//        return template.query(getMothers, new Object[]{particle.getName()}, PARTICLE_ENTRY_ROW_MAPPER).stream().map(this::convertToParticle).collect(toList());
//    }
//
//    private Particle convertToParticle(String fromDb) {
//        try {
//            return mapper.readValue(fromDb, Particle.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
