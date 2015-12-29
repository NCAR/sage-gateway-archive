package sgf.gateway.utils.hibernate;

import org.apache.commons.collections.CollectionUtils;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionMerger {

    /* Makes the originalList have the same items as newList and ordered as newList has them ordered, but it keeps
       originalList items when both lists have the same item according to item.equals().
       */
    public static void mergeList(final List originalList, final List newList) {

        mergeCollection(originalList, newList);

        // Reorder items according to newList ordering
        Collections.sort(originalList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.toString(newList.indexOf(o1)).compareTo(Integer.toString(newList.indexOf(o2)));
            }
        });
    }

    /* Makes the originalCollection have the same items as newCollection, but it keeps
       originalCollection items when both collections have the same item according to item.equals().
       */
    public static void mergeCollection(Collection originalCollection, Collection newCollection) {

        if (newCollection.isEmpty()) {

            originalCollection.clear();

        } else {

            // Gather elements not in the original collection
            Collection elementsAdded = CollectionUtils.subtract(newCollection, originalCollection);
            originalCollection.addAll(elementsAdded);

            // Gather elements not in the new collection
            Collection elementsRemoved = CollectionUtils.subtract(originalCollection, newCollection);
            originalCollection.removeAll(elementsRemoved);
        }
    }

    public static void mergeTopicCollection(Taxonomy taxonomy, Collection<Topic> originalCollection, Collection<Topic> newCollection) {

        Collection elementsAdded = CollectionUtils.subtract(newCollection, originalCollection);
        originalCollection.addAll(elementsAdded);

        Collection<Topic> elementsRemoved = CollectionUtils.subtract(originalCollection, newCollection);

        Topic[] topics = elementsRemoved.toArray(new Topic[0]);

        for (Topic topic : topics) {

            if (!topic.getType().equals(taxonomy)) {

                elementsRemoved.remove(topic);
            }
        }

        originalCollection.removeAll(elementsRemoved);
    }
}
