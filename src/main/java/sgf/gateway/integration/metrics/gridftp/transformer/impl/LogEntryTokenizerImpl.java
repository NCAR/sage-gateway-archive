package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import sgf.gateway.integration.metrics.gridftp.transformer.LogEntryTokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntryTokenizerImpl implements LogEntryTokenizer {

    // matches an upper case key and a value of any kind of character except whitespace: KEY=Value
    private final Pattern tokenPattern = Pattern.compile("([A-Z]+)=(\\S*)");

    @Override
    public Map<String, String> tokenize(String gridFTPMetrics) {

        Map<String, String> tokenMap = new HashMap<String, String>();

        Matcher tokenMatcher = tokenPattern.matcher(gridFTPMetrics);

        while (tokenMatcher.find()) {

            String key = tokenMatcher.group(1);
            String value = tokenMatcher.group(2);

            tokenMap.put(key, value);
        }

        return tokenMap;
    }
}
