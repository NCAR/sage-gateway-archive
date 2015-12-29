package sgf.gateway.model.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to filter collections of the dataset to a collection of datasets of a particular state.
 */
public class DatasetFilterImpl {

    /**
     * Private utility class constructor.
     */
    private DatasetFilterImpl() {

    }

    /**
     * Filters the collection of datasets to the desired state.
     *
     * @param state    the state to filter to.
     * @param datasets the collection of datasets to filter.
     * @return the colleciton of dataset in the specified state.
     */
    public static Collection<Dataset> filter(PublishedState state, Collection<Dataset> datasets) {

        List<Dataset> result = new ArrayList<Dataset>();

        for (Dataset dataset : datasets) {

            if (state == dataset.getPublishedState()) {

                result.add(dataset);
            }
        }

        return result;
    }

    /**
     * Filters the input collection to only return published datasets.
     *
     * @param datasets unfiltered collection of datasets.
     * @return datasets that are published.
     */
    public static Collection<Dataset> published(Collection<Dataset> datasets) {

        return filter(PublishedState.PUBLISHED, datasets);
    }

    /**
     * Filters the input collection to only return pre-published datasets.
     *
     * @param datasets unfiltered collection of datasets.
     * @return datasets that are pre-published.
     */
    public static Collection<Dataset> prePublished(Collection<Dataset> datasets) {

        return filter(PublishedState.PRE_PUBLISHED, datasets);
    }

    /**
     * Filters the input collection to only return retracted datasets.
     *
     * @param datasets unfiltered collection of datasets.
     * @return datasets that are retracted.
     */
    public static Collection<Dataset> retracted(Collection<Dataset> datasets) {

        return filter(PublishedState.RETRACTED, datasets);
    }

    public static List<Dataset> getDatasetParentList(Dataset dataset) {

        List<Dataset> parentList = new ArrayList<Dataset>();

        addParent(parentList, dataset);

        return parentList;
    }

    public static void addParent(List<Dataset> parentList, Dataset dataset) {

        if (dataset.isTopLevelDataset() == false) {

            DatasetImpl datasetParent = (DatasetImpl) dataset.getParent();

            parentList.add(0, datasetParent);

            addParent(parentList, datasetParent);
        }
    }
}
