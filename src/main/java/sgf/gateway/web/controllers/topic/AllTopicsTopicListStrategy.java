package sgf.gateway.web.controllers.topic;

import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Topic;

import java.util.Collection;

public class AllTopicsTopicListStrategy implements TopicListStrategy {

    private final TopicRepository topicRepository;

    public AllTopicsTopicListStrategy(final TopicRepository topicRepository) {

        this.topicRepository = topicRepository;
    }

    public Collection<Topic> getTopics() {

        Collection<Topic> topics = this.topicRepository.getAll();

        return topics;
    }
}
