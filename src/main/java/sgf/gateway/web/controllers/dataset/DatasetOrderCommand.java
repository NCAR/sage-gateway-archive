package sgf.gateway.web.controllers.dataset;

import sgf.gateway.model.metadata.Dataset;

import java.util.List;

public class DatasetOrderCommand {

    private Dataset dataset;

    private List<Dataset> movingDatasets;

    public DatasetOrderCommand(Dataset dataset) {

        this.dataset = dataset;
    }

    public DatasetOrderCommand(Dataset parentDataset, List<Dataset> movingDatasets) {

        this.dataset = parentDataset;
        this.movingDatasets = movingDatasets;
    }

    public String getDatasetShortName() {

        return this.dataset.getShortName();
    }

    public Dataset getDataset() {
        return dataset;
    }

    public List<Dataset> getMovingDatasets() {
        return movingDatasets;
    }

    public void setMovingDatasets(List<Dataset> movingDatasets) {
        this.movingDatasets = movingDatasets;
    }
}
