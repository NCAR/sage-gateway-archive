package sgf.gateway.model.metadata.builder.impl;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.builder.DataFormatBuilder;
import sgf.gateway.model.metadata.factory.DataFormatFactory;

public class DataFormatBuilderImpl implements DataFormatBuilder {

    private DatasetRepository datasetRepository;
    private DataFormatFactory dataFormatFactory;

    public DataFormatBuilderImpl(DatasetRepository datasetRepository, DataFormatFactory dataFormatFactory) {

        this.datasetRepository = datasetRepository;
        this.dataFormatFactory = dataFormatFactory;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.builder.DataFormatBuilder#build(java.lang.String)
     */
    public synchronized DataFormat build(String dataFormatName) {

        String formatName = dataFormatName.trim();

        DataFormat dataFormat = find(formatName);

        if (null == dataFormat) {

            dataFormat = create(formatName);
        }

        return dataFormat;
    }

    protected DataFormat find(String dataFormat) {

        DataFormat result;

        result = this.datasetRepository.findDataFormat(dataFormat);

        return result;
    }

    protected DataFormat create(String dataFormat) {
        DataFormat result = this.dataFormatFactory.create(dataFormat, null);

        return result;
    }
}
