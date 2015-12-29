package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

import java.io.Serializable;
import java.util.List;

public class TopicRepositoryImpl extends AbstractRepositoryImpl<Topic, Serializable> implements TopicRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Topic> getEntityClass() {
        return Topic.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Topic> getAll() {
        return super.getAllOrdered("name");
    }

    /**
     * {@inheritDoc}
     */
    public Topic get(final UUID identity) {

        return super.get(identity);
    }


    /**
     * Find topics by type.
     */
    public List<Topic> findByTaxonomy(Taxonomy type) {

        Criteria criteria = this.getSession().createCriteria(getEntityClass());

        criteria.add(Restrictions.eq("type", type));

        criteria.addOrder(Order.asc("name"));

        return criteria.list();
    }

    public Topic findTopic(String name, Taxonomy topicType) {

        Query query = this.getSession().getNamedQuery("findTopicByNameAndTaxonomy");

        query.setString("name", name);
        query.setInteger("taxonomy", topicType.ordinal());

        List<Topic> results = query.list();

        Topic resultToReturn = null;

        if (results.size() == 1) {

            resultToReturn = results.get(0);
        }

        return resultToReturn;
    }
}
