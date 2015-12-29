package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.validation.spring.DefaultValidator;

import java.util.List;

/**
 * Controller for retrieval of lost OpenID.
 */
@Controller
public class EmailOpenidController {

    public static final String FORM_VIEW = "/ac/guest/secure/openid/consumer/emailOpenidRequest";
    public static final String SUCCESS_VIEW = "/ac/guest/secure/openid/consumer/emailOpenidResponse";

    private final UserRepository userRepository;
    private final MailService mailService;

    DefaultValidator emailOpenidValidator;

    public EmailOpenidController(UserRepository userRepository, MailService mailService, DefaultValidator emailOpenidValidator) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.emailOpenidValidator = emailOpenidValidator;
    }

    @RequestMapping(value = "/ac/guest/secure/emailOpenid.htm", method = RequestMethod.GET)
    public ModelAndView setupForm() throws Exception {

        ModelAndView modelAndView = new ModelAndView(FORM_VIEW);

        return modelAndView;
    }

    @ModelAttribute("command")
    public User setupCommand() {

        User command = new User();

        return command;
    }

    @RequestMapping(value = "/ac/guest/secure/emailOpenid.htm", method = RequestMethod.POST)
    protected ModelAndView onSubmit(@ModelAttribute("command") User command, BindingResult bindingResult) throws Exception {

        User user = command;
        ModelAndView modelAndView = new ModelAndView();

        emailOpenidValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName(FORM_VIEW);

        } else {

            // User could have multiple accounts with different openids.
            List<User> userAccounts = userRepository.findUsersByEmail(user.getEmail());

            mailService.sendUserOpenidReminderMailMessage(user, userAccounts);

            modelAndView.setViewName(SUCCESS_VIEW);
        }

        return modelAndView;
    }
}
