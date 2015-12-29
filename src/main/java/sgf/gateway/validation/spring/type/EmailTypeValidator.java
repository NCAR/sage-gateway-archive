package sgf.gateway.validation.spring.type;

import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EmailTypeValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public EmailTypeValidator(String field, String errorCode, String defaultMessage) {

        this.field = field;
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

        String email = (String) errors.getFieldValue(this.field);

        boolean test = EmailValidator.getInstance().isValid(email);

        if (!test) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
