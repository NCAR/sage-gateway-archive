package sgf.gateway.integration.metrics.gridftp.transformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remove all the funky characters in front of the HOSTNAME key
 */
public class HostnameTransformer {

    private final String hostKey;
    private final Pattern tokenPattern;

    public HostnameTransformer(String hostKey) {
        this.hostKey = hostKey;
        this.tokenPattern = Pattern.compile("\\S+" + hostKey);

    }

    public String transform(String input) {

        Matcher regexMatcher = tokenPattern.matcher(input);
        String output = regexMatcher.replaceAll(hostKey);

        return output;
    }

}
