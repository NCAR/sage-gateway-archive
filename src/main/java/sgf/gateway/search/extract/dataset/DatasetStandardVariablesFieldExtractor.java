package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.Variable;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetStandardVariablesFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetStandardVariablesFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        for (Variable variable : dataset.getCurrentDatasetVersion().getVariables()) {
            for (StandardName standardName : variable.getStandardNames()) {
                value.add(standardName.getName());
            }
        }

        return value;
    }
}
