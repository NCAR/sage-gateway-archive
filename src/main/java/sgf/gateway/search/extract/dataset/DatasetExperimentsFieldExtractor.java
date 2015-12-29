package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.ActivityType;
import sgf.gateway.model.metadata.activities.modeling.Experiment;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetExperimentsFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetExperimentsFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        Experiment experiment = (Experiment) dataset.getActivity(ActivityType.EXPERIMENT);

        if (experiment != null) {
            value.add(experiment.getName());
        }

        return value;
    }
}
