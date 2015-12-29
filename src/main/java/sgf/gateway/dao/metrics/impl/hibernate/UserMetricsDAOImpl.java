package sgf.gateway.dao.metrics.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sgf.gateway.dao.metrics.UserMetricsDAO;
import sgf.gateway.model.metrics.UserLogin;
import sgf.gateway.model.metrics.UserSearch;

public class UserMetricsDAOImpl implements UserMetricsDAO {

    SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    public void storeUserLogin(final UserLogin userLogin) {

        this.getSession().saveOrUpdate(userLogin);
    }

    /**
     * {@inheritDoc}
     */
    public void storeUserSearch(final UserSearch userSearch) {

        this.getSession().saveOrUpdate(userSearch);
    }


    public SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {

        Session session = this.sessionFactory.getCurrentSession();

        return session;
    }
}
