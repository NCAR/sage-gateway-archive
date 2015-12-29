package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.dao.security.UserRepository;

public class LocalEmailUniqueValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    private final UserRepository userRepository;

    public LocalEmailUniqueValidator(String field, String errorCode, String defaultMessage, UserRepository userRepository) {

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

        String email = (String) errors.getFieldValue(this.field);

        boolean test = this.userRepository.isLocalEmailUnique(email);

        if (!test) {

            errors.reject(errorCode, defaultMessage);
        }
    }
}
