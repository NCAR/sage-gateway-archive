package sgf.gateway.model.metadata.activities.modeling.factory;

import sgf.gateway.model.metadata.activities.modeling.Ensemble;

public interface EnsembleFactory {

    /**
     * Creates a new Ensemble object.
     *
     * @param name the name
     * @return the ensemble impl
     */
    Ensemble createEnsemble(String name);
}
