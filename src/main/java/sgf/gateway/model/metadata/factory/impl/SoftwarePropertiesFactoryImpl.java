package sgf.gateway.model.metadata.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.SoftwareProperties;
import sgf.gateway.model.metadata.SoftwarePropertiesImpl;
import sgf.gateway.model.metadata.factory.SoftwarePropertiesFactory;

public class SoftwarePropertiesFactoryImpl implements SoftwarePropertiesFactory {

    public SoftwareProperties create(Dataset dataset) {

        Assert.notNull(dataset, "Enclosing dataset cannot be null");

        SoftwarePropertiesImpl props = new SoftwarePropertiesImpl(dataset);

        return props;
    }

}
