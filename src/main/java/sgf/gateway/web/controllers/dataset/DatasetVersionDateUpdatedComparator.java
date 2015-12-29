package sgf.gateway.web.controllers.dataset;

import sgf.gateway.model.metadata.DatasetVersion;

import java.util.Comparator;

public class DatasetVersionDateUpdatedComparator implements Comparator<DatasetVersion> {

    @Override
    public int compare(DatasetVersion dv1, DatasetVersion dv2) {

        // We want the the dataset versions sorted latest updated first.  That is why we use the d2 instance first in this case.
        return dv2.getDateCreated().compareTo(dv1.getDateCreated());
    }
}
