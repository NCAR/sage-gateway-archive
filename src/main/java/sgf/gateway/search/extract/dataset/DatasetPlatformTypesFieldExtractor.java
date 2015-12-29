/*
 *
 */
package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.observing.PlatformType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author brownrig
 */
public class DatasetPlatformTypesFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetPlatformTypesFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();
        Collection<PlatformType> platforms = dataset.getDescriptiveMetadata().getPlatformTypes();

        if (null != platforms) {
            for (PlatformType platform : platforms) {

                if (null != platform.getShortName()) {
                    value.add(platform.getShortName());
                }
            }
        }

        return value;
    }

}
