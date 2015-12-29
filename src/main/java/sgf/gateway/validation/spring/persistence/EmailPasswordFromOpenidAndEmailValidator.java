package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;

public class EmailPasswordFromOpenidAndEmailValidator implements Validator {

    private final String openidField;
    private final String emailField;
    private final String errorCode;
    private final String defaultMessage;

    private final UserRepository userRepository;

    public EmailPasswordFromOpenidAndEmailValidator(final String openidField, final String emailField, final String errorCode, final String defaultMessage, UserRepository userRepository) {

        this.openidField = openidField;
        this.emailField = emailField;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.userRepository = userRepository;
    }


    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String openid = (String) errors.getFieldValue(this.openidField);

        User user = this.userRepository.findUserByOpenid(openid);

        // FIXME change this into separate validators.

        if (user != null) {

            if (user.getUserName() != null && user.getPassword() != null) {

                String email = (String) errors.getFieldValue(this.emailField);

                if (!user.getEmail().equalsIgnoreCase(email)) {

                    errors.reject(this.errorCode, this.defaultMessage);
                }

            } else {

                errors.reject(this.errorCode, this.defaultMessage);
            }

        } else {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }

}
