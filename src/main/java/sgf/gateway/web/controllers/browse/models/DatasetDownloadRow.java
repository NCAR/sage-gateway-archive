package sgf.gateway.web.controllers.browse.models;

import sgf.gateway.model.metadata.Dataset;

import java.util.Collection;

public class DatasetDownloadRow {

    private final Dataset dataset;
    private final Collection<LogicalFileDownloadRow> logicalFileDownloadRowList;

    public DatasetDownloadRow(Dataset dataset, Collection<LogicalFileDownloadRow> logicalFileDownloadRowList) {

        this.dataset = dataset;
        this.logicalFileDownloadRowList = logicalFileDownloadRowList;
    }

    public Dataset getDataset() {

        return this.dataset;
    }

    public Collection<LogicalFileDownloadRow> getLogicalFileDownloadRows() {

        return this.logicalFileDownloadRowList;
    }
}
