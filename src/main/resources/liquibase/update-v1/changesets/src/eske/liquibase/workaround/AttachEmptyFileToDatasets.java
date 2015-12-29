package eske.liquibase.workaround;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.safehaus.uuid.UUID;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class AttachEmptyFileToDatasets implements CustomTaskChange {

    /**
     * {@inheritDoc}
     */
    public void execute(Database database) {

        JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
        Connection liquibaseConnection = jdbcConnection.getUnderlyingConnection();
        DataSource dataSource = new SingleConnectionDataSource(liquibaseConnection, true);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final String existingEmptyDatasets = "SELECT id FROM metadata.dataset "
                + "WHERE id NOT IN "
                + "(SELECT DISTINCT dataset_id FROM metadata.logical_file);";
        final String createResourceStatement = "INSERT INTO metadata.resource(id, version, type_id, name)  "
                + "VALUES (?, ?, ?, ?); ";
        final String createLogicalFileStatement = "INSERT INTO metadata.logical_file(id, dataset_id, size) "
                + "VALUES (?, ?, ?); ";

        jdbcTemplate.query(existingEmptyDatasets, new RowCallbackHandler() {

            public void processRow(final ResultSet resultSet) throws SQLException {

                final UUID newId = org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();

                jdbcTemplate.execute(createResourceStatement, new PreparedStatementCallback() {

                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        preparedStatement.setObject(1, newId, Types.OTHER);
                        preparedStatement.setInt(2, 0);
                        preparedStatement.setInt(3, 1);
                        preparedStatement.setString(4, "empty.file");

                        preparedStatement.execute();

                        return null;
                    }

                });

                jdbcTemplate.execute(createLogicalFileStatement, new PreparedStatementCallback() {

                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        preparedStatement.setObject(1, newId, Types.OTHER);
                        preparedStatement.setObject(2, resultSet.getObject("id"), Types.OTHER);
                        preparedStatement.setLong(3, 1L);

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
