package sgf.gateway.model.metadata;

import java.util.Comparator;

public class TopicComparator implements Comparator<Topic> {

    /**
     * {@inheritDoc}
     */
    public int compare(Topic topic1, Topic topic2) {
        return topic1.getName().compareTo(topic2.getName());
    }

}
