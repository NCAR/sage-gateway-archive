package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Location;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetLocationsFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetLocationsFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();
        Collection<Location> locations = dataset.getDescriptiveMetadata().getLocations();

        if (null != locations) {
            for (Location location : locations) {

                if (null != location.getName()) {
                    value.add(location.getName());
                }
            }
        }

        return value;
    }
}
