package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetRealmsFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetRealmsFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        for (Topic inheritedTopic : dataset.getInheritedTopics()) {

            Taxonomy taxonomy = inheritedTopic.getType();

            if (taxonomy.equals(Taxonomy.REALM)) {
                value.add(inheritedTopic.getName());
            }
        }

        return value;
    }
}
