package sgf.gateway.model.metadata.builder;

import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

public interface TopicBuilder {

    Topic build(String topicName, Taxonomy topicType);

}
