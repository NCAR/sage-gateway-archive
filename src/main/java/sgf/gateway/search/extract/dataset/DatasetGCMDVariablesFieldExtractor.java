package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetGCMDVariablesFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetGCMDVariablesFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        for (Topic inheritedTopic : dataset.getInheritedTopics()) {

            Taxonomy taxonomy = inheritedTopic.getType();

            if (taxonomy.equals(Taxonomy.GCMD)) {
                value.add(inheritedTopic.getName());
            }
        }

        return value;
    }
}
