package sgf.gateway.model.metadata.activities.modeling.builder.impl;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.activities.modeling.Experiment;
import sgf.gateway.model.metadata.activities.modeling.builder.ExperimentBuilder;
import sgf.gateway.model.metadata.activities.modeling.factory.ExperimentFactory;

/**
 * Tries to retrieve the named experiment from the database. If the experiment doesn't exist, it creates a new experiment.
 */
public class ExperimentBuilderImpl implements ExperimentBuilder {

    private DatasetRepository datasetRepository;
    private ExperimentFactory experimentFactory;

    public ExperimentBuilderImpl(DatasetRepository datasetRepository, ExperimentFactory experimentFactory) {

        this.datasetRepository = datasetRepository;
        this.experimentFactory = experimentFactory;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Experiment build(String name) {

        String experimentName = name.trim();

        Experiment experiment = find(experimentName);

        if (null == experiment) {

            experiment = create(experimentName);
        }

        return experiment;
    }

    /**
     * looks up experiment by short and full name
     *
     * @param experimentName
     * @return
     */
    protected Experiment find(String experimentName) {

        Experiment result;

        result = this.datasetRepository.findExperimentByShortName(experimentName);
        if (result == null) {
            result = this.datasetRepository.findExperiment(experimentName);
        }

        return result;
    }

    protected Experiment create(String experimentName) {

        Experiment result = this.experimentFactory.createExperiment(experimentName);

        // FIXME: Forces desc and shortname

        // FIXME: Ideally, an event would be sent here to indicate a new object has been created.

        result.setDescription(experimentName);
        result.setShortName(experimentName);

        return result;
    }
}
