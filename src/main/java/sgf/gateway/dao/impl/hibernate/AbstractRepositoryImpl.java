package sgf.gateway.dao.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import sgf.gateway.dao.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * A base class to provide common functionality used by Repository classes.
 *
 * @param <E>  Represents the Persistent Entity's Class.  Use the Interface (e.g. Project.class instead of ProjectImpl.class)
 * @param <PK> Represents a Serializable Primary Key for the persistent entity. Usually "identifier".
 * @author cgrant
 */
public abstract class AbstractRepositoryImpl<E, PK extends Serializable> implements Repository<E, PK> {

    SessionFactory sessionFactory;

    /**
     * Subclasses must override to get Class/Type  to use (e.g Project.class).
     */
    protected abstract Class<E> getEntityClass();

    /**
     * Return entity with given id (primary key).  Or null.
     * <p/>
     * Using criteria rather than "get" method because get won't let you pass the interface (e.g. Topic.class), but requires the
     * mapped implementation (e.g. TopicImpl.class).  Criteria works with the interface.
     * <p/>
     * org.hibernate.NonUniqueResultException
     */
    @SuppressWarnings("unchecked")
    public E get(final PK id) {

        //return (E) getSession().get(getEntityClass(), id);
        Criteria criteria = this.getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("identifier", id));

        return (E) criteria.uniqueResult();
    }


    /**
     * Return all entities.  Can return empty list.
     */
    @SuppressWarnings("unchecked")
    public List<E> getAll() {

        Criteria criteria = this.getSession().createCriteria(getEntityClass());

        return criteria.list();
    }

    /**
     * Return all entities in ascending order based on the given property.  Can return empty list.
     *
     * @param orderByProperty
     * @return
     */
    protected List<E> getAllOrdered(String... orderByProperties) {

        Criteria criteria = this.getSession().createCriteria(getEntityClass());

        for (String orderBy : orderByProperties) {

            criteria.addOrder(Order.asc(orderBy));
        }

        List<E> list = criteria.list();

        return list;
    }

    /**
     * Find entities where a property has a certain value.  Can return empty list.
     * e.g findByProperty("name", "Fun New Project")
     */
    @SuppressWarnings("unchecked")
    protected List<E> findByProperty(final String propertyName, final Object value) {

        Criteria criteria = this.getSession().createCriteria(getEntityClass());

        criteria.add(Restrictions.eq(propertyName, value));

        return criteria.list();
    }


    /**
     * Invoke a named query, no parameters to the query.  Can return empty list.
     * <p/>
     * e.g. findByNamedQuery("findProjectByName")
     *
     * @param namedQueryName
     * @return
     */
    @SuppressWarnings("unchecked")
    protected List<E> findByNamedQuery(final String namedQueryName) {

        Query query = this.getSession().getNamedQuery(namedQueryName);
        return query.list();
    }

    /**
     * Find using array for POSITIONAL parameters (? in query string).
     *
     * @param propertyNames
     * @param values
     * @return
     */
    // TODO: named parameters?
    @SuppressWarnings("unchecked")
    protected List<E> findByNamedQueryPositParams(final String namedQueryName, Object[] values) {

        Query query = this.getSession().getNamedQuery(namedQueryName);

        for (int i = 0; i < values.length; i++) {

            query.setParameter(i, values[i]);
        }

        return query.list();

    }

    /**
     * Find using 1 or more Criterion.
     * <p/>
     * e.g.
     * findByCriteria (Restrictions.eq("name", name).ignoreCase() )
     * findByCriteria( Restrictions.isNull("parent") , Restrictions.ilike("name", "Bob%"), Restrictions.gt("salary", 3000))
     * findByCriteria ( criterionArrayList.toArray() )
     *
     * @param criterion
     * @return
     */
    @SuppressWarnings("unchecked")
    protected List<E> findByCriteria(Criterion... criterion) {

        Criteria criteria = getSession().createCriteria(getEntityClass());

        for (Criterion c : criterion) {

            criteria.add(c);
        }

        return criteria.list();
    }

    /**
     * Find a unique result using 1 or more Criterion.  Or null.
     * <p/>
     * e.g.
     * findByCriteria (Restrictions.eq("name", name).ignoreCase() )
     * findByCriteria( Restrictions.isNull("parent") , Restrictions.ilike("name", "Bob%"), Restrictions.gt("salary", 3000))
     * findByCriteria(Restrictions.like("name", "%arctic%" ).ignoreCase(), Restrictions.like("description", "%award%" ).ignoreCase());
     * <p/>
     * You could also use an Array to pass Criterion arguments.  E.g. criterionArrayList.toArray() or
     * Criterion[] crit= {Restrictions.isNull("parent"),  Restrictions.like("name", "%arctic%" ).ignoreCase() };
     * <p/>
     * org.hibernate.NonUniqueResultException
     *
     * @param criterion
     * @return
     */
    @SuppressWarnings("unchecked")
    protected E findUniqueByCriteria(Criterion... criterion) {

        Criteria criteria = getSession().createCriteria(getEntityClass());

        for (Criterion c : criterion) {

            criteria.add(c);
        }

        return (E) criteria.uniqueResult();
    }

    /**
     * Add (save or update) an Entity.
     */
    public void add(E newInstance) {

        this.getSession().saveOrUpdate(newInstance);
    }

    /**
     * Persist and return the new identifier PK.
     */
    public Serializable create(E newInstance) {

        Serializable id = this.getSession().save(newInstance);

        return id;
    }

    /**
     * Remove (delete) a persistent Entity.
     */
    public void remove(E entity) {

        this.getSession().delete(entity);
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
