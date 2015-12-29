package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.ContainerType;
import sgf.gateway.model.metadata.Dataset;

public class DatasetTypeFieldExtractor extends DatasetAbstractFieldExtractor {

    private final String datasetDocType;
    private final String projectDocType;
    private final String softwareDocType;

    public DatasetTypeFieldExtractor(String datasetDocType, String projectDocType, String softwareDocType) {

        this.datasetDocType = datasetDocType;
        this.projectDocType = projectDocType;
        this.softwareDocType = softwareDocType;
    }

    @Override
    protected Object getValue(Dataset dataset) {

        String value = this.getDocTypeFromDataset(dataset);

        if (dataset.isSoftwareDataset()) {
            value = this.softwareDocType;
        }

        return value;
    }

    private String getDocTypeFromDataset(Dataset dataset) {

        String docType;

        if (dataset.getContainerType().equals(ContainerType.DATASET)) {
            docType = this.datasetDocType;
        } else {
            docType = this.projectDocType;
        }

        return docType;
    }
}
