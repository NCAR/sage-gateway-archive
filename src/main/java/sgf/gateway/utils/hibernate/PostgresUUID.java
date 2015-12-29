package sgf.gateway.utils.hibernate;

import org.postgresql.util.PGobject;

import java.sql.SQLException;

/**
 * The Class PostgresUUID.
 */
public class PostgresUUID extends PGobject {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new postgres uuid.
     *
     * @param uuid the uuid
     * @throws SQLException the SQL exception
     */
    public PostgresUUID(String uuid) throws SQLException {

        super();
        setType("uuid");
        setValue(uuid);
    }
}
