package sgf.gateway.model.metadata.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.TopicImpl;
import sgf.gateway.model.metadata.factory.TopicFactory;

public class TopicFactoryImpl implements TopicFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public TopicFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {
        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public Topic create(String name, Taxonomy type, Dataset dataset) {

        Topic topicImpl = this.create(name, type);

        dataset.addTopic(topicImpl);

        return topicImpl;
    }

    /**
     * {@inheritDoc}
     */
    public Topic create(String name, Taxonomy type) {

        Assert.notNull(name, "Topic must have a name.");
        Assert.notNull(type, "Topic must have a taxonomy type.");

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(TopicImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Topic topicImpl = new TopicImpl((UUID) vuId.getIdentifierValue(), null, name.trim(), type);

        return topicImpl;
    }

}
