package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.mipt.dao.ParticleColumns.*;

@Repository
@RequiredArgsConstructor
public class ParticleRepository {
    private final JdbcTemplate template;
    private final String insert = "CREATE (n:PARTICLE {name:?,alias:?,mass:?})";
    private final String getMothers = "MATCH (:PARTICLE   {name=~ ?)<-[IS_MOTHER_OF]-(PARTICLE) RETURN PARTICLE";
    private final String getParticleByName = "MATCH (n:PARTICLE)  where n.name =~ ? RETURN n";
    private final static RowMapper<Particle> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) -> {
        ParticleEntry entry = new ParticleEntry(rs.getLong(ID.column()), rs.getString(NAME.column()),
                rs.getString(ALIAS.column()), rs.getDouble(MASS.column()));
        ArrayList<String> aliases = new ArrayList<>(asList(entry.getAlias().substring(1, entry.getAlias().length() - 1).split(", ")));
        return new Particle(entry.getId(), entry.getName(), aliases, entry.getMass());
    });

    public void saveParticle(ParticleEntry entry) {
        template.update(insert, entry.getName(), entry.getAlias(), entry.getMass());
    }

    public Particle getParticleByName(String name) {
        return template.queryForObject(getParticleByName, new Object[]{name}, PARTICLE_ENTRY_ROW_MAPPER);
    }

    public List<Particle> getMothers(Particle particle) {
        return template.query(getMothers, new Object[]{particle.getName()}, PARTICLE_ENTRY_ROW_MAPPER);
    }

    private Particle convertToParticle(ParticleEntry entry) {
        ArrayList<String> aliases = new ArrayList<>(asList(entry.getAlias().substring(1, entry.getAlias().length() - 1).split(", ")));
        return new Particle(entry.getId(), entry.getName(), aliases, entry.getMass());
    }
}
