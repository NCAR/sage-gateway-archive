package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShortNameCharactersValidator implements ConstraintValidator<ShortNameCharacters, String> {

    // These characters are allowed in urls and do not need to be percent encoded.
    // Short name may contain the letters a-z, A-Z, numbers, and the characters: $ - _ . ! * ' ( ) ,
    // We are not allowing plus sign '+' because in some cases the plus sign gets translated into a space character
    // based on the age of the browser.
    private final String REGEX = "^[a-zA-Z0-9_\\.(),\\!\\$\\'\\*\\-]+$";

    @Override
    public void initialize(ShortNameCharacters constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean valid = false;

        if (StringUtils.hasText(value)) {

            if (value.matches(REGEX)) {

                valid = true;
            }
        }

        return valid;
    }
}