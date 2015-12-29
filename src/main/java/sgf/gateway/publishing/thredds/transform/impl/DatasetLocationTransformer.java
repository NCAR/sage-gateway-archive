package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Location;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.builder.LocationBuilder;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata;

public class DatasetLocationTransformer implements ThreddsDescriptiveMetadataTransformer {

    private LocationBuilder locationBuilder;

    public DatasetLocationTransformer(LocationBuilder locationBuilder) {

        this.locationBuilder = locationBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        clearAllLocations(descriptiveMetadata);

        ThreddsMetadata.GeospatialCoverage geoCoverage = invDataset.getGeospatialCoverage();

        if (geoCoverage != null) {

            for (ThreddsMetadata.Vocab name : geoCoverage.getNames()) {

                Location location = getLocation(name);

                descriptiveMetadata.addLocation(location);
            }
        }
    }

    private Location getLocation(ThreddsMetadata.Vocab name) {

        Location location = this.locationBuilder.build(name.getText(), Taxonomy.GCMD);

        return location;
    }

    private void clearAllLocations(DescriptiveMetadata descriptiveMetadata) {
        descriptiveMetadata.removeAllLocations();
    }
}
