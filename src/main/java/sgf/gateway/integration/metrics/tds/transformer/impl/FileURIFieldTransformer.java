package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class FileURIFieldTransformer implements FieldTransformer {

    private final String schemeKey;
    private final String hostKey;
    private final String stemKey;

    public FileURIFieldTransformer(String schemeKey, String hostKey, String stemKey) {
        this.schemeKey = schemeKey;
        this.hostKey = hostKey;
        this.stemKey = stemKey;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String schemeToken = tokens.get(schemeKey);
        String hostToken = tokens.get(hostKey);
        String fileURIStemToken = tokens.get(stemKey);

        String fileURI = schemeToken + "://" + hostToken + fileURIStemToken;

        payload.setFileURI(fileURI);

        return payload;
    }
}
