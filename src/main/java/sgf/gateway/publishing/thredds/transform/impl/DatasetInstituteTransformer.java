package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.descriptive.builder.InstitutionBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetInstituteTransformer implements ThreddsDatasetTransformer {

    private InstitutionBuilder institutionBuilder;

    public DatasetInstituteTransformer(InstitutionBuilder institutionBuilder) {

        this.institutionBuilder = institutionBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        Institution theInstitution = getInstitution(invDataset);

        dataset.setInstitution(theInstitution);
    }

    private Institution getInstitution(InvDataset invDataset) {

        Institution theInstitution = null;

        String institutionName = invDataset.findProperty("institute");

        if (institutionName != null) {
            theInstitution = this.institutionBuilder.build(institutionName);
        }

        return theInstitution;
    }
}
