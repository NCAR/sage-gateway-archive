package sgf.gateway.utils.hibernate;

import org.hibernate.dialect.PostgreSQL82Dialect;

import java.sql.Types;

/**
 * The Class ExtendedPostgreSQLDialect.
 */
public class ExtendedPostgreSQLDialect extends PostgreSQL82Dialect {

    /**
     * Instantiates a new extended postgre sql dialect.
     */
    public ExtendedPostgreSQLDialect() {

        super();
        registerColumnType(Types.OTHER, "uuid");
    }
}
