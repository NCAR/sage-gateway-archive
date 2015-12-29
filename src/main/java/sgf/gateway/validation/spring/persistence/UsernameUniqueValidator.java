package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.dao.security.UserRepository;

public class UsernameUniqueValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    private final UserRepository userRepository;

    public UsernameUniqueValidator(String field, String errorCode, String defaultMessage, UserRepository userRepository) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String username = (String) errors.getFieldValue(this.field);

        boolean test = this.userRepository.isUsernameUniqueIgnoreCase(username);

        if (!test) {

            errors.reject(errorCode, defaultMessage);
        }
    }
}
