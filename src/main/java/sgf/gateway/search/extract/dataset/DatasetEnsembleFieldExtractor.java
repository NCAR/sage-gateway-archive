package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.ActivityType;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;

public class DatasetEnsembleFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetEnsembleFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        String value = null;

        Ensemble ensemble = (Ensemble) dataset.getActivity(ActivityType.ENSEMBLE);

        if (ensemble != null) {
            value = ensemble.getName();
        }

        return value;
    }
}
