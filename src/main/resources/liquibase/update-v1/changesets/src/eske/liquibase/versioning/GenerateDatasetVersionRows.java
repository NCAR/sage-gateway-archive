package eske.liquibase.versioning;

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

public class GenerateDatasetVersionRows implements CustomTaskChange {

    private static final String DATA_MIGRATION_USER_ID = "65e4d7f9-ecad-4d16-a4bb-e668abeeb5fe";

    public void execute(Database database) {

        JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
        Connection liquibaseConnection = jdbcConnection.getUnderlyingConnection();
        DataSource dataSource = new SingleConnectionDataSource(liquibaseConnection, true);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final String existingDatasets = "SELECT * FROM metadata.dataset;";
        final String createVersionStatement = "INSERT INTO metadata.dataset_version(id, dataset_id, version, publisher, \"comment\", label, index) VALUES (?, ?, ?, ?, ?, ?, ?); ";

        jdbcTemplate.query(existingDatasets, new RowCallbackHandler() {

            public void processRow(final ResultSet resultSet) throws SQLException {

                jdbcTemplate.execute(createVersionStatement, new PreparedStatementCallback() {

                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        preparedStatement.setObject(1, org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID(), Types.OTHER);
                        preparedStatement.setObject(2, resultSet.getObject("id"), Types.OTHER);
                        preparedStatement.setLong(3, 1L);
                        preparedStatement.setObject(4, new UUID(DATA_MIGRATION_USER_ID), Types.OTHER);
                        preparedStatement.setString(5, "Default first version");
                        preparedStatement.setString(6, "1");
                        preparedStatement.setInt(7, 0);

                        preparedStatement.execute();

                        return null;
                    }

                });
            }

        });

    }

    public String getConfirmationMessage() {
        return "Whiz bang task completed!";
    }

    public void setUp() throws SetupException {

    }

    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    @Override
    public void setFileOpener(ResourceAccessor arg0) {
        // TODO Auto-generated method stub

    }

}
