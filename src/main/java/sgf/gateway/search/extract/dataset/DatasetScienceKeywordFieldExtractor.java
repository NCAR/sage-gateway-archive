package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.ScienceKeyword;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetScienceKeywordFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetScienceKeywordFieldExtractor() {
        super();
    }

    // For search
    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();
        Collection<ScienceKeyword> keywords = dataset.getDescriptiveMetadata().getScienceKeywords();

        for (ScienceKeyword keyword : keywords) {

            addIfNotNull(value, keyword.getTopic());
            addIfNotNull(value, keyword.getTerm());
            addIfNotNull(value, keyword.getVariableLevel1());
            addIfNotNull(value, keyword.getVariableLevel2());
            addIfNotNull(value, keyword.getVariableLevel3());
            addIfNotNull(value, keyword.getVariableLevel4());
        }

        return value;
    }

    private void addIfNotNull(Collection<String> value, String name) {
        if (name != null) {
            value.add(name);
        }
    }

}
