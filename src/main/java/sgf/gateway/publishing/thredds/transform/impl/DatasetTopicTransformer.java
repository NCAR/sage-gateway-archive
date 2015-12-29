package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.builder.TopicBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetTopicTransformer implements ThreddsDatasetTransformer {

    private TopicBuilder topicBuilder;
    public static final String THREDDS_PROPERTY_NAME = "Climate";

    public DatasetTopicTransformer(TopicBuilder topicBuilder) {

        this.topicBuilder = topicBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        Topic topic = getTopic(invDataset);

        dataset.addTopic(topic);
    }

    protected Topic getTopic(InvDataset invDataset) {

        Topic topic = this.topicBuilder.build(THREDDS_PROPERTY_NAME, Taxonomy.DEFAULT);
        return topic;
    }
}
