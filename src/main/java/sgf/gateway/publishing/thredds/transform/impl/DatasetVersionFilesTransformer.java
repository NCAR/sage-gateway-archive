package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.factory.LogicalFileFactory;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetVersionTransformer;
import sgf.gateway.publishing.thredds.transform.ThreddsLogicalFileTransformer;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDatasetImpl;
import thredds.catalog.ServiceType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatasetVersionFilesTransformer implements ThreddsDatasetVersionTransformer {

    private LogicalFileRepository logicalFileRepository;

    private LogicalFileFactory logicalFileFactory;

    private ThreddsLogicalFileTransformer threddsLogicalFileTransformer;

    public static final String FILE_SIZE_PROPERTY = "size";
    public static final String FILE_VERSION_PROPERTY = "file_version";
    public static final String IDENTIFIER_PROPERTY = "file_id";
    public static final String FILE_TRACKING_ID_PROPERTY = "tracking_id";

    public DatasetVersionFilesTransformer(LogicalFileRepository logicalFileRepository, LogicalFileFactory logicalFileFactory, ThreddsLogicalFileTransformer threddsLogicalFileTransformer) {

        this.logicalFileRepository = logicalFileRepository;
        this.logicalFileFactory = logicalFileFactory;
        this.threddsLogicalFileTransformer = threddsLogicalFileTransformer;
    }

    @Override
    public void transform(InvDataset invDataset, DatasetVersion datasetVersion) {

        Set<LogicalFile> removableLogicalFiles = new HashSet<LogicalFile>(datasetVersion.getLogicalFiles());

        List<InvDataset> invDatasetFiles = invDataset.getDatasets();

        for (InvDataset invDatasetFile : invDatasetFiles) {

            if (isFile(invDatasetFile)) {

                LogicalFile logicalFile = this.getLogicalFile(invDatasetFile, datasetVersion);

                datasetVersion.addLogicalFile(logicalFile);

                logicalFile.associateTo(datasetVersion);

                removableLogicalFiles.remove(logicalFile);

                this.threddsLogicalFileTransformer.transform(invDatasetFile, logicalFile);
            }
        }

        for (LogicalFile logicalFile : removableLogicalFiles) {

            datasetVersion.removeLogicalFile(logicalFile);
        }
    }

    protected boolean isFile(final InvDataset threddsDataset) {

        boolean isFile = false;

        InvDatasetImpl theDataset = (InvDatasetImpl) threddsDataset;

        if (!threddsDataset.isHarvest() && !threddsDataset.hasNestedDatasets() && ((theDataset.getDataSize() > 0) || ((theDataset.getDataSize() == 0) && threddsDataset.hasAccess() && (threddsDataset.getAccess(ServiceType.LAS) == null))) && !isAggregation(theDataset)) {
            isFile = true;
        }

        // Check For ESG - PCMDI Publisher File ID

        if (threddsDataset.getProperties() != null) {

            String aggregationID = threddsDataset.findProperty("file_id");

            if (StringUtils.hasText(aggregationID)) {

                isFile = true;
            }
        }

        return isFile;
    }

    protected boolean isAggregation(final InvDataset threddsDataset) {

        boolean isAggregation = false;

        String datasetId = threddsDataset.getID();

        if ((datasetId != null) && (datasetId.length() > 0)) {

            isAggregation = threddsDataset.getID().toLowerCase().indexOf("aggregation") != -1;
        }

        if (threddsDataset.getProperties() != null) {

            String aggregationID = threddsDataset.findProperty("aggregation_id");

            if (aggregationID != null) {

                isAggregation = true;
            }
        }

        return isAggregation;
    }

    protected LogicalFile getLogicalFile(InvDataset invDatasetFile, DatasetVersion datasetVersion) {

        String fileIdentifier = this.getIdentifier(invDatasetFile);

        LogicalFile logicalFile = this.logicalFileRepository.findByLineageIdentifier(fileIdentifier);

        if (logicalFile == null) {

            logicalFile = this.createLogicalFile(invDatasetFile, datasetVersion);
        }

        return logicalFile;
    }

    protected LogicalFile createLogicalFile(InvDataset invDatasetFile, DatasetVersion datasetVersion) {

        String fileIdentifier = getIdentifier(invDatasetFile);
        String threddsIdentifier = invDatasetFile.getID();
        String fileName = invDatasetFile.getName();
        Long fileSize = getFileSize(invDatasetFile);
        String versionIdentifier = getThreddsFileVersion(invDatasetFile);
        String cmorTrackingIdentifier = getTrackingIdentifier(invDatasetFile);

        LogicalFile logicalFile = this.logicalFileFactory.createLogicalFile(threddsIdentifier, fileIdentifier, versionIdentifier, datasetVersion, fileName, fileSize, null, null, cmorTrackingIdentifier);

        return logicalFile;
    }

    public String getIdentifier(InvDataset threddsDataset) {

        String identifier = threddsDataset.getID();

        if (threddsDataset.getProperties() != null) {

            String datasetIdentifier = threddsDataset.findProperty(IDENTIFIER_PROPERTY);

            if (datasetIdentifier != null) {

                identifier = datasetIdentifier;
            }
        }

        return identifier;
    }

    protected Long getFileSize(InvDataset threddsDataset) {

        Long fileSize = (new Double(((InvDatasetImpl) threddsDataset).getDataSize())).longValue();

        if (threddsDataset.getProperties() != null) {

            // NOTE: Handles explicit PCMDI Publisher size property.
            String fileSizeString = threddsDataset.findProperty(FILE_SIZE_PROPERTY);

            if (fileSizeString != null) {

                fileSize = Long.parseLong(fileSizeString);
            }
        }

        return fileSize;
    }

    protected String getThreddsFileVersion(InvDataset threddsDataset) {

        String version = null;

        if (threddsDataset.getProperties() != null) {

            version = threddsDataset.findProperty(FILE_VERSION_PROPERTY);
        }

        return version;
    }

    private String getTrackingIdentifier(InvDataset threddsDataset) {

        String trackingID = null;

        if (threddsDataset.getProperties() != null) {

            trackingID = threddsDataset.findProperty(FILE_TRACKING_ID_PROPERTY);
        }

        return trackingID;
    }
}
