package eske.liquibase.periodofperformance;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.safehaus.uuid.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import sgf.gateway.utils.Pair;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Copies the dates of a dataset representation of a project's TimeCoordinateAxis to the
 * CADISProject's period of performance fields.
 */
public class CopyPeriodOfPerformances implements CustomTaskChange {

    /**
     * {@inheritDoc}
     */
    public void execute(Database database) {

        JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
        Connection liquibaseConnection = jdbcConnection.getUnderlyingConnection();
        DataSource dataSource = new SingleConnectionDataSource(liquibaseConnection, true);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final String datasetRepresentationsStatement = "SELECT id, dataset_id FROM metadata.project "
                + "WHERE dataset_id IS NOT NULL;";
        final String timeCoverageStatement = "SELECT start_range, end_range FROM metadata.time_coordinate_axis "
                + "WHERE id=(SELECT id FROM metadata.coordinate_axis WHERE geophysical_properties_id=? "
                + "AND coordinate_type_id=2);";
        final String copyPeriodOfPerfomanceStatement = "UPDATE metadata.cadis_project SET period_of_performance_start=?, period_of_performance_end=? "
                + "WHERE id=?;";

        jdbcTemplate.query(datasetRepresentationsStatement, new RowCallbackHandler() {

            public void processRow(final ResultSet resultSet) throws SQLException {

                final UUID projectId = new UUID(resultSet.getString("id"));
                final UUID datasetId = new UUID(resultSet.getString("dataset_id"));

                List<Pair> dates = jdbcTemplate.query(timeCoverageStatement, new PreparedStatementSetter() {

                    public void setValues(PreparedStatement preparedStatement) throws SQLException {

                        preparedStatement.setObject(1, datasetId, Types.OTHER);
                    }

                }, new RowMapper() {

                    public Object mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

                        Pair result = new Pair(resultSet.getTimestamp("start_range"), resultSet.getTimestamp("end_range"));
                        return result;
                    }

                });

                if (!dates.isEmpty()) {
                    final Object[] argList = {dates.get(0).getKey(), dates.get(0).getValue(), projectId};
                    final int[] argTypeList = {Types.TIMESTAMP, Types.TIMESTAMP, Types.OTHER};

                    jdbcTemplate.update(copyPeriodOfPerfomanceStatement, argList, argTypeList);
                }
            }

        });
    }

    /**
     * {@inheritDoc}
     */
    public String getConfirmationMessage() {

        return "Copied dataset representation time coverages to CADISProjects.";
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
