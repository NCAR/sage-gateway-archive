package sgf.gateway.service.publishing.dataset.file;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.factory.LogicalFileFactory;
import sgf.gateway.service.publishing.TransferFilesToDatasetRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TransferFileProcessor {

    private final LocalFileDirectoryStrategy directoryStrategy;
    private final DatasetRepository datasetRepository;
    private final LogicalFileRepository logicalFileRepository;
    private final LogicalFileFactory logicalFileFactory;

    public TransferFileProcessor(LocalFileDirectoryStrategy directoryStrategy, DatasetRepository datasetRepository,
                                 LogicalFileRepository logicalFileRepository, LogicalFileFactory logicalFileFactory) {

        super();
        this.directoryStrategy = directoryStrategy;
        this.datasetRepository = datasetRepository;
        this.logicalFileRepository = logicalFileRepository;
        this.logicalFileFactory = logicalFileFactory;
    }

    public List<LogicalFile> transfer(TransferFilesToDatasetRequest transferRequest) {

        List<UploadedFile> uploadedFiles = this.transform(transferRequest);

        try {

            this.transactTransfer(uploadedFiles);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this.getCreatedOrUpdatedLogicalFiles(uploadedFiles);
    }

    protected List<UploadedFile> transform(TransferFilesToDatasetRequest transferRequest) {

        Dataset dataset = this.datasetRepository.getByShortName(transferRequest.getDatasetIdentifier());

        List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

        for (MultipartFile source : transferRequest.getFiles()) {

            if (StringUtils.hasText(source.getOriginalFilename())) {

                UploadedFile uploadedFile = this.transform(dataset, source);
                uploadedFiles.add(uploadedFile);
            }
        }

        return uploadedFiles;
    }

    protected UploadedFile transform(Dataset dataset, MultipartFile source) {

        File target = this.getTarget(dataset, source);

        UploadedFile uploadedFile = new UploadedFile(dataset.getShortName(), source, target);

        LogicalFile logicalFile = this.getLogicalFile(dataset, target);

        if (logicalFile != null) {

            uploadedFile.setLogicalFileId(logicalFile.getIdentifier());
            uploadedFile.setPreexisting(true);
        }

        return uploadedFile;
    }

    protected File getTarget(Dataset dataset, MultipartFile source) {

        File datasetDirectory = this.directoryStrategy.getDirectory(dataset);
        File target = new File(datasetDirectory, source.getOriginalFilename());

        return target;
    }

    protected LogicalFile getLogicalFile(Dataset dataset, File target) {

        LogicalFile logicalFile = null;

        // need a query that is based on dataset and disk location not just name or this is ACADIS specific!!
        List<LogicalFile> logicalFiles = this.logicalFileRepository.findByDatasetShortNameAndLogicalFileName(dataset.getShortName(), target.getName(), false);

        if (!logicalFiles.isEmpty()) {

            logicalFile = logicalFiles.get(0);
        }

        return logicalFile;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void transactTransfer(List<UploadedFile> uploadedFiles) {

        this.createOrUpdateLogicalFiles(uploadedFiles);

        this.transferOnDisk(uploadedFiles);
    }

    protected void createOrUpdateLogicalFiles(List<UploadedFile> uploadedFiles) {

        for (UploadedFile uploadedFile : uploadedFiles) {

            if (uploadedFile.isPreexisting()) {

                this.updateLogicalFile(uploadedFile);

            } else {

                LogicalFile logicalFile = this.createLogicalFile(uploadedFile);
                uploadedFile.setLogicalFileId(logicalFile.getIdentifier());
            }
        }
    }

    protected void updateLogicalFile(UploadedFile uploadedFile) {

        LogicalFile logicalFile = this.logicalFileRepository.get(uploadedFile.getLogicalFileId());
        MultipartFile source = uploadedFile.getSource();

        logicalFile.setSize(source.getSize());
    }

    protected LogicalFile createLogicalFile(UploadedFile uploadedFile) {

        Dataset dataset = this.datasetRepository.getByShortName(uploadedFile.getDatasetShortName());

        DatasetVersion currentDatasetVersion = dataset.getCurrentDatasetVersion();
        File target = uploadedFile.getTarget();
        String targetName = uploadedFile.getTarget().getName();
        long sourceSize = uploadedFile.getSource().getSize();

        LogicalFile logicalFile = this.logicalFileFactory.createLogicalFile(targetName, targetName, "1", currentDatasetVersion, targetName, sourceSize, null, "1", "");

        logicalFile.setDiskLocation(target.getAbsolutePath());

        currentDatasetVersion.addLogicalFile(logicalFile);

        return logicalFile;
    }

    protected void transferOnDisk(List<UploadedFile> uploadedFiles) {

        for (UploadedFile uploadedFile : uploadedFiles) {

            File directory = uploadedFile.getTarget().getParentFile();

            if (!directory.exists()) {
                // Is it safe to assume that mkdirs() always succeeds?
                boolean ignoredResult = directory.mkdirs();
            }

            uploadedFile.transfer();
        }
    }

    protected List<LogicalFile> getCreatedOrUpdatedLogicalFiles(List<UploadedFile> uploadedFiles) {

        List<LogicalFile> logicalFiles = new ArrayList<LogicalFile>();

        for (UploadedFile uploadedFile : uploadedFiles) {
            LogicalFile logicalFile = this.logicalFileRepository.get(uploadedFile.getLogicalFileId());
            logicalFiles.add(logicalFile);
        }

        return logicalFiles;
    }
}
