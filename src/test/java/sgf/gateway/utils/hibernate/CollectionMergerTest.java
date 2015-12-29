package sgf.gateway.utils.hibernate;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.TopicImpl;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;

public class CollectionMergerTest {

    private Collection<Topic> originalTopicCollection;
    private Collection<Topic> newTopicCollection;

    @Before
    public void setup() {

        originalTopicCollection = new HashSet<Topic>();
        newTopicCollection = new HashSet<Topic>();
    }

    @Test
    public void addOneNewTopicToEmptyCollection() {

        Topic defaultTopic = new TopicImpl(new UUID("7f65b5c0-3d44-413e-b0fc-87f0c2aa2371"), 0, "newTopicName", Taxonomy.DEFAULT);

        newTopicCollection.add(defaultTopic);

        CollectionMerger.mergeTopicCollection(Taxonomy.DEFAULT, originalTopicCollection, newTopicCollection);

        assertThat(originalTopicCollection.size(), is(1));
        assertThat(originalTopicCollection, contains(defaultTopic));
    }

    @Test
    public void addOneNewTopicToCollection() {

        Topic defaultTopic = new TopicImpl(new UUID("f18292c4-a29e-4528-bcbb-2d4ad68d6a60"), 0, "originalTopicName", Taxonomy.DEFAULT);

        originalTopicCollection.add(defaultTopic);

        Topic isoTopic = new TopicImpl(new UUID("7f65b5c0-3d44-413e-b0fc-87f0c2aa2371"), 0, "newTopicName", Taxonomy.ISO);

        newTopicCollection.add(isoTopic);

        CollectionMerger.mergeTopicCollection(Taxonomy.ISO, originalTopicCollection, newTopicCollection);

        assertThat(originalTopicCollection.size(), is(2));
        assertThat(originalTopicCollection, hasItems(defaultTopic, isoTopic));
    }

    @Test
    public void removeTopicFromCollection() {

        Topic defaultTopic = new TopicImpl(new UUID("f18292c4-a29e-4528-bcbb-2d4ad68d6a60"), 0, "originalTopicName", Taxonomy.DEFAULT);
        originalTopicCollection.add(defaultTopic);

        CollectionMerger.mergeTopicCollection(Taxonomy.DEFAULT, originalTopicCollection, newTopicCollection);

        assertThat(originalTopicCollection.size(), is(0));
    }

    @Test
    public void topicNotRemovedFromCollection() {

        Topic defaultTopic = new TopicImpl(new UUID("f18292c4-a29e-4528-bcbb-2d4ad68d6a60"), 0, "originalTopicName", Taxonomy.DEFAULT);
        originalTopicCollection.add(defaultTopic);

        CollectionMerger.mergeTopicCollection(Taxonomy.ISO, originalTopicCollection, newTopicCollection);

        assertThat(originalTopicCollection.size(), is(1));
        assertThat(originalTopicCollection, contains(defaultTopic));
    }

    @Test
    public void onlyISOTopicRemovedFromCollection() {

        Topic defaultTopic = new TopicImpl(new UUID("f18292c4-a29e-4528-bcbb-2d4ad68d6a60"), 0, "originalTopicName", Taxonomy.DEFAULT);
        Topic isoTopic = new TopicImpl(new UUID("7f65b5c0-3d44-413e-b0fc-87f0c2aa2371"), 0, "newTopicName", Taxonomy.ISO);
        originalTopicCollection.add(defaultTopic);
        originalTopicCollection.add(isoTopic);

        CollectionMerger.mergeTopicCollection(Taxonomy.ISO, originalTopicCollection, newTopicCollection);

        assertThat(originalTopicCollection.size(), is(1));
        assertThat(originalTopicCollection, contains(defaultTopic));
    }

    @Test
    public void onlyOneISOTopicRemovedFromCollection() {

        Topic isoTopicToBeRemoved = new TopicImpl(new UUID("f18292c4-a29e-4528-bcbb-2d4ad68d6a60"), 0, "isoTopicToBeRemovedName", Taxonomy.ISO);
        Topic isoTopic = new TopicImpl(new UUID("7f65b5c0-3d44-413e-b0fc-87f0c2aa2371"), 0, "isoTopicName", Taxonomy.ISO);

        originalTopicCollection.add(isoTopicToBeRemoved);
        originalTopicCollection.add(isoTopic);

        newTopicCollection.add(isoTopic);

        CollectionMerger.mergeTopicCollection(Taxonomy.ISO, originalTopicCollection, newTopicCollection);

        assertThat(originalTopicCollection.size(), is(1));
        assertThat(originalTopicCollection, contains(isoTopic));
    }
}
