package eske.liquibase.idnnode;

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
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


/**
 * Add the IDN node "IPY"
 *
 * @author hannah
 */
public class TagIPYDatasets implements CustomTaskChange {

    JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    public void execute(Database database) {

        JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
        Connection liquibaseConnection = jdbcConnection.getUnderlyingConnection();
        DataSource dataSource = new SingleConnectionDataSource(liquibaseConnection, true);

        jdbcTemplate = new JdbcTemplate(dataSource);

        final String existingIPYDatasets = "SELECT DISTINCT dataset_id FROM metadata.project "
                + "WHERE dataset_id<>'7f29ead9-d496-11de-892a-00c0f03d5b7c';";

        List<UUID> topLevelDatasets = jdbcTemplate.query(existingIPYDatasets, new RowMapper() {

            public Object mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

                return new UUID(resultSet.getString("dataset_id"));
            }

        });
        recurseThroughHierarchy(topLevelDatasets);

    }

    private void recurseThroughHierarchy(List<UUID> parents) {

        final String findDatasetChildrenStatement = "SELECT id FROM metadata.dataset "
                + "WHERE parent_dataset_id=?";
        final String tagIPYDatasetsStatement = "UPDATE metadata.cadis_descriptive_metadata SET idn_node=? WHERE id=?;";

        for (final UUID id : parents) {

            jdbcTemplate.execute(tagIPYDatasetsStatement, new PreparedStatementCallback() {

                public Object doInPreparedStatement(PreparedStatement preparedStatement)
                        throws SQLException, DataAccessException {

                    preparedStatement.setString(1, "IPY");
                    preparedStatement.setObject(2, id, Types.OTHER);

                    preparedStatement.execute();

                    return null;
                }

            });

            List<UUID> children = jdbcTemplate.query(findDatasetChildrenStatement, new PreparedStatementSetter() {

                public void setValues(PreparedStatement preparedStatement) throws SQLException {

                    preparedStatement.setObject(1, id, Types.OTHER);
                }

            }, new RowMapper() {

                public Object mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

                    return new UUID(resultSet.getString("id"));
                }

            });
            recurseThroughHierarchy(children);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getConfirmationMessage() {

        return "Tagged all IPY datasets with the IDN node 'IPY'.";
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
