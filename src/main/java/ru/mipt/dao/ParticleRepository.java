package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static ru.mipt.dao.ParticleColumns.*;

@RequiredArgsConstructor
public class ParticleRepository {
    private final NamedParameterJdbcTemplate template;
    private final String insert = "INSERT INTO PARTICLES(name, alias, mass) values (:name, :alias, :mass)";
    private final String get = "";
    private final static RowMapper<ParticleEntry> PARTICLE_ENTRY_ROW_MAPPER = ((rs, rowNum) ->
            new ParticleEntry(rs.getString(NAME.column()),
                    rs.getString(ALIAS.column()), rs.getDouble(MASS.column())));

    public void save(ParticleEntry entry) {
        MapSqlParameterSource params = new MapSqlParameterSource(NAME.param(), entry.getName())
                .addValue(ALIAS.param(), entry.getAlias())
                .addValue(MASS.param(), entry.getMass());
        template.update(insert, params);
    }

    public ParticleEntry find(String name) {
        return template.queryForObject(get, new MapSqlParameterSource(NAME.param(), name), PARTICLE_ENTRY_ROW_MAPPER);
    }
}
