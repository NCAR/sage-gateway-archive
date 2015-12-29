package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;

public class FileNameCharacterChecker {

    // Filename may contain alphanumeric, underbar, dot, dash, or space
    public static final String REGEX = "^[a-zA-Z0-9_\\. \\-]+$";

    public boolean isFileNameValid(String filename) {

        boolean valid = false;

        if (StringUtils.hasText(filename)) {

            if (filename.matches(REGEX)) {

                valid = true;
            }
        }

        return valid;
    }
}