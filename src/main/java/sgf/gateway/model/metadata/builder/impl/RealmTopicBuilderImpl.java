package sgf.gateway.model.metadata.builder.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.factory.TopicFactory;

/**
 * Specialization of the TopicBuilderImpl.
 * <p/>
 * Note: This implementation allows creation a new instance of a Realm Taxonomy Topic if it doesn't already exist.
 */
public class RealmTopicBuilderImpl extends TopicBuilderImpl {

    private static final Log LOG = LogFactory.getLog(RealmTopicBuilderImpl.class);

    private TopicFactory topicFactory;

    public RealmTopicBuilderImpl(TopicRepository topicRepository, TopicFactory topicFactory) {

        super(topicRepository);

        this.topicFactory = topicFactory;

    }

    @Override
    public synchronized Topic build(String topicName, Taxonomy topicType) {

        String trimmedTopicName = topicName.trim();

        if (LOG.isTraceEnabled()) {
            LOG.trace("Building Topic: " + trimmedTopicName);
        }

        Topic topic = find(trimmedTopicName, topicType);

        if (null == topic) {

            if (LOG.isTraceEnabled()) {
                LOG.trace("Couldn't find topic, creating new instance.");
            }

            topic = create(trimmedTopicName, topicType);
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Building return topic: " + topic);
        }

        return topic;
    }

    protected Topic find(String topicName, Taxonomy topicType) {

        Topic result;

        result = super.build(topicName, topicType);

        return result;
    }

    protected Topic create(String topicName, Taxonomy topicType) {

        Topic result = this.topicFactory.create(topicName, topicType);

        return result;
    }

}
