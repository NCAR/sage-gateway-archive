package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

import java.io.Serializable;
import java.util.List;

public interface TopicRepository extends Repository<Topic, Serializable> {

    /**
     * Get a topic by ID.
     *
     * @param identity
     * @return
     */
    Topic get(UUID identity);

    Topic get(Serializable identity);

    /**
     * Get all Topics objects ordered by name.
     */
    @Override
    List<Topic> getAll();

    /**
     * Finds the topics of the specified type for the browse interface.
     * <p>
     * Only returns topics of the specified type that have been assigned to datasets.
     * </p>
     *
     * @param type the type (aka Taxonomy) of topics wanted
     * @return the topics
     */
    List<Topic> findByTaxonomy(Taxonomy type);

    Topic findTopic(String name, Taxonomy topicType);
}
