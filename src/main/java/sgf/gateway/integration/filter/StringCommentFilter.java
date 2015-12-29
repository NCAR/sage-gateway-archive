package sgf.gateway.integration.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCommentFilter {

    private final Pattern commentedLinePattern = Pattern.compile("\\s*(#|--|//).*");

    public Boolean filter(String input) {
        Boolean pass = !isCommented(input);
        return pass;
    }

    private Boolean isCommented(String line) {

        Matcher matcher = commentedLinePattern.matcher(line);
        Boolean commented = matcher.matches();

        return commented;
    }
}
