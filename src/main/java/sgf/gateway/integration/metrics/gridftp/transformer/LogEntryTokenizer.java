package sgf.gateway.integration.metrics.gridftp.transformer;

import java.util.Map;

public interface LogEntryTokenizer {
    Map<String, String> tokenize(String gridFTPMetrics);
}