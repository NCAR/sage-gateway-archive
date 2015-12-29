package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.service.security.AccountService;

@Controller
public class ConfirmAccountController {

    private final AccountService accountService;

    private String loginForm;

    public ConfirmAccountController(final AccountService accountService) {

        this.accountService = accountService;
    }

    @ModelAttribute("command")
    public ConfirmAccountCommand setupCommand() {

        //Create form backing object
        ConfirmAccountCommand command = new ConfirmAccountCommand();

        return command;
    }

    @RequestMapping(value = "/ac/guest/secure/confirmAccount")
    protected ModelAndView handle(@ModelAttribute("command") ConfirmAccountCommand command) throws Exception {

        UUID identifier = command.getIdentifier();

        accountService.confirmRegistration(identifier);

        ModelAndView modelAndView = new ModelAndView("/ac/guest/secure/registration/confirmAccount");
        modelAndView.addObject("loginForm", this.loginForm);

        return modelAndView;
    }

    public void setLoginForm(String loginForm) {

        this.loginForm = loginForm;
    }
}
