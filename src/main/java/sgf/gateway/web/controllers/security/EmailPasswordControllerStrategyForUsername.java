package sgf.gateway.web.controllers.security;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.security.AccountService;

import java.util.ArrayList;
import java.util.List;

public class EmailPasswordControllerStrategyForUsername implements EmailPasswordControllerStrategy {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final Validator validator;

    public EmailPasswordControllerStrategyForUsername(AccountService accountService, UserRepository userRepository, MailService mailService, Validator validator) {

        this.accountService = accountService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.validator = validator;
    }

    @Override
    public ModelAndView showForm() {
        return new ModelAndView("/ac/guest/secure/emailPasswordRequest");
    }

    @Override
    public ModelAndView handleSubmit(User user, BindingResult bindingResult) {

        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            // No redirect
            return new ModelAndView("/ac/guest/secure/emailPasswordRequest");

        } else {

            try {

                user = this.userRepository.findUserByUserName(user.getUserName());

                String newPassword = accountService.setRandomPassword(user);

                // send email ONLY after password reset has successfully completed
                List<User> toList = new ArrayList<User>();
                toList.add(user);
                mailService.sendUserPasswordReminderMailMessage(toList, newPassword);

            } catch (Exception e) {

                throw new RuntimeException("Could not change password for user:" + user.getUserName(), e);
            }

            ModelAndView modelAndView = new ModelAndView("redirect:/ac/guest/secure/emailPasswordRequestSubmitted.htm");

            return modelAndView;
        }
    }
}
