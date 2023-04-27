package pl.zajavka.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.OpinionRepository;
import pl.zajavka.domain.Opinion;
import pl.zajavka.infrastructure.configuration.DatabaseConfiguration;

import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class OpinionDatabaseRepository implements OpinionRepository {

    private final SimpleDriverDataSource simpleDriverDataSource;
    private final DatabaseMapper databaseMapper;

    @Override
    public Opinion create(Opinion opinion) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.OPINION_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.OPINION_TABLE_PKEY.toLowerCase());

        Map<String, ?> params = databaseMapper.map(opinion);
        Number opinionId = jdbcInsert.executeAndReturnKey(params);
        return opinion.withId((long) opinionId.intValue());
    }
}
