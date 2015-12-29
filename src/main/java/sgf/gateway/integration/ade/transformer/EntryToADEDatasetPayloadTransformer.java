package sgf.gateway.integration.ade.transformer;

import sgf.gateway.integration.ade.ADEDatasetPayload;
import sgf.gateway.integration.ade.opensearch.Entry;

public class EntryToADEDatasetPayloadTransformer {

    private final String dataCenter;

    public EntryToADEDatasetPayloadTransformer(String dataCenter) {
        super();
        this.dataCenter = dataCenter;
    }

    public ADEDatasetPayload transform(Entry entry) {
        ADEDatasetPayload payload = new ADEDatasetPayload(this.dataCenter, entry);
        return payload;
    }
}
