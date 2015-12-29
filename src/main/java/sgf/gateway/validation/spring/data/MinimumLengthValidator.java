package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Used to validate the length of a specific input field.
 * <p/>
 * Warning, this class might have difficulties with input fields that are greater in length then the max int size.
 *
 * @author nhook
 */

public class MinimumLengthValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;
    private final int minimumLength;

    public MinimumLengthValidator(String field, String errorCode, String defaultMessage, int minimumLength) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.minimumLength = minimumLength;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String value = (String) errors.getFieldValue(this.field);

        if (value.length() < this.minimumLength) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
