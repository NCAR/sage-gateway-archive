package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.LogicalFileImpl;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.metadata.inventory.VariableImpl;

import java.net.URI;
import java.util.Collection;
import java.util.List;

public class LogicalFileRepositoryImpl extends AbstractRepositoryImpl implements LogicalFileRepository {

    public LogicalFileRepositoryImpl() {
        super();
    }

    @Override
    public void addLogicalFile(LogicalFile file) {

        this.getSession().saveOrUpdate(file);
    }

    @Override
    public LogicalFile get(UUID identifier) {

        LogicalFile result = (LogicalFile) super.get(identifier);

        return result;
    }

    @Override
    public LogicalFile findByLineageIdentifier(String lineageIdentifier) {

        Query query = this.getSession().getNamedQuery("findLogicalFileByLineageId");

        query.setString("lineageIdentifier", lineageIdentifier);

        List<LogicalFile> results = query.list();

        LogicalFile resultToReturn = null;

        if (results.size() == 1) {

            resultToReturn = results.get(0);
        }

        return resultToReturn;
    }

    @Override
    public Collection<Variable> findByStandardNames(Collection<String> names) {

        Criteria criteria = this.getSession().createCriteria(VariableImpl.class);


        Criteria subquery = criteria.createCriteria("standardNamesReference", "name");
        subquery.add(Restrictions.in("name", names));

        List<Variable> result = criteria.list();

        return result;
    }

    @Override
    public Collection<Variable> findByNames(Collection<String> names) {

        Criteria criteria = this.getSession().createCriteria(VariableImpl.class);

        criteria.add(Restrictions.in("name", names));

        List<Variable> result = criteria.list();

        return result;
    }

    @Override
    public LogicalFile findLogicalFileByAccessPointURL(URI accessURI) {

        Query query = getSession().getNamedQuery("findLogicalFileByFileAccessPointURL");
        query.setParameter("accessURI", accessURI);

        LogicalFile logicalFile = (LogicalFile) query.uniqueResult();

        return logicalFile;
    }

    @Override
    public LogicalFile findLogicalFileByAccessPointId(UUID fileAccessPointId) {

        Criteria criteria = this.getSession().createCriteria(LogicalFileImpl.class, "logicalFile");

        criteria.createAlias("logicalFile.fileAccessPoints", "fileAccessPoint");

        criteria.add(Restrictions.eq("fileAccessPoint.identifier", fileAccessPointId));

        LogicalFile logicalFile = (LogicalFile) criteria.uniqueResult();

        return logicalFile;
    }

    @Override
    public List<LogicalFile> findByDatasetShortNameAndLogicalFileName(String shortName, String filename, boolean exactMatch) {

        Query query;

        if (exactMatch) {

            query = this.getSession().getNamedQuery("logicalFilesByDatasetShortNameAndFileNameExact");
        } else {

            query = this.getSession().getNamedQuery("logicalFilesByDatasetShortNameAndFileNameIgnoreCase");
        }

        query.setString("dataset_short_name", shortName);
        query.setString("logical_file_name", filename);

        List<LogicalFile> results = query.list();

        return results;
    }

    @Override
    protected Class getEntityClass() {

        return LogicalFileImpl.class;
    }
}
