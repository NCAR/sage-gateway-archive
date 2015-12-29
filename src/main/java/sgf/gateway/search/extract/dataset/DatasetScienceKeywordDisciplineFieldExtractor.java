package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.ScienceKeyword;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetScienceKeywordDisciplineFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetScienceKeywordDisciplineFieldExtractor() {
        super();
    }

    // For Facets
    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();
        Collection<ScienceKeyword> keywords = dataset.getDescriptiveMetadata().getScienceKeywords();

        if (null != keywords) {
            for (ScienceKeyword keyword : keywords) {

                if (null != keyword.getTopic()) {
                    value.add(initcap(keyword.getTopic()));
                }
            }
        }

        return value;
    }

    private String initcap(String name) {

        String initcapped = null;

        if (name != null) {
            initcapped = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }

        return initcapped;
    }

}
