package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;

import java.util.Map;

public class UserAgentFieldTransformer implements FieldTransformer {

    private final String gridFtpUserAgent;

    public UserAgentFieldTransformer(String gridFtpUserAgent) {
        this.gridFtpUserAgent = gridFtpUserAgent;
    }

    @Override
    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        payload.setUserAgent(gridFtpUserAgent);

        return payload;
    }
}
