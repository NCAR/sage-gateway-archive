package sgf.gateway.model.metadata.builder.impl;

import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.DataProductType;
import sgf.gateway.model.metadata.builder.DataProductTypeBuilder;
import sgf.gateway.model.metadata.factory.DataProductTypeFactory;

public class DataProductTypeBuilderImpl implements DataProductTypeBuilder {

    private MetadataRepository metadataRepository;
    private DataProductTypeFactory dataProductTypeFactory;

    public DataProductTypeBuilderImpl(MetadataRepository metadataRepository,
                                      DataProductTypeFactory dataProductTypeFactory) {

        this.dataProductTypeFactory = dataProductTypeFactory;
        this.metadataRepository = metadataRepository;
    }

    public synchronized DataProductType build(String name) {

        return build(name, null);
    }

    public synchronized DataProductType build(String name, String description) {

        String productTypeName = name.trim();

        DataProductType dataProductType = find(productTypeName);

        if (null == dataProductType) {

            String productDescription = description;

            if (null == description) { // force a description

                productDescription = name + " product description.";
            }

            dataProductType = create(productTypeName, productDescription);
        }

        return dataProductType;
    }

    protected DataProductType find(String name) {

        DataProductType result;

        result = this.metadataRepository.findDataProductTypeByName(name);

        return result;
    }

    protected DataProductType create(String name, String description) {
        DataProductType result = this.dataProductTypeFactory.create(name, description);

        return result;
    }

}
