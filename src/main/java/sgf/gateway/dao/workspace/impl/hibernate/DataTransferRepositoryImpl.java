package sgf.gateway.dao.workspace.impl.hibernate;

import org.hibernate.Query;
import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.workspace.DataTransferRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;

import java.io.Serializable;
import java.util.List;

public class DataTransferRepositoryImpl extends AbstractRepositoryImpl<DataTransferRequest, Serializable> implements DataTransferRepository {

    @Override
    protected Class<DataTransferRequest> getEntityClass() {

        return DataTransferRequest.class;
    }

    /**
     * {@inheritDoc}
     */
    public List<DataTransferRequest> findDataTransferRequests(User user) {

        Assert.notNull(user, "User reference cannot be null");

        Query query = this.getSession().getNamedQuery("findDataTransferRequests");
        query.setParameter("userId", user.getIdentifier());

        List<DataTransferRequest> results = query.list();

        return results;
    }

    /**
     * {@inheritDoc}
     */
    public List<DataTransferRequest> findAllDataTransferRequestsByStatus(DataTransferRequestStatus status) {

        Assert.notNull(status, "Status reference cannot be null");

        Query query = this.getSession().getNamedQuery("findAllDataTransferRequestsByStatus");
        query.setParameter("status", status);

        List<DataTransferRequest> results = query.list();

        return results;
    }

    /**
     * {@inheritDoc}
     */
    public void storeDataTransferRequest(DataTransferRequest request) {

        super.add(request);
    }

    /**
     * {@inheritDoc}
     */
    public DataTransferRequest getDataTransferRequest(UUID id) {

        return super.get(id);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * This is a DataTransferITEM, not a REQUEST
     */
    public void storeDataTransferItem(DataTransferItem item) {

        this.getSession().saveOrUpdate(item);
    }

    public DataTransferItem getDataTransferItem(UUID identifier) {

        return (DataTransferItem) getSession().load(DataTransferItem.class, identifier);
    }

    public Integer getMaxRequestNumber(User user) {

        Assert.notNull(user, "User reference cannot be null");

        Query query = this.getSession().getNamedQuery("getMaxRequestNumber");
        query.setParameter("userId", user.getIdentifier());

        Integer result = (Integer) query.uniqueResult();

        return result;
    }
}
