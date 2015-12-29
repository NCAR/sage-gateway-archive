package eske.liquibase.persistentids;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class AddPersistentIdsToEmptyFiles implements CustomTaskChange {

    /**
     * {@inheritDoc}
     */
    public void execute(Database database) {

        JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
        Connection liquibaseConnection = jdbcConnection.getUnderlyingConnection();
        DataSource dataSource = new SingleConnectionDataSource(liquibaseConnection, true);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final String existingEmptyFilesStatement = "SELECT id FROM metadata.logical_file WHERE "
                + "id NOT IN (SELECT resource_id FROM metadata.persistent_identifier WHERE type_id=0);";
        final String createPersistentIdStatement = "INSERT INTO metadata.persistent_identifier(identifier, type_id, resource_id) "
                + "VALUES (?, 0, ?); ";

        jdbcTemplate.query(existingEmptyFilesStatement, new RowCallbackHandler() {

            public void processRow(final ResultSet resultSet) throws SQLException {

                jdbcTemplate.execute(createPersistentIdStatement, new PreparedStatementCallback() {

                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        String uuid = resultSet.getString("id");
                        preparedStatement.setString(1, "empty.file." + uuid);
                        preparedStatement.setObject(2, resultSet.getObject("id"), Types.OTHER);

                        preparedStatement.execute();

                        return null;
                    }

                });
            }

        });

    }

    /**
     * {@inheritDoc}
     */
    public String getConfirmationMessage() {

        return "Empty file placeholders attached to all datasets.";
    }

    /**
     * {@inheritDoc}
     */
    public void setUp() throws SetupException {

    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    public ValidationErrors validate(Database arg0) {
        return new ValidationErrors();
    }

    @Override
    public void setFileOpener(ResourceAccessor arg0) {
        // TODO Auto-generated method stub

    }

}
