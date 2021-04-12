package ru.mipt.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class ParticleRepository {
    private final ObjectMapper mapper;
    private final JdbcTemplate template;
    private final String insert = "CREATE (n:PARTICLE {name:?,alias:?,mass:?})";
    private final String getMothers = "MATCH (:PARTICLE {name: ?})<-[IS_MOTHER_OF]-(PARTICLE) RETURN PARTICLE";
    private final String getParticleByName = "MATCH (n:PARTICLE)  where n.name =~ ? " +
            "WITH {id:n.id," +
            "name:n.name, " +
            "alias:n.alias, " +
            "mass:n.mass} AS Particle " +
            "RETURN Particle";
    private final static RowMapper<String> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) -> {
       return rs.getString(1);
    });

    public void saveParticle(ParticleEntry entry) {
        template.update(insert, entry.getName(), entry.getAlias(), entry.getMass());
    }

    public Particle getParticleByName(String name) {
        return convertToParticle(template.queryForObject(getParticleByName, new Object[]{name}, PARTICLE_ENTRY_ROW_MAPPER));
    }

    public List<Particle> getMothers(Particle particle) {
        return template.query(getMothers, new Object[]{particle.getName()}, PARTICLE_ENTRY_ROW_MAPPER).stream().map(this::convertToParticle).collect(toList());
    }

    private Particle convertToParticle(String fromDb) {
        try {
            ParticleEntry entry = mapper.readValue(fromDb, ParticleEntry.class);
            ArrayList<String> aliases =  new ArrayList<>(asList(entry.getAlias().substring(1, entry.getAlias().length() - 1).split(", ")));
            return new Particle(entry.getId(), entry.getName(), aliases, entry.getMass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
