package sgf.gateway.dao.metadata.impl.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;
import sgf.gateway.model.metadata.activities.modeling.Experiment;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.inventory.*;
import sgf.gateway.service.metadata.DatasetNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DatasetRepositoryImpl extends AbstractRepositoryImpl implements DatasetRepository {

    private static final Log LOG = LogFactory.getLog(DatasetRepositoryImpl.class);

    public DatasetRepositoryImpl() {
        super();
    }

    public Dataset get(UUID identifier) {

        Dataset result = (Dataset) this.getSession().get(DatasetImpl.class, identifier);

        if (null == result) {

            throw new DatasetNotFoundException(identifier);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    public Collection<Dataset> get(Collection<UUID> datasetIdentities) {

        Query query = this.getSession().getNamedQuery("findDatasetsById");

        List<UUID> ids = new ArrayList<UUID>(datasetIdentities.size());

        for (UUID objectIdentity : datasetIdentities) {
            ids.add(objectIdentity);
        }

        query.setParameterList("datasetIds", ids);

        List<Dataset> datasetList = query.list();

        return datasetList;
    }

    @SuppressWarnings({"unchecked"})
    public Collection<Dataset> getTopLevel() {

        Query query = getSession().getNamedQuery("findVisibleTopLevelDatasets");

        List<Dataset> datasetList = query.list();

        return datasetList;
    }

    public List<Dataset> getAll() {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class);

        criteria = criteria.addOrder(Order.asc("title"));

        List<Dataset> datasetList = criteria.list();

        return datasetList;
    }

    public List<Dataset> getPaginatedByDateUpdatedDescending(Integer offset, Integer numberOfResults) {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class);
        criteria = criteria.addOrder(Order.desc("dateUpdated"));
        criteria = criteria.addOrder(Order.asc("title"));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(numberOfResults);

        List<Dataset> datasetList = criteria.list();

        return datasetList;
    }


    public List<Dataset> getParentList(Dataset dataset) {

        List<Dataset> parentList = new ArrayList<Dataset>();

        this.addParent(parentList, dataset);

        return parentList;
    }

    void addParent(List<Dataset> parentList, Dataset dataset) {

        if (dataset.isTopLevelDataset() == false) {

            Dataset datasetParent = dataset.getParent();

            parentList.add(0, datasetParent);

            this.addParent(parentList, datasetParent);
        }
    }

    public Collection<LogicalFile> getFilteredLogicalFiles(Dataset dataset, String logicalFileNamePattern, Collection<UUID> variableIdentities) {

        List<LogicalFile> logicalFileList = null;

        if (((logicalFileNamePattern == null) || "".equals(logicalFileNamePattern)) && variableIdentities.isEmpty()) {

            Query query = this.getSession().getNamedQuery("findLogicalFilesForDatasetVersion");
            query.setParameter("datasetVersionId", dataset.getCurrentDatasetVersion().getIdentifier());

            logicalFileList = query.list();
        }
        if (((logicalFileNamePattern != null) && !"".equals(logicalFileNamePattern)) && variableIdentities.isEmpty()) {

            Query query = this.getSession().getNamedQuery("findLogicalFilesForDatasetVersionWithFilteredNames");
            query.setParameter("datasetVersionId", dataset.getCurrentDatasetVersion().getIdentifier());
            query.setParameter("logicalFileNamePattern", logicalFileNamePattern);

            logicalFileList = query.list();
        }
        if (((logicalFileNamePattern == null) || "".equals(logicalFileNamePattern)) && !variableIdentities.isEmpty()) {


            Query query = this.getSession().getNamedQuery("findLogicalFilesForDatasetVersionWithFilteredVaraibles");
            query.setParameter("datasetVersionId", dataset.getCurrentDatasetVersion().getIdentifier());

            List<UUID> variableUUIDList = new ArrayList<UUID>();

            for (UUID objectIdentity : variableIdentities) {
                variableUUIDList.add(objectIdentity);
            }

            query.setParameterList("variableIdentities", variableUUIDList);

            logicalFileList = query.list();
        }
        if (((logicalFileNamePattern != null) && !"".equals(logicalFileNamePattern)) && !variableIdentities.isEmpty()) {

            Query query = this.getSession().getNamedQuery("findLogicalFilesForDatasetVersionWithFilteredNamesAndVariables");
            query.setParameter("datasetVersionId", dataset.getCurrentDatasetVersion().getIdentifier());
            query.setParameter("logicalFileNamePattern", logicalFileNamePattern);

            List<UUID> variableUUIDList = new ArrayList<UUID>();

            for (UUID objectIdentity : variableIdentities) {
                variableUUIDList.add(objectIdentity);
            }

            query.setParameterList("variableIdentities", variableUUIDList);

            logicalFileList = query.list();
        }

        return logicalFileList;
    }

    public Dataset getByShortName(String shortName) {

        Dataset dataset = null;

        Query query = this.getSession().getNamedQuery("findDatasetByShortName");
        query.setParameter("identifier", shortName);

        UUID datasetUUID = (UUID) query.uniqueResult();

        if (datasetUUID != null) {

            dataset = (Dataset) this.getSession().get(DatasetImpl.class, datasetUUID);
        }

        return dataset;
    }

    @Override
    public Dataset getByShortNameIgnoreCase(String shortName) {

        Dataset dataset = null;

        Query query = this.getSession().getNamedQuery("findDatasetByShortNameCaseInsensitive");
        query.setParameter("identifier", shortName);

        UUID datasetUUID = (UUID) query.uniqueResult();

        if (datasetUUID != null) {

            dataset = (Dataset) this.getSession().get(DatasetImpl.class, datasetUUID);
        }

        return dataset;
    }

    @Override
    public Dataset getProjectByShortName(String projectName) {

        Dataset projectDataset = null;
        Dataset dataset = this.getByShortName(projectName);

        if (dataset != null) {
            if (dataset.getContainerType().equals(ContainerType.PROJECT)) {
                projectDataset = dataset;
            }
        }

        return projectDataset;
    }

    @Override
    public List<Dataset> getByTitleIgnoreCase(String title) {

        Criteria criteria = getSession().createCriteria(Dataset.class);

        criteria.add(Restrictions.eq("title", title).ignoreCase());

        List<Dataset> datasets = criteria.list();

        return datasets;
    }

    public Dataset findByAuthoritativeIdentifier(String authoritativeIdentifier) {

        Criteria criteria = getSession().createCriteria(Dataset.class);

        criteria.add(Restrictions.eq("authoritativeIdentifier", authoritativeIdentifier));

        Dataset dataset = (Dataset) criteria.uniqueResult();

        return dataset;
    }

    // TODO: pass in topic String or whole keyword object?  Also, use sql to get UUID or HQL to get Dataset?
    public Collection<Dataset> findDatasetsByDiscipline(ScienceKeyword keyword) {

        Query query = this.getSession().getNamedQuery("findDatasetsByScienceKeywordTopic");

        query.setString("scienceKeywordTopic", keyword.getTopic());

        List<UUID> results = query.list();

        List<Dataset> datasets = new ArrayList<Dataset>();

        for (UUID identifier : results) {

            datasets.add(this.get(identifier));
        }

        return datasets;

    }

    public Integer findDatasetCountByDiscipline(ScienceKeyword keyword) {

        Query query = this.getSession().getNamedQuery("findDatasetCountByScienceKeywordTopic");

        query.setString("scienceKeywordTopic", keyword.getTopic());

        Integer count = (Integer) query.uniqueResult();

        return count;

    }

    public Collection<Dataset> findByDateRangeForOai(String projectIdentifier, Date updatedMin, Date updatedMax, Integer maxResults, Integer index) {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class, "dataset");

        this.addBaseOAICriteria(criteria, projectIdentifier, updatedMin, updatedMax, maxResults, index);

        criteria.addOrder(Order.asc("dataset.id"));

        Collection<Dataset> datasetCollection = criteria.list();

        return datasetCollection;
    }

    private Criteria addBaseOAICriteria(Criteria criteria, String dataCenterIdentifier, Date updatedMin, Date updatedMax, Integer maxResults, Integer index) {

        addOAIDataCenterIdentifierCriteria(criteria, dataCenterIdentifier);
        addOAIDatasetAttributeCriteria(criteria);
        addDateUpdatedCriteria(criteria, updatedMin, updatedMax);
        addPaginationCriteria(criteria, maxResults, index);

        return criteria;
    }

    private void addOAIDataCenterIdentifierCriteria(Criteria criteria, String dataCenterIdentifier) {

        if (dataCenterIdentifier != null) {

            criteria.add(Restrictions.eq("dataset.dataCenter.identifier", dataCenterIdentifier));
        }
    }

    private void addOAIDatasetAttributeCriteria(Criteria criteria) {
        criteria.add(Restrictions.isNotNull("dataset.shortName"));
        criteria.add(Restrictions.isNotNull("dataset.title"));
        criteria.add(Restrictions.isNotNull("dataset.description"));
    }

    private void addDateUpdatedCriteria(Criteria criteria, Date updatedMin, Date updatedMax) {

        if (updatedMin != null) {
            criteria.add(Restrictions.ge("dataset.dateUpdated", updatedMin));
        }

        if (updatedMax != null) {
            criteria.add(Restrictions.lt("dataset.dateUpdated", updatedMax));
        }
    }

    private void addPaginationCriteria(Criteria criteria, Integer maxResults, Integer index) {

        if (index != null) {
            criteria.setFirstResult(index);
        }

        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }
    }

    public Collection<UUID> findAllIdentifiersForBrokeredDatasets() {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class, "dataset");

        criteria.add(Restrictions.eq("brokered", true));

        criteria.setProjection(Projections.id());

        Collection<UUID> brokeredDatasetIdentifiers = criteria.list();

        return brokeredDatasetIdentifiers;
    }

    /**
     * {@inheritDoc}
     */
    public boolean datasetExists(String shortName) {

        Dataset dataset = this.getByShortName(shortName);

        return dataset != null;
    }

    /**
     * {@inheritDoc}
     */
    public void add(Dataset dataset) {

        LOG.debug("Storing dataset: " + dataset);

        this.getSession().saveOrUpdate(dataset);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(Dataset dataset) {

        LOG.debug("Deleting dataset: " + dataset);

        this.getSession().delete(dataset);
    }

    /**
     * {@inheritDoc}
     */
    public StandardName findStandardName(String name, StandardNameType type) {

        Query query = this.getSession().getNamedQuery("findStandardNameByNameAndType");

        query.setString("name", name);
        query.setInteger("type", type.ordinal());

        List<StandardName> results = query.list();

        LOG.debug("standardName results: " + results.size());

        if (results.size() == 1) {

            return results.get(0);
        }

        return null;
    }


    /**
     * {@inheritDoc}
     */
    public Collection<Variable> findVariablesByStandardName(String name, StandardNameType type) {

        Query query = this.getSession().getNamedQuery("findVariablesByStandardName");

        query.setString("name", name);
        query.setInteger("type", type.ordinal());

        List<Variable> results = query.list();

        LOG.debug("variable by standardName results: " + results.size());

        return results;
    }

    /**
     * {@inheritDoc}
     */
    public Unit findUnit(String symbol) {

        Query query = this.getSession().getNamedQuery("findUnitBySymbol");

        query.setString("symbol", symbol);

        List<Unit> results = query.list();

        LOG.debug("unit results: " + results.size());

        if (results.size() == 1) {

            return results.get(0);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public DataFormat findDataFormat(String name) {

        Query query = this.getSession().getNamedQuery("findDataFormatByName");

        query.setString("name", name);

        List<DataFormat> results = query.list();

        if (results.size() == 1) {

            return results.get(0);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public TimeFrequency findTimeFrequency(String name) {

        Query query = this.getSession().getNamedQuery("findTimeFrequencyByName");

        query.setString("name", name);

        List<TimeFrequency> results = query.list();

        if (results.size() == 1) {

            return results.get(0);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PhysicalDomain findPhysicalDomain(String name) {

        Query query = this.getSession().getNamedQuery("findPhysicalDomainByName");

        query.setString("name", name);

        List<PhysicalDomain> results = query.list();

        if (results.size() == 1) {

            return results.get(0);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Institution findInstitution(String name) {
        Query query = this.getSession().getNamedQuery("findInstitutionByName");
        query.setString("name", name);

        List<Institution> results = query.list();
        if (results.size() == 1) {
            return results.get(0);
        }
        return null;
    }

    public Location getLocation(Serializable identifier) {

        Location location = (Location) this.getSession().get(LocationImpl.class, identifier);

        return location;
    }

    public Collection<Location> getAllLocations() {

        Criteria criteria = getSession().createCriteria(Location.class);

        Collection<Location> locations = criteria.list();

        return locations;
    }

    /**
     * {@inheritDoc}
     */
    public Location findLocation(String name, Taxonomy topicType) {

        Query query = this.getSession().getNamedQuery("findLocationByNameAndTaxonomy");

        query.setString("name", name);
        query.setInteger("taxonomy", topicType.ordinal());

        List<Location> results = query.list();

        Location resultToReturn = null;

        if (results.size() == 1) {

            resultToReturn = results.get(0);
        }

        return resultToReturn;
    }

    /**
     * {@inheritDoc}
     */
    public Experiment findExperiment(String name) {

        Query query = this.getSession().getNamedQuery("findExperimentByName");

        query.setString("name", name);

        List<Experiment> results = query.list();

        Experiment resultToReturn = null;

        if (results.size() == 1) {

            resultToReturn = results.get(0);
        }

        return resultToReturn;
    }

    /**
     * {@inheritDoc}
     */
    public Experiment findExperimentByShortName(String shortName) {

        Query query = this.getSession().getNamedQuery("findExperimentByShortName");

        query.setString("shortName", shortName);

        List<Experiment> results = query.list();

        Experiment resultToReturn = null;

        if (results.size() == 1) {

            resultToReturn = results.get(0);
        }

        return resultToReturn;
    }

    public Ensemble findEnsemble(String name) {

        Query query = this.getSession().getNamedQuery("findEnsembleByName");

        query.setString("name", name);

        List<Ensemble> results = query.list();

        Ensemble resultToReturn = null;

        if (results.size() == 1) {

            resultToReturn = results.get(0);
        }

        return resultToReturn;
    }

    public Date getEarliestDateUpdated() {

        Query query = this.getSession().getNamedQuery("oldestDateUpdated");

        return (Date) query.uniqueResult();
    }

    public List<Dataset> getByType(ContainerType containerType) {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class, "dataset");

        criteria = criteria.add(Restrictions.eq("dataset.containerType", containerType));

        criteria = criteria.addOrder(Order.asc("dataset.title"));

        List<Dataset> datasets = criteria.list();
        return datasets;
    }

    @Override
    public List<Dataset> getProjectTypeDatasets() {

        return this.getByType(ContainerType.PROJECT);
    }

    @Override
    public List<Dataset> getDatasetTypeDatasets() {

        return this.getByType(ContainerType.DATASET);
    }

    @Override
    protected Class getEntityClass() {
        // TODO Auto-generated method stub
        return null;
    }

    public Long getTotalCount() {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class, "dataset");
        criteria.setProjection(Projections.rowCount());

        Long count = (Long) criteria.uniqueResult();

        return count;
    }
}
