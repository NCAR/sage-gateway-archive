package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConfirmationFieldValidator implements Validator {

    private final String field;
    private final String confirmationField;
    private final String errorCode;
    private final String defaultMessage;

    public ConfirmationFieldValidator(String field, String confirmationField, String errorCode, String defaultMessage) {

        this.field = field;
        this.confirmationField = confirmationField;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String value = (String) errors.getFieldValue(this.field);
        String confirmationValue = (String) errors.getFieldValue(this.confirmationField);

        boolean test = value.equals(confirmationValue);

        if (!test) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
