package sgf.gateway.model.metadata.builder.impl;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Location;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.builder.LocationBuilder;

/**
 * Implementation of the LocationBuilder interface.
 * <p/>
 * Note: This implementation does not create a new instance of a Location if it doesn't already exist. This behavior can be used with a controlled list of
 * locations.
 *
 * @author hannah
 */
public class LocationBuilderImpl implements LocationBuilder {

    private DatasetRepository datasetRepository;

    public LocationBuilderImpl(DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;
    }

    /**
     * {@inheritDoc}
     */
    public Location build(String locationName, Taxonomy locationType) {

        String trimmedLocationName = locationName.trim();

        Location location = this.datasetRepository.findLocation(trimmedLocationName, locationType);

        return location;
    }
}
