package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;
import sgf.gateway.web.controllers.project.command.ProjectCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleCharactersValidator implements ConstraintValidator<TitleCharacters, String> {

    // Title may contain alphanumeric, underbar, dot, dash, space, question mark, parentheses, # Sign?
    public static final String REGEX = "^[a-zA-Z0-9_\\.() ?\\-]+$";

    @Override
    public void initialize(TitleCharacters constraintAnnotation) {

    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {

        boolean valid = false;

        if (StringUtils.hasText(title)) {

            if (title.matches(REGEX)) {

                valid = true;
            }
        }

        return valid;
    }
}
