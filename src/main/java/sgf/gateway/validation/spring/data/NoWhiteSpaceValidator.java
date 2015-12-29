package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoWhiteSpaceValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public NoWhiteSpaceValidator(final String field, final String errorCode, final String defaultMessage) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> arg0) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String value = (String) errors.getFieldValue(this.field);

        if (containsSpace(value)) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }

    private boolean containsSpace(String string) {

        Pattern pattern = Pattern.compile("\\s");

        Matcher matcher = pattern.matcher(string);

        boolean matches = matcher.find();

        return matches;
    }

}
