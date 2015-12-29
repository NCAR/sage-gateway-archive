package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.DataProductType;
import sgf.gateway.model.metadata.builder.DataProductTypeBuilder;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;

public class DatasetDataProductTypeTransformer implements ThreddsDescriptiveMetadataTransformer {

    private DataProductTypeBuilder dataProductTypeBuilder;

    public DatasetDataProductTypeTransformer(DataProductTypeBuilder dataProductTypeBuilder) {

        this.dataProductTypeBuilder = dataProductTypeBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        DataProductType dataProductType = findOrCreateDataProductType(invDataset);

        if (null != dataProductType) {

            descriptiveMetadata.setDataProductType(dataProductType);
        }
    }

    protected DataProductType findOrCreateDataProductType(InvDataset threddsDataset) {

        DataProductType result = null;

        String productString = threddsDataset.findProperty("product");

        if (StringUtils.hasText(productString)) {

            String trimmedName = productString.trim();

            result = this.dataProductTypeBuilder.build(trimmedName);
        }

        return result;
    }

}
