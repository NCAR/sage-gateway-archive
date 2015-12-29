package sgf.gateway.web.controllers.topic;

import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

import java.util.Collection;

public class CadisTaxonomyTopicListStrategy implements TopicListStrategy {

    private final TopicRepository topicRepository;

    public CadisTaxonomyTopicListStrategy(TopicRepository topicRepository) {

        this.topicRepository = topicRepository;
    }

    public Collection<Topic> getTopics() {

        Collection<Topic> topics = this.topicRepository.findByTaxonomy(Taxonomy.CADIS_DISCIPLINE);

        return topics;
    }
}
