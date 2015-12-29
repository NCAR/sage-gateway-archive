package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.DataType;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import sgf.gateway.service.publishing.api.PublishingException;
import thredds.catalog.InvDataset;
import ucar.nc2.constants.FeatureType;

public class DatasetDataTypeTransformer implements ThreddsDescriptiveMetadataTransformer {

    public static final String THREDDS_PROPERTY_NAME = "dataType";

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        descriptiveMetadata.removeAllDataTypes();

        DataType dataType = this.getDataType(invDataset);

        if (dataType != null) {

            descriptiveMetadata.addDataType(dataType);
        }
    }

    protected DataType getDataType(InvDataset invDataset) {

        DataType dataType = null;

        String threddsDataTypeString = this.getThreddsDataTypeString(invDataset);

        if (threddsDataTypeString != null) {

            try {

                dataType = DataType.valueOf(threddsDataTypeString);

            } catch (final IllegalArgumentException illegalArgExcept) {

                throw new PublishingException("Unknown data type.", illegalArgExcept);
            }
        }

        return dataType;
    }

    protected String getThreddsDataTypeString(InvDataset invDataset) {

        String threddsDataTypeString = null;

        if (invDataset.getDataType() != null) {

            FeatureType featureType = invDataset.getDataType();

            threddsDataTypeString = convertThreddsDataTypeString(featureType.toString());

        } else if (invDataset.getDocumentation(THREDDS_PROPERTY_NAME) != null) {

            String dataTypeString = invDataset.getDocumentation(THREDDS_PROPERTY_NAME);

            threddsDataTypeString = convertThreddsDataTypeString(dataTypeString);
        }

        return threddsDataTypeString;
    }

    protected String convertThreddsDataTypeString(String featureTypeString) {

        return featureTypeString.toUpperCase().replace(' ', '_');
    }
}
