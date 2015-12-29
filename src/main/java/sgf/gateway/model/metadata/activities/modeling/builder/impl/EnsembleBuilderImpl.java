package sgf.gateway.model.metadata.activities.modeling.builder.impl;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;
import sgf.gateway.model.metadata.activities.modeling.builder.EnsembleBuilder;
import sgf.gateway.model.metadata.activities.modeling.factory.EnsembleFactory;

/**
 * Tries to retrieve the named ensemble from the database. If the ensemble doesn't exist, it creates a new ensemble.
 */
public class EnsembleBuilderImpl implements EnsembleBuilder {

    private DatasetRepository datasetRepository;
    private EnsembleFactory ensembleFactory;

    public EnsembleBuilderImpl(DatasetRepository datasetRepository, EnsembleFactory ensembleFactory) {

        this.datasetRepository = datasetRepository;
        this.ensembleFactory = ensembleFactory;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Ensemble build(String name) {

        String ensembleName = name.trim();

        Ensemble ensemble = find(ensembleName);

        if (null == ensemble) {

            ensemble = create(ensembleName);
        }

        return ensemble;
    }

    protected Ensemble find(String ensembleName) {

        Ensemble result = this.datasetRepository.findEnsemble(ensembleName);

        return result;
    }

    protected Ensemble create(String ensembleName) {
        Ensemble result = this.ensembleFactory.createEnsemble(ensembleName);

        return result;
    }


}
