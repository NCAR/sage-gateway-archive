package sgf.gateway.web.controllers.dataset.command;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.dataaccess.FileAccessPointComparator;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.LogicalFileComparator;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DatasetVersionFilesCommand {

    final LogicalFileComparator filesComparator = new LogicalFileComparator();
    final FileAccessPointComparator fapComparator = new FileAccessPointComparator();

    private DatasetVersion datasetVersion;

    public DatasetVersionFilesCommand() {
        super();
    }

    public DatasetVersion getDatasetVersion() {
        return this.datasetVersion;
    }

    public void setDatasetVersion(DatasetVersion datasetVersion) {
        this.datasetVersion = datasetVersion;
    }

    public Dataset getDataset() {
        return this.datasetVersion.getDataset();
    }

    public Collection<LogicalFile> getSortedLogicalFiles() {

        // retrieve the logical files of the dataset version and sort
        List<LogicalFile> logicalFiles = new ArrayList<LogicalFile>(this.datasetVersion.getLogicalFiles());
        Collections.sort(logicalFiles, this.filesComparator);

        return logicalFiles;
    }

    public Collection<FileAccessPoint> getSortedFileAccessPoints(LogicalFile logicalFile) {

        // retrieve the file access points of the logical file and sort
        List<FileAccessPoint> fileAccessPoints = new ArrayList<FileAccessPoint>(logicalFile.getFileAccessPoints());
        Collections.sort(fileAccessPoints, this.fapComparator);

        return fileAccessPoints;
    }

    public String getRelativeFileAccessPointURI(FileAccessPoint fileAccessPoint) {

        URI accessUri = fileAccessPoint.getEndpoint();

        String result = accessUri.getRawSchemeSpecificPart();

        return result;
    }

}
