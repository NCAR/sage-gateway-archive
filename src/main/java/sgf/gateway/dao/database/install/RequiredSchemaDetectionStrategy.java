package sgf.gateway.dao.database.install;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

public class RequiredSchemaDetectionStrategy implements NewDatabaseDetectionStrategy {

    private static final String SCHEMA_COUNT_QUERY = "select count(*) from pg_namespace where nspname = :name;";

    private String requiredSchema;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public RequiredSchemaDetectionStrategy(DataSource dataSource, String requiredSchema) {
        super();
        this.requiredSchema = requiredSchema;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean isNewDatabase() {

        Map namedParameters = Collections.singletonMap("name", requiredSchema);

        int schemaCount = this.jdbcTemplate.queryForInt(SCHEMA_COUNT_QUERY, namedParameters);

        boolean result = true;

        if (1 == schemaCount) {
            result = false;
        }

        return result;
    }

}
