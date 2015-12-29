package eske.liquibase.versioning;

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

public class GenerateLogicalFileVersionRows implements CustomTaskChange {

    public void execute(Database database) {

        JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
        Connection liquibaseConnection = jdbcConnection.getUnderlyingConnection();
        DataSource dataSource = new SingleConnectionDataSource(liquibaseConnection, true);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);


        final String fileSelectQuery = "SELECT "
                + "  dataset_version.id AS dataset_version_id, "
                + "  logical_file.id, "
                + "  logical_file.size, "
                + "  logical_file.data_format_id, "
                + "  persistent_identifier.identifier "
                + "FROM "
                + "  metadata.dataset_version, "
                + "  metadata.logical_file, "
                + "  metadata.persistent_identifier "
                + "WHERE "
                + "  logical_file.dataset_id = dataset_version.dataset_id AND"
                + "  persistent_identifier.resource_id = dataset_version.dataset_id AND"
                + "  persistent_identifier.type_id = 0;";


        final String createVersionStatement = "INSERT INTO metadata.logical_file_version"
                + "(id, object_version dataset_version_id, data_format_id, size, lineage_id, cmor_tracking_id)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.query(fileSelectQuery, new RowCallbackHandler() {

            public void processRow(final ResultSet resultSet) throws SQLException {

                jdbcTemplate.execute(createVersionStatement, new PreparedStatementCallback() {

                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        preparedStatement.setObject(1, resultSet.getObject("logical_file.id"), Types.OTHER);
                        preparedStatement.setLong(2, 1);
                        preparedStatement.setObject(3, resultSet.getObject("dataset_version_id"), Types.OTHER);
                        preparedStatement.setObject(4, resultSet.getObject("logical_file.data_format_id"), Types.OTHER);
                        preparedStatement.setLong(5, resultSet.getLong("logical_file.size")); //size
                        preparedStatement.setObject(6, resultSet.getString("persistent_identifier.identifier"), Types.OTHER);
                        preparedStatement.setNull(7, Types.VARCHAR); // cmor tracking id

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
