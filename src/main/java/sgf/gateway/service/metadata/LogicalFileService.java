package sgf.gateway.service.metadata;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.publishing.TransferFilesToDatasetRequest;

import java.util.List;

public interface LogicalFileService {

    List<LogicalFile> transferFilesToDataset(TransferFilesToDatasetRequest transferFilesToDatasetRequest);

    void deleteFile(String datasetIdentifier, String filename);

}
