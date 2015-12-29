package sgf.gateway.model.metadata.activities.modeling.factory;

import sgf.gateway.model.metadata.activities.modeling.Experiment;

public interface ExperimentFactory {

    /**
     * Creates a new Experiment object.
     *
     * @param name the name
     * @return the experiment impl
     */
    Experiment createExperiment(String name);
}
