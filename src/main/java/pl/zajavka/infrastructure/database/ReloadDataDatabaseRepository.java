package pl.zajavka.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.ReloadDataRepository;

@Slf4j
@Repository
@AllArgsConstructor
public class ReloadDataDatabaseRepository implements ReloadDataRepository {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Override
    public void run(String sql) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        int result = jdbcTemplate.update(sql);
        log.debug("Inserted bootstrap data rows: [{}]", result);
    }
}
