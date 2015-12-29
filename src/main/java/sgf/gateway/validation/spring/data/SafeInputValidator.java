package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.web.controllers.security.FormValidationUtils;

public class SafeInputValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public SafeInputValidator(final String field, final String errorCode, final String defaultMessage) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String value = (String) errors.getFieldValue(this.field);

        if (FormValidationUtils.isInvalid(value)) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
