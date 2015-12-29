package sgf.gateway.web.controllers.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.validation.spring.DefaultValidator;
import sgf.gateway.web.controllers.security.command.RegistrationCommand;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

    private final AccountService accountService;

    private final DefaultValidator registrationValidator;

    private String formView;

    public RegistrationController(AccountService accountService, DefaultValidator registrationValidator, String formView) {

        this.accountService = accountService;
        this.registrationValidator = registrationValidator;
        this.formView = formView;
    }

    @ModelAttribute("registrationCommand")
    public RegistrationCommand setupCommand() {

        return new RegistrationCommand();
    }

    @RequestMapping(value = "/ac/guest/secure/registration", method = RequestMethod.GET)
    protected String getForm() {

        return this.formView;
    }

    @RequestMapping(value = "/ac/guest/secure/registration", method = RequestMethod.POST)
    protected String onSubmit(@ModelAttribute("registrationCommand") RegistrationCommand registrationCommand, BindingResult bindingResult, HttpServletRequest request) {

        registrationValidator.validate(registrationCommand, bindingResult);

        String view;

        if (bindingResult.hasErrors()) {

            view = this.formView;

        } else {

            // We are using Organization as a "honeypot"
            if (StringUtils.isBlank(registrationCommand.getOrganization())) {

                User user = this.accountService.registerUser(registrationCommand);

                request.getSession().setAttribute("REGISTERED_USER", user);
            }

            view = "redirect:/ac/guest/secure/accountCreated.html";
        }

        return view;
    }
}
