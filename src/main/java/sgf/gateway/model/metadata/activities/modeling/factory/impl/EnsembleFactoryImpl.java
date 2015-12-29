package sgf.gateway.model.metadata.activities.modeling.factory.impl;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;
import sgf.gateway.model.metadata.activities.modeling.EnsembleImpl;
import sgf.gateway.model.metadata.activities.modeling.factory.EnsembleFactory;

public class EnsembleFactoryImpl implements EnsembleFactory {

    /**
     * The new instance identifier strategy.
     */
    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new ensemble factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public EnsembleFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * Creates the ensemble.
     *
     * @param name the name
     * @return the ensemble impl
     */
    public Ensemble createEnsemble(String name) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(EnsembleImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        EnsembleImpl ensemble = new EnsembleImpl((UUID) vuId.getIdentifierValue(), null, name);

        return ensemble;
    }
}
