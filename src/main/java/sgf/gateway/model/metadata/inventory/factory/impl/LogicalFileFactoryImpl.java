package sgf.gateway.model.metadata.inventory.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.LogicalFileImpl;
import sgf.gateway.model.metadata.inventory.factory.LogicalFileFactory;

public class LogicalFileFactoryImpl implements LogicalFileFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new logical file factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public LogicalFileFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

	/*
     * (non-Javadoc)
	 * 
	 * @see sgf.gateway.model.metadata.inventory.factory.impl.LogicalFileFactory#createLogicalFile(java.lang.String, java.lang.String, 
	 * 	sgf.gateway.model.metadata.IDataset,
	 * java.lang.Long)
	 */

    /**
     * {@inheritDoc}
     */
    public LogicalFile createLogicalFile(String primaryIdentifier, String lineageIdentifier, String versionIdentifier,
                                         DatasetVersion datasetVersion, String name, Long size, DataFormat dataFormat, String label,
                                         String cmorTrackingIdentifier) {

        Assert.notNull(primaryIdentifier, "primaryIdentifier cannot be null");
        Assert.notNull(lineageIdentifier, "lineageIdentifier cannot be null");
        Assert.notNull(datasetVersion, "datasetVersion cannot be null");
        Assert.notNull(name, "name cannot be null");
        Assert.notNull(size, "size cannot be null");

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(LogicalFileImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        LogicalFile logicalFile = new LogicalFileImpl((UUID) vuId.getIdentifierValue(), vuId.getVersion(), primaryIdentifier, lineageIdentifier,
                versionIdentifier, datasetVersion, name, size, dataFormat, label, cmorTrackingIdentifier);

        return logicalFile;
    }

}
