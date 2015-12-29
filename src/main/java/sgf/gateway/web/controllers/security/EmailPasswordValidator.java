package sgf.gateway.web.controllers.security;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.utils.spring.Constants;

public class EmailPasswordValidator implements Validator {

    private final UserRepository userRepository;

    public EmailPasswordValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object command, Errors errors) {

        User user = (User) command;
        String userName = user.getUserName();
        String email = user.getEmail();

        // check for correct data
        User user2 = this.userRepository.findUserByUserName(userName);

        if ((userName == null) || (email == null) || (user2 == null) || !user2.getEmail().equals(email)) {

            errors.reject(Constants.ERROR_INVALID, new Object[]{"User name", "Email"}, "Invalid Username/Email Address combination");
        }
    }
}
