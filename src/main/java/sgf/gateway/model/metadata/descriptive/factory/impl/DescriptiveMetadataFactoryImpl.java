package sgf.gateway.model.metadata.descriptive.factory.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadataImpl;
import sgf.gateway.model.metadata.descriptive.factory.DescriptiveMetadataFactory;

public class DescriptiveMetadataFactoryImpl implements DescriptiveMetadataFactory {

    /**
     * {@inheritDoc}
     */
    public DescriptiveMetadata create(Dataset dataset) {

        return new DescriptiveMetadataImpl(dataset);
    }

}
