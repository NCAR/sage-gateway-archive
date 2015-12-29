package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;
import sgf.gateway.model.metadata.activities.modeling.Experiment;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.inventory.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface DatasetRepository {

    void add(Dataset dataset);

    void remove(Dataset dataset);

    Dataset get(UUID identifier);

    Collection<Dataset> get(Collection<UUID> datasetIdentities);

    Collection<Dataset> getAll();

    List<Dataset> getPaginatedByDateUpdatedDescending(Integer offset, Integer numberOfResults);

    List<Dataset> getParentList(Dataset dataset);

    Collection<Dataset> getTopLevel();

    Collection<LogicalFile> getFilteredLogicalFiles(Dataset dataset, String logicalFileNamePattern, Collection<UUID> variableIdentities);

    /**
     * @return the dataset if a match is found, NULL if not. Any Type (Project or Dataset)
     */
    Dataset getByShortName(String shortName);

    Dataset getByShortNameIgnoreCase(String shortName);

    List<Dataset> getByTitleIgnoreCase(String title);

    Dataset getProjectByShortName(String projectName); // Only Project type

    Dataset findByAuthoritativeIdentifier(String authoritativeIdentifier);

    Collection<Dataset> findByDateRangeForOai(String projectIdentifier, Date updatedMin, Date updatedMax, Integer maxResults, Integer index);

    Collection<UUID> findAllIdentifiersForBrokeredDatasets();

    boolean datasetExists(String shortName);

    StandardName findStandardName(String name, StandardNameType type);

    Collection<Variable> findVariablesByStandardName(String name, StandardNameType type);

    Unit findUnit(String symbol);

    DataFormat findDataFormat(String name);

    TimeFrequency findTimeFrequency(String name);

    PhysicalDomain findPhysicalDomain(String name);

    Institution findInstitution(String name);

    Collection<Location> getAllLocations();

    Location getLocation(Serializable identifier);

    Collection<Dataset> findDatasetsByDiscipline(ScienceKeyword keyword);

    Integer findDatasetCountByDiscipline(ScienceKeyword keyword);

    Location findLocation(String name, Taxonomy topicType);

    Experiment findExperiment(String name);

    Experiment findExperimentByShortName(String shortName);

    Ensemble findEnsemble(String name);

    Date getEarliestDateUpdated();

    List<Dataset> getProjectTypeDatasets();

    List<Dataset> getDatasetTypeDatasets();

    Long getTotalCount();
}
