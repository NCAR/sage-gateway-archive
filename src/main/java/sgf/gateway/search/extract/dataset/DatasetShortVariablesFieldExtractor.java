package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.Variable;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetShortVariablesFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetShortVariablesFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        for (Variable variable : dataset.getCurrentDatasetVersion().getVariables()) {
            value.add(variable.getName());
        }

        return value;
    }
}
