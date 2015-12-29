package sgf.gateway.model.metadata;

import java.util.Comparator;

public class DatasetComparator implements Comparator<Dataset> {

    /**
     * {@inheritDoc}
     */
    public int compare(Dataset dataset1, Dataset dataset2) {
        return dataset1.getTitle().compareTo(dataset2.getTitle());
    }

}
