package sgf.gateway.validation.spring.required;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RequiredFieldValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public RequiredFieldValidator(String field, String errorCode, String defaultMessage) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    public void validate(Object command, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, this.field, this.errorCode, this.defaultMessage);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }
}
