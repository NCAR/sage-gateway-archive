package sgf.gateway.validation.spring.required;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequiredObjectValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public RequiredObjectValidator(final String field, final String errorCode, final String defaultMessage) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return false;
    }

    public void validate(Object command, Errors errors) {

        Object object = errors.getFieldValue(this.field);

        if (object == null) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}