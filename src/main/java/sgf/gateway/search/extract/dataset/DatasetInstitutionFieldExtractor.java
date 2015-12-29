package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.Institution;

public class DatasetInstitutionFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetInstitutionFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        String value = null;

        Institution institution = dataset.getInstitution();

        if (institution != null) {
            value = institution.getName();
        }

        return value;
    }
}
