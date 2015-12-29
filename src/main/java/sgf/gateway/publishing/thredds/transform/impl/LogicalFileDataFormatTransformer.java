package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.factory.DataFormatFactory;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.publishing.thredds.transform.ThreddsLogicalFileTransformer;
import thredds.catalog.DataFormatType;
import thredds.catalog.InvDataset;

public class LogicalFileDataFormatTransformer implements ThreddsLogicalFileTransformer {

    private DatasetRepository datasetRepository;
    private DataFormatFactory dataFormatFactory;

    public LogicalFileDataFormatTransformer(DatasetRepository datasetRepository, DataFormatFactory dataFormatFactory) {

        this.datasetRepository = datasetRepository;
        this.dataFormatFactory = dataFormatFactory;
    }

    @Override
    public void transform(InvDataset invDatasetFile, LogicalFile logicalFile) {

        DataFormat dataFormat = this.getDataFormat(invDatasetFile);

        logicalFile.setDataFormat(dataFormat);
    }

    protected DataFormat getDataFormat(InvDataset invDatasetFile) {

        String dataFormatName = this.getDataFormatName(invDatasetFile);

        DataFormat dataFormat = this.datasetRepository.findDataFormat(dataFormatName);

        if (dataFormat == null) {

            dataFormat = this.dataFormatFactory.create(dataFormatName);
        }

        return dataFormat;
    }

    protected String getDataFormatName(InvDataset invDatasetFile) {

        String dataFormatName = null;

        DataFormatType formatType = invDatasetFile.getDataFormatType();

        if (null != formatType) {

            dataFormatName = formatType.toString();

            if (StringUtils.hasText(dataFormatName)) {

                dataFormatName = dataFormatName.trim();
            }
        }

        return dataFormatName;
    }

}
