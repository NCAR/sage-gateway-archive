package sgf.gateway.integration.metrics.tds.transformer;

import java.util.Map;

public interface LogEntryTokenizer {
    Map<String, String> tokenize(String tdsMetrics);
}
