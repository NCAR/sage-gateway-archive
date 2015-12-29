package sgf.gateway.utils.html;

/**
 * The Class WordWrapFormatter.
 */
public class WordWrapFormatter {

    /**
     * Format.
     *
     * @param string the string
     * @return the string
     */
    public String format(String string) {

        // Changed the character from <wbr/> to &#8203 due to the fact that IE8 started to ignore the <wbr/> character.
        // Please see the following jira ticket:  GTWY-2602

        // The # sign must be first because if the # is not first all the other # signs of all the previous replacements will also get changed.
        String formatString = string.replaceAll("#", "#&#8203;");
        formatString = formatString.replaceAll("\\.", ".&#8203;");
        formatString = formatString.replaceAll("-", "-&#8203;");
        formatString = formatString.replaceAll("%", "%&#8203;");
        formatString = formatString.replaceAll("_", "_&#8203;");
        // Ignore spaces since they will automajically be word breaks because of HTML.
        // formatString = formatString.replaceAll(" ", " &#8203");

        return formatString;
    }
}
