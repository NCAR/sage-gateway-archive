package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.service.security.AccountService;

public class EmailExistsValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    private final AccountService accountService;

    public EmailExistsValidator(final String field, final String errorCode, final String defaultMessage, final AccountService accountService) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String email = (String) errors.getFieldValue(this.field);

        boolean test = this.accountService.emailExists(email);

        if (!test) {

            errors.reject(errorCode, defaultMessage);
        }
    }
}
