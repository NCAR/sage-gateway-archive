package sgf.gateway.model.metadata.inventory.factory;

import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;

public interface LogicalFileFactory {

    /**
     * Creates a new LogicalFile object.
     *
     * @param primaryIdentifier      the primary identifier
     * @param lineageIdentifier      the lineage identifier
     * @param datasetVersion         the dataset version
     * @param name                   the name
     * @param size                   the size
     * @param dataFormat             the data format
     * @param label                  the label used by the Dataset Publisher for identifying the file version
     * @param cmorTrackingIdentifier the cmor tracking identifier
     * @return the logical file
     */
    public LogicalFile createLogicalFile(String primaryIdentifier, String lineageIdentifier, String versionIdentifier,
                                         DatasetVersion datasetVersion, String name, Long size, DataFormat dataFormat, String label,
                                         String cmorTrackingIdentifier);

}
