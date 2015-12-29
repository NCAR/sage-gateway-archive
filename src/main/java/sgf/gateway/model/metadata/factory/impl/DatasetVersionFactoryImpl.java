package sgf.gateway.model.metadata.factory.impl;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetImpl;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.DatasetVersionImpl;
import sgf.gateway.model.metadata.factory.DatasetVersionFactory;
import sgf.gateway.model.security.User;

import java.net.URI;

public class DatasetVersionFactoryImpl implements DatasetVersionFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    public DatasetVersionFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    public DatasetVersion create(String versionIdentifier, Dataset dataset, User publisher, String label, String comment,
                                 URI authoritativeSourceURI) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(DatasetImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        DatasetVersionImpl newDatasetVersion = new DatasetVersionImpl((UUID) vuId.getIdentifierValue(), vuId.getVersion(), versionIdentifier, dataset,
                publisher, comment, label);

        newDatasetVersion.setAuthoritativeSourceURI(authoritativeSourceURI);

        return newDatasetVersion;
    }

}
