package sgf.gateway.integration.metrics.gridftp.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntryFilter {

    private final Pattern ftpRetrievalPattern = Pattern.compile(".*TYPE=(RETR|ERET).*");

    public Boolean filter(String input) {
        Boolean pass = isFTPRetrievalEntry(input);
        return pass;
    }

    private Boolean isFTPRetrievalEntry(String input) {

        Matcher matcher = ftpRetrievalPattern.matcher(input);
        Boolean matches = matcher.matches();

        return matches;
    }
}
