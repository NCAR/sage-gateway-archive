package sgf.gateway.dao.metrics.impl.hibernate;

import org.hibernate.criterion.Restrictions;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metrics.UserAgentDAO;
import sgf.gateway.model.metrics.UserAgent;

import java.io.Serializable;
import java.util.List;

public class UserAgentDAOImpl extends AbstractRepositoryImpl<UserAgent, Serializable> implements UserAgentDAO {

    @Override
    protected Class<UserAgent> getEntityClass() {
        return UserAgent.class;
    }

    /**
     * {@inheritDoc}
     */
    public UserAgent getUserAgent(final String name) {

        return super.findUniqueByCriteria(Restrictions.eq("name", name).ignoreCase());
    }

    /**
     * {@inheritDoc}
     */
    public List<UserAgent> getUserAgents() {

        return super.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public void storeUserAgent(final UserAgent userAgent) {

        super.add(userAgent);
    }
}
