package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import java.util.Map;

public class FileURIStrategy {

    // when the type of transfer is ERET (Extended Retrieval) the schema is regrettably absent in the log entry
    // so schema is hard-coded in the bean definition

    // the database data has the gridftp host as vetsman though the machine on which gridftp now runs is actually
    // vetsdataprod so host is hard-coded in the bean definition to match the database allowing older and newer
    // logs, which show the correct hostname, to be processed

    private final String schema;
    private final String host;
    private final String portKey;
    private final String stemKey;

    public FileURIStrategy(String schema, String host, String portKey, String stemKey) {
        this.schema = schema;
        this.host = host;
        this.portKey = portKey;
        this.stemKey = stemKey;
    }

    public String getFileURI(Map<String, String> tokens) {

        String portToken = tokens.get(portKey);
        String stemToken = tokens.get(stemKey);

        // all gridftp full access urls are recorded in the database as :port//file/path but the file stem varies
        // between one and two preceeding slashes in the gridftp usage logs so I guarantee one slash here...
        stemToken = stemToken.replaceFirst("^/*", "/");

        String fileURI = schema + "://" + host + ":" + portToken + "/" + stemToken;

        return fileURI;
    }
}
