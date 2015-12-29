package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MaximumLengthValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;
    private final int maximumLength;

    public MaximumLengthValidator(final String field, final String errorCode, final String defaultMessage, final int maximumLength) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.maximumLength = maximumLength;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String value = (String) errors.getFieldValue(this.field);

        if (this.maximumLength < value.length()) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
