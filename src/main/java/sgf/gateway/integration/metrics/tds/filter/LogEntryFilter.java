package sgf.gateway.integration.metrics.tds.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntryFilter {

    private final Pattern fileDownloadEntryPattern = Pattern.compile(".*/thredds/fileServer/.*");

    public Boolean filter(String input) {
        Boolean pass = isFileDownloadEntry(input);
        return pass;
    }

    private Boolean isFileDownloadEntry(String input) {

        Matcher matcher = fileDownloadEntryPattern.matcher(input);
        Boolean matches = matcher.matches();

        return matches;
    }
}
