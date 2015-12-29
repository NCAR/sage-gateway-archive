package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;

import java.util.Map;

public class OpenIdFieldTransformer implements FieldTransformer {

    private final String userDNKey;

    public OpenIdFieldTransformer(String userDNKey) {
        this.userDNKey = userDNKey;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String userDNToken = tokens.get(userDNKey);

        String openId = getOpenId(userDNToken);

        payload.setOpenId(openId);

        return payload;
    }

    private String getOpenId(String userDNToken) {

        // sample token: USERDN=/O=ESG-CET/OU=NCAR/OU=simpleCA-vetswebprod.ucar.edu/CN=https://www.earthsystemgrid.org/myopenid/enienhouse
        String splitter = "CN=";

        String[] all = userDNToken.split(splitter);

        String openId = all[1];

        return openId;
    }
}
