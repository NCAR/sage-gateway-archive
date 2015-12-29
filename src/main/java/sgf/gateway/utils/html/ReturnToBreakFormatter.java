package sgf.gateway.utils.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class ReturnToBreakFormatter.
 */
public class ReturnToBreakFormatter {

    /**
     * The pattern.
     */
    private final Pattern pattern;

    /**
     * Instantiates a new return to break formatter.
     */
    public ReturnToBreakFormatter() {

        this.pattern = Pattern.compile("\\r\\n|\\r|\\n", Pattern.MULTILINE);
    }

    /**
     * Format.
     *
     * @param string the string
     * @return the string
     */
    public String format(String string) {

        Matcher matcher = this.pattern.matcher(string);

        // The extra \r\n is for readability of the html that is produced from this formatting. It can be removed if we really don't like having it here.
        String formatString = matcher.replaceAll("<br>\r\n");

        return formatString;
    }
}
