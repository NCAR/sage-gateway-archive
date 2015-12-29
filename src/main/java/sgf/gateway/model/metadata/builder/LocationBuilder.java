package sgf.gateway.model.metadata.builder;

import sgf.gateway.model.metadata.Location;
import sgf.gateway.model.metadata.Taxonomy;

/**
 * Interface to define the behaviors of a location builder.
 *
 * @author hannah
 */
public interface LocationBuilder {

    Location build(String locationName, Taxonomy locationType);
}
