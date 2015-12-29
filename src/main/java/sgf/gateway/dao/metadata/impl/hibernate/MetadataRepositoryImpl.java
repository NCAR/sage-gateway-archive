package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.GatewayImpl;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.modeling.ExperimentImpl;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MetadataRepositoryImpl extends AbstractRepositoryImpl implements MetadataRepository {

    /**
     * {@inheritDoc}
     */
    public List<LogicalFile> findLogicalFileById(Collection<UUID> objectIds) {

        List<Serializable> list = new ArrayList<Serializable>();

        // Adding the given object identities to a serializable list.
        for (UUID objectId : objectIds) {
            list.add(objectId);
        }

        Query query = this.getSession().getNamedQuery("findLogicalFilesById");

        if (list.isEmpty()) {

            query.setParameter("idList", null);
        } else {

            query.setParameterList("idList", list);
        }

        List<LogicalFile> logicalFiles = query.list();

        return logicalFiles;
    }

    /**
     * {@inheritDoc}
     */
    public Object getObject(Class clazz, UUID id) {

        String clazzType = clazz.getName();

        return this.getObject(clazzType, id);
    }

    public Object getObject(String clazz, UUID id) {

        // NOTE: must use get(), not load(), to properly retrieve the most-specific subclass type.
        // The Hibernate method load() does not hit the database until one of the object properties is access.
        return this.getSession().get(clazz, id);
    }

    /**
     * {@inheritDoc}
     */
    public void storeDataset(Dataset dataset) {

        this.getSession().saveOrUpdate(dataset);
        this.getSession().getSessionFactory().getCurrentSession().flush();
    }


    public TimeFrequency getTimeFrequency(Serializable identifier) {

        TimeFrequency timeFrequency = (TimeFrequency) this.getSession().get(TimeFrequency.class, identifier);

        return timeFrequency;
    }

    /**
     * {@inheritDoc}
     */
    public List<TimeFrequency> findTimeFrequencies() {

        Criteria criteria = getSession().createCriteria(TimeFrequency.class);

        List<TimeFrequency> timeFrequency = criteria.list();

        return timeFrequency;
    }

    public DataFormat getDataFormat(Serializable identifer) {

        DataFormat dataFormat = (DataFormat) this.getSession().get(DataFormatImpl.class, identifer);

        return dataFormat;
    }

    /**
     * {@inheritDoc}
     */
    public List<DataFormat> findDataFormats() {

        Criteria criteria = getSession().createCriteria(DataFormat.class);

        List<DataFormat> dataFormat = criteria.list();

        return dataFormat;
    }

    public ScienceKeyword getScienceKeyword(UUID identifer) {

        ScienceKeyword scienceKeyword = (ScienceKeyword) this.getSession().get(ScienceKeywordImpl.class, identifer);

        return scienceKeyword;
    }

    public ScienceKeyword findScienceKeywordByDisplayText(String displayText) {

        Criteria criteria = getSession().createCriteria(ScienceKeywordImpl.class);

        criteria.add(Restrictions.eq("displayText", displayText).ignoreCase());

        ScienceKeyword scienceKeyword = (ScienceKeyword) criteria.uniqueResult();

        return scienceKeyword;
    }

    public List<ScienceKeyword> getScienceKeywords() {

        Criteria criteria = getSession().createCriteria(ScienceKeywordImpl.class);

        criteria.addOrder(Order.asc("displayText"));

        List<ScienceKeyword> scienceKeywords = criteria.list();

        return scienceKeywords;
    }

    // These are the distinct keyword topics, because there are no lower level data (ie. "term" is null);
    public List<ScienceKeyword> getScienceKeywordTopics() {

        Criteria criteria = getSession().createCriteria(ScienceKeywordImpl.class);

        criteria.add(Restrictions.isNull("term"));

        criteria.addOrder(Order.asc("topic"));

        List<ScienceKeyword> scienceKeywords = criteria.list();

        return scienceKeywords;
    }

    public List<ScienceKeyword> getUsedScienceKeywordTopics() {

        Query query = this.getSession().getNamedQuery("findUsedScienceKeywordTopics");

        List<String> usedTopicNames = query.list();

        List<ScienceKeyword> usedScienceKeywords = this.getScienceKeywordTopicsByName(usedTopicNames);

        return usedScienceKeywords;
    }

    private List<ScienceKeyword> getScienceKeywordTopicsByName(List<String> topics) {

        List<ScienceKeyword> scienceKeywordTopics = new ArrayList<>();

        for (String topic : topics) {

            ScienceKeyword scienceKeyword = this.getScienceKeywordByTopic(topic);

            scienceKeywordTopics.add(scienceKeyword);
        }

        return scienceKeywordTopics;
    }

    private ScienceKeyword getScienceKeywordByTopic(String topic) {

        Criteria criteria = getSession().createCriteria(ScienceKeywordImpl.class);
        criteria.add(Restrictions.eq("topic", topic));
        criteria.add(Restrictions.isNull("term"));

        ScienceKeyword scienceKeyword = (ScienceKeyword) criteria.uniqueResult();

        return scienceKeyword;
    }

    /**
     * {@inheritDoc}
     */
    public DataProductType findDataProductTypeByName(String name) {

        Query query = this.getSession().getNamedQuery("findDataProductTypeByName");
        query.setString("name", name);

        List<DataProductType> dpts = query.list();

        DataProductType dpt = null;

        if (dpts.size() == 1) {

            dpt = dpts.get(0);
        }
        return dpt;
    }

    /**
     * {@inheritDoc}
     */
    public List<GatewayImpl> findGateways() {

        Criteria criteria = getSession().createCriteria(GatewayImpl.class);

        List<GatewayImpl> gateways = criteria.list();

        return gateways;
    }

    /**
     * {@inheritDoc}
     */
    public List<ExperimentImpl> findExperiments() {

        Criteria criteria = getSession().createCriteria(ExperimentImpl.class);

        List<ExperimentImpl> experiments = criteria.list();

        return experiments;
    }

    /**
     * {@inheritDoc}
     */
    public List<Dataset> findDatasetsByUserAndOperation(User user, Operation operation) {

        Query query = this.getSession().getNamedQuery("findDatasetsByUserAndOperation");

        query.setParameter("user", user);
        query.setParameter("operation", operation);

        List<Dataset> datasets = query.list();

        return datasets;
    }

    /**
     * {@inheritDoc}
     */
    public List<Dataset> findDatasetsByGroupUserAndOperation(User user, Operation operation) {

        Query query = this.getSession().getNamedQuery("findDatasetsByGroupUserAndOperation");

        query.setParameter("user", user);
        query.setParameter("operation", operation);

        List<Dataset> datasets = query.list();

        return datasets;
    }

    public MetadataProfile findMetadataProfileByName(String name) {

        Query query = this.getSession().getNamedQuery("findMetadataProfileByName");

        query.setString("name", name);

        MetadataProfile metadataProfile = (MetadataProfile) query.uniqueResult();

        return metadataProfile;
    }

    @Override
    protected Class getEntityClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<T> findObjectsByNamedQuery(String namedQuery) {
        // TODO Auto-generated method stub
        return null;
    }
}
