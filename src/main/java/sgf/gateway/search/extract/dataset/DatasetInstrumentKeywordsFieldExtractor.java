/*
 *
 */
package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;

import java.util.ArrayList;
import java.util.Collection;


public class DatasetInstrumentKeywordsFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetInstrumentKeywordsFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();
        Collection<InstrumentKeyword> instruments = dataset.getDescriptiveMetadata().getInstrumentKeywords();

        if (null != instruments) {
            for (InstrumentKeyword instrument : instruments) {

                if (null != instrument.getShortName()) {
                    value.add(instrument.getShortName());
                }
            }
        }

        return value;
    }
}
