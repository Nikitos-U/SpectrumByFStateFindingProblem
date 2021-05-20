//package ru.mipt.dao;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import java.util.Collection;
//
//import static ru.mipt.dao.CascadeColumns.*;
//
//@RequiredArgsConstructor
//public class CascadesRepository {
//    private final NamedParameterJdbcTemplate template;
//    private final String insert = "";
//    private final String get = "";
//    private final static RowMapper<CascadeEntry> CASCADE_ENTRY_ROW_MAPPER = ((rs, rowNum) ->
//            new CascadeEntry(rs.getArray(PARTICLES.column()),
//                    rs.getArray(HISTORY.column()), rs.getDouble(MASS.column())));
//
//    public void save(CascadeEntry cascadeEntry) {
//        MapSqlParameterSource params = new MapSqlParameterSource(PARTICLES.param(), cascadeEntry.getParticles())
//                .addValue(HISTORY.param(), cascadeEntry.getHistory())
//                .addValue(MASS.param(), cascadeEntry.getMass());
//        template.update(insert, params);
//    }
//
//    public Collection<CascadeEntry> findAllByParticles(String query) {
//        return template.query(get, CASCADE_ENTRY_ROW_MAPPER);
//    }
//}
