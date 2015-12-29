package sgf.gateway.web.controllers.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate form input to prevent HTML injection attacks.
 */
public class FormValidationUtils {

    /**
     * The list of invalid characters.
     */
    private static final Pattern pattern = Pattern.compile(".*[^a-zA-Z0-9_\\-\\.\\@\\'\\:\\;\\,\\s/()].*");

    private FormValidationUtils() {
    }

    /**
     * Method to check whether a given string contains invalid characters.
     *
     * @param input
     * @return
     */
    public static boolean isInvalid(String input) {

        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

}
