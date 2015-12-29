package sgf.gateway.service.metadata.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.LogicalFileService;
import sgf.gateway.service.publishing.TransferFilesToDatasetRequest;
import sgf.gateway.service.publishing.dataset.file.TransferFileProcessor;

import java.io.File;
import java.util.List;

public class LogicalFileServiceImpl implements LogicalFileService {

    private final DatasetRepository datasetRepository;
    private final TransferFileProcessor transferFileProcessor;

    public LogicalFileServiceImpl(DatasetRepository datasetRepository, TransferFileProcessor transferFileProcessor) {

        super();
        this.datasetRepository = datasetRepository;
        this.transferFileProcessor = transferFileProcessor;
    }

    @Override
    public List<LogicalFile> transferFilesToDataset(TransferFilesToDatasetRequest transferFilesToDatasetRequest) {
        return transferFileProcessor.transfer(transferFilesToDatasetRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFile(String datasetIdentifier, String fileName) {

        Dataset dataset = datasetRepository.getByShortName(datasetIdentifier);

        DatasetVersion datasetVersion = dataset.getCurrentDatasetVersion();

        LogicalFile logicalFile = datasetVersion.getLogicalFileByFileName(fileName);

        if (logicalFile != null && logicalFile.isDeletable()) {

            datasetVersion.removeLogicalFile(logicalFile);

            deleteFromFileSystem(logicalFile);
        }
    }

    private void deleteFromFileSystem(LogicalFile logicalFile) {

        File file = logicalFile.getFile();

        boolean exists = file.exists();

        if (exists) {

            boolean deleted = file.delete();

            if (!deleted) {

                throw new FileNotDeletedException(file);
            }
        }
    }
}
