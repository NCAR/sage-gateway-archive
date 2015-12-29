package sgf.gateway.dao.security.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Role;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;

import java.io.Serializable;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Serializable> implements UserRepository {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    public void storeUser(User user) {
        this.add(user);
    }

    /**
     * {@inheritDoc}
     */
    public User getUser(UUID identifier) {

        User user = this.get(identifier);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    public User findUserByUserName(String username) {

        User user = this.findUniqueByCriteria(Restrictions.eq("userName", username));

        return user;
    }

    public boolean isUsernameUniqueIgnoreCase(String username) {

        boolean value = false;

        List<User> users = this.findByCriteria(Restrictions.eq("userName", username).ignoreCase());

        if (users.isEmpty()) {

            value = true;
        }

        return value;
    }

    // TODO change this to a collection or set?
    public List<User> findUsersByEmail(String email) {

        List<User> users = this.findByCriteria(Restrictions.eq("email", email).ignoreCase());

        return users;
    }

    public boolean isLocalEmailUnique(String email) {

        boolean unique = false;

        List<User> users = this.findLocalUsersByEmail(email);

        if (users.size() == 0) {

            unique = true;
        }

        return unique;
    }

    private List<User> findLocalUsersByEmail(String email) {

        List<User> users = this.findByCriteria(Restrictions.eq("email", email).ignoreCase(), Restrictions.isNotNull("password"));

        return users;
    }

    /**
     * {@inheritDoc}
     */
    public User findUserByOpenid(String openid) {
        User user = this.findUniqueByCriteria(Restrictions.eq("openid", openid));
        return user;
    }

    /**
     * {@inheritDoc}
     */
    public List<User> findUsersThatMatch(String match, int limit) {

        // perform case-insensitive queries and pad the matching expression
        String match2 = "%" + match.toLowerCase() + "%";

        Query query = this.getSession().getNamedQuery("findUsersThatMatch");
        query.setParameter("match", match2);

        query.setMaxResults(limit);

        List<User> users = query.list();

        return users;
    }

    /**
     * {@inheritDoc}
     */
    public Long countUsersThatMatch(String match) {

        //perform case-insensitive queries and pad the matching expression
        String matchExpression = "%" + match.toLowerCase() + "%";

        Query query = this.getSession().getNamedQuery("countUsersThatMatch");
        query.setParameter("match", matchExpression);

        Long count = (Long) query.uniqueResult();

        return count;
    }

    /**
     * {@inheritDoc}
     */
    public List<User> findGroupUsersInStatusThatMatch(String groupName, String match, Status status, int limit) {

        // perform case-insensitive queries and pad the matching expression
        String match2 = "%" + match.toLowerCase() + "%";

        Query query = this.getSession().getNamedQuery("findGroupUsersInStatusThatMatch");
        query.setParameter("groupName", groupName);
        query.setParameter("status", status);
        query.setParameter("match", match2);
        query.setMaxResults(limit);

        List<User> users = query.list();

        return users;
    }

    /**
     * {@inheritDoc}
     */
    public Long countGroupUsersInStatusThatMatch(String groupName, String match, Status status) {

        //perform case-insensitive queries and pad the matching expression
        final String matchingExpression = "%" + match.toLowerCase() + "%";

        Query query = this.getSession().getNamedQuery("countGroupUsersInStatusThatMatch");
        query.setParameter("groupName", groupName);
        query.setParameter("status", status);
        query.setParameter("match", matchingExpression);

        Long count = (Long) query.uniqueResult();

        return count;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> findRoles() {

        Criteria criteria = this.getSession().createCriteria(Role.class);
        List<Role> roles = criteria.list();

        return roles;
    }
}
