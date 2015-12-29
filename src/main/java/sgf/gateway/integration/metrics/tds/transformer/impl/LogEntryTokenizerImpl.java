package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.tds.transformer.LogEntryTokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntryTokenizerImpl implements LogEntryTokenizer {

    // matches on unquoted tokens without spaces OR quoted tokens that may have spaces
    private final Pattern tokenPattern = Pattern.compile("('([^']*)')|([^'\\s]+)");
    private final List<String> keys;

    public LogEntryTokenizerImpl(List<String> keys) {
        super();
        this.keys = keys;
    }

    @Override
    public Map<String, String> tokenize(String tdsMetrics) {

        Map<String, String> tokenMap = new HashMap<String, String>();

        List<String> tokens = rXSplit(tdsMetrics);

        for (Integer index = 0; index < keys.size(); index++) {

            String key = keys.get(index);
            String value = tokens.get(index);

            tokenMap.put(key, value);
        }

        return tokenMap;
    }

    private List<String> rXSplit(String tdsMetrics) {

        List<String> tokens = new ArrayList<String>();
        Matcher tokenMatcher = tokenPattern.matcher(tdsMetrics);

        while (tokenMatcher.find()) {

            String token;

            if (tokenMatcher.group(2) != null) {
                token = tokenMatcher.group(2); // quoted token
            } else {
                token = tokenMatcher.group(3); // unquoted token
            }

            tokens.add(token);
        }

        return tokens;
    }
}
