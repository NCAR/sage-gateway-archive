/*
 *
 */
package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.search.extract.FieldExtractor;

/**
 * @author brownrig
 */
abstract public class DatasetAbstractFieldExtractor implements FieldExtractor {

    @Override
    public Object extract(Object object) {

        Dataset dataset = (Dataset) object;
        Object value = getValue(dataset);

        return value;
    }

    abstract protected Object getValue(Dataset dataset);

}
