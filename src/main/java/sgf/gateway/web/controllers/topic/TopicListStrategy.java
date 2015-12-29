package sgf.gateway.web.controllers.topic;

import sgf.gateway.model.metadata.Topic;

import java.util.Collection;

public interface TopicListStrategy {

    Collection<Topic> getTopics();
}
