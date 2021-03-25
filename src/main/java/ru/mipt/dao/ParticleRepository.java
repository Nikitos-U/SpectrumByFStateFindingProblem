package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.mipt.dao.ParticleColumns.*;

@RequiredArgsConstructor
public class ParticleRepository implements FstateRepository {
    private final JdbcTemplate template;
    private final String insert = "CREATE (n:PARTICLE {id:?,name:?,alias:?,mass:?})";
    private final static RowMapper<ParticleEntry> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) ->
            new ParticleEntry(rs.getLong(ID.column()),rs.getString(NAME.column()),
                    rs.getString(ALIAS.column()), rs.getDouble(MASS.column())));

    public void saveParticle(ParticleEntry entry) {
        template.update(insert, entry.getName(), entry.getAlias(), entry.getMass());
    }

    @Override
    public Particle getParticleByName(String name) {
        return null;
    }

    @Override
    public List<Decay> getChilds(Particle particle) {
        return null;
    }

    @Override
    public List<Particle> getMothers(Particle particle) {
        return null;
    }

    @Override
    public List<Decay> getDecaysByParticles(List<Particle> particles) {
        return null;
    }

    private Particle convertToParticle(ParticleEntry entry) {
        ArrayList<String> aliases = new ArrayList<>(asList(entry.getAlias().substring(1, entry.getAlias().length() - 1).split(", ")));
        return new Particle(entry.getId(), entry.getName(), aliases, entry.getMass());
    }
}
