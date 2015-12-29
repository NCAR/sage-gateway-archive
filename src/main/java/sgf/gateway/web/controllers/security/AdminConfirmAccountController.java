package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.service.security.AccountService;

@Controller
public class AdminConfirmAccountController {

    private final AccountService accountService;

    public AdminConfirmAccountController(final AccountService accountService) {

        this.accountService = accountService;
    }

    @ModelAttribute("command")
    public ConfirmAccountCommand setupCommand() {

        //Create form backing object
        ConfirmAccountCommand command = new ConfirmAccountCommand();

        return command;
    }

    @RequestMapping(value = "/ac/root/confirmAccount", method = RequestMethod.POST)
    protected ModelAndView handle(@ModelAttribute("command") ConfirmAccountCommand command, RedirectAttributes redirectAttributes) throws Exception {

        UUID identifier = command.getIdentifier();

        accountService.confirmRegistration(identifier);

        redirectAttributes.addFlashAttribute("successMessage", "User's account has been confirmed.");

        ModelAndView modelAndView = new ModelAndView("redirect:/ac/root/lookupUser.htm");
        modelAndView.addObject("identifier", identifier.toString());

        return modelAndView;
    }
}
