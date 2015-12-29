package sgf.gateway.model.metadata.descriptive.factory;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;

public interface DescriptiveMetadataFactory {

    DescriptiveMetadata create(Dataset dataset);

}
