package sgf.gateway.web.controllers.topic;

import org.junit.Test;
import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Topic;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllTopicsTopicListStrategyTest {

    @Test
    public void verifyReturnResults() {

        Topic mockTopic1 = mock(Topic.class);
        Topic mockTopic2 = mock(Topic.class);

        ArrayList<Topic> expectedTopics = new ArrayList<Topic>();
        expectedTopics.add(mockTopic1);
        expectedTopics.add(mockTopic2);

        TopicRepository mockTopicRepository = mock(TopicRepository.class);

        when(mockTopicRepository.getAll()).thenReturn(expectedTopics);

        AllTopicsTopicListStrategy strategy = new AllTopicsTopicListStrategy(mockTopicRepository);

        Collection<Topic> returnedTopics = strategy.getTopics();

        assertThat(returnedTopics, hasItems(mockTopic1, mockTopic2));
    }
}
