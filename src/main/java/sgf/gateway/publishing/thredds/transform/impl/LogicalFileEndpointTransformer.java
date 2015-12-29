package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.dataaccess.factory.FileAccessPointFactory;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.publishing.thredds.transform.ThreddsLogicalFileTransformer;
import thredds.catalog.InvAccess;
import thredds.catalog.InvDataset;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicalFileEndpointTransformer implements ThreddsLogicalFileTransformer {

    private FileAccessPointFactory fileAccessPointFactory;

    public LogicalFileEndpointTransformer(FileAccessPointFactory fileAccessPointFactory) {

        this.fileAccessPointFactory = fileAccessPointFactory;
    }

    @Override
    public void transform(InvDataset invDatasetFile, LogicalFile logicalFile) {

        Set<FileAccessPoint> removableFileAccessPoints = new HashSet<FileAccessPoint>(logicalFile.getFileAccessPoints());

        List<InvAccess> invAccessList = invDatasetFile.getAccess();

        for (InvAccess invAccess : invAccessList) {

            FileAccessPoint fileAccessPoint = this.getFileAccessPoint(invAccess, logicalFile);

            logicalFile.addFileAccessPoint(fileAccessPoint);

            removableFileAccessPoints.remove(fileAccessPoint);
        }

        for (FileAccessPoint fileAccessPoint : removableFileAccessPoints) {

            logicalFile.removeFileAccessPoint(fileAccessPoint);
        }
    }

    protected FileAccessPoint getFileAccessPoint(InvAccess invAccess, LogicalFile logicalFile) {

        URI endpoint = invAccess.getStandardUri();

        FileAccessPoint fileAccessPoint = logicalFile.getFileAccessPointByURI(endpoint);

        if (fileAccessPoint == null) {

            fileAccessPoint = this.createFileAccessPoint(invAccess, logicalFile);
        }

        return fileAccessPoint;
    }

    protected FileAccessPoint createFileAccessPoint(InvAccess invAccess, LogicalFile logicalFile) {

        URI endpoint = invAccess.getStandardUri();

        FileAccessPoint fileAccessPoint = this.fileAccessPointFactory.createFileAccessPoint(logicalFile, endpoint);

        return fileAccessPoint;
    }
}
