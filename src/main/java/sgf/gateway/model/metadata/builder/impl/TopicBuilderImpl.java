package sgf.gateway.model.metadata.builder.impl;

import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.builder.TopicBuilder;

/**
 * Implementation of the TopicBuilder interface.
 * <p/>
 * Note: This implementation does not create a new instance of a Topic if it doesn't already exist. This behavior can be used with a controlled list of topics.
 *
 * @author hannah
 */
public class TopicBuilderImpl implements TopicBuilder {

    private TopicRepository topicRepository;

    public TopicBuilderImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * {@inheritDoc}
     */
    public Topic build(String topicName, Taxonomy topicType) {

        String trimmedTopicName = topicName.trim();

        Topic topic = this.topicRepository.findTopic(trimmedTopicName, topicType);

        return topic;
    }
}
