package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.builder.TopicBuilder;
import thredds.catalog.InvDataset;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class DatasetTopicTransformerTest {

    DatasetTopicTransformer datasetTopicTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);
    TopicBuilder mockTopicBuilder = mock(TopicBuilder.class);
    Topic mockTopic = mock(Topic.class);

    String topicName = "Topic 1";

    public static final String THREDDS_PROPERTY_NAME = "Climate";

    @Before
    public void setUp() throws Exception {

        when(mockTopicBuilder.build(THREDDS_PROPERTY_NAME, Taxonomy.DEFAULT)).thenReturn(mockTopic);

        datasetTopicTransformer = new DatasetTopicTransformer(mockTopicBuilder);
    }

    @Test
    public void testGetTopic() {

        // Returns a topic but we aren't using it here
        datasetTopicTransformer.getTopic(mockInvDataset);

        verify(this.mockTopicBuilder).build(THREDDS_PROPERTY_NAME, Taxonomy.DEFAULT);

        //assertThat(topic.getName(), is(topicName));
        assertNotNull(mockTopic);
    }

    @Test
    public void testTransform() {

        datasetTopicTransformer.transform(mockInvDataset, mockDataset);

        verify(this.mockDataset).addTopic(mockTopic);

    }


}
