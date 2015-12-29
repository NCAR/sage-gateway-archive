package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.SoftwareProperties;

public interface SoftwarePropertiesFactory {

    SoftwareProperties create(Dataset dataset);

}
