package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.Objects;

import static java.util.Arrays.asList;
import static ru.mipt.dao.ParticleColumns.*;

@RequiredArgsConstructor
public class ParticleRepository {
    private final JdbcTemplate template;
    private final String insert = "CREATE (n:PARTICLE {name:?,alias:?,mass:?})";
    private final String get = "SELECT * FROM PARTICLES where name = :name or ALIAS = :name";
    private final static RowMapper<ParticleEntry> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) ->
            new ParticleEntry(rs.getString(NAME.column()),
                    rs.getString(ALIAS.column()), rs.getDouble(MASS.column())));

    public void save(ParticleEntry entry) {
        template.update(insert, entry.getName(), entry.getAlias(), entry.getMass());
    }
//
//    public Particle findByName(String name) {
//        return convertToParticle(Objects.requireNonNull(template.queryForObject(get, name, name,)));
//    }

    private Particle convertToParticle(ParticleEntry entry) {
        ArrayList<String> aliases = new ArrayList<>(asList(entry.getAlias().substring(1, entry.getAlias().length() - 1).split(", ")));
        return new Particle(entry.getName(), aliases, entry.getMass());
    }
}
