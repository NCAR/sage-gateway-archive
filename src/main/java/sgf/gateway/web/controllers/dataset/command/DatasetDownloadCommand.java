package sgf.gateway.web.controllers.dataset.command;

import sgf.gateway.model.metadata.Dataset;

public class DatasetDownloadCommand {

    private Dataset dataset = null;
    private String host = null;

    public void setDataset(Dataset dataset) {

        this.dataset = dataset;
    }

    public Dataset getDataset() {

        return this.dataset;
    }

    public void setHost(String host) {

        this.host = host;
    }

    public String getHost() {

        return this.host;
    }
}
