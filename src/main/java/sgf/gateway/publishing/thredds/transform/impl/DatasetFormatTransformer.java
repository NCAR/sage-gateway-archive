package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.builder.DataFormatBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetFormatTransformer implements ThreddsDatasetTransformer {

    private final DataFormatBuilder dataFormatBuilder;

    public DatasetFormatTransformer(DataFormatBuilder builder) {
        super();
        this.dataFormatBuilder = builder;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        if (hasDataFormat(invDataset)) {
            DataFormat dataFormat = this.getDataFormat(invDataset);
            dataset.addDataFormat(dataFormat);
        }
    }

    protected Boolean hasDataFormat(InvDataset invDataset) {

        Boolean has = false;

        if (invDataset.getDataFormatType() != null) {
            String formatType = invDataset.getDataFormatType().toString();
            has = StringUtils.hasText(formatType);
        }

        return has;
    }

    protected DataFormat getDataFormat(InvDataset invDataset) {

        String formatType = invDataset.getDataFormatType().toString();

        DataFormat dataFormat = this.dataFormatBuilder.build(formatType);

        return dataFormat;
    }
}
