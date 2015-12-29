package sgf.gateway.model.metadata.activities.modeling.factory.impl;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.activities.modeling.Experiment;
import sgf.gateway.model.metadata.activities.modeling.ExperimentImpl;
import sgf.gateway.model.metadata.activities.modeling.factory.ExperimentFactory;

public class ExperimentFactoryImpl implements ExperimentFactory {

    /**
     * The new instance identifier strategy.
     */
    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new experiment factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public ExperimentFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public Experiment createExperiment(String name) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(ExperimentImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        ExperimentImpl experiment = new ExperimentImpl((UUID) vuId.getIdentifierValue(), null, name);

        return experiment;
    }
}
