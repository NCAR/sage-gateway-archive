package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.AccountService;

@Controller
public class DisableUserAccountController {

    private final AccountService accountService;
    private final UserRepository userRepository;

    public DisableUserAccountController(AccountService accountService, UserRepository userRepository) {

        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    @ModelAttribute("command")
    public DisableUserAccountCommand setupCommand(@RequestParam("identifier") UUID identifier) {

        User user = this.userRepository.getUser(identifier);

        if (user == null) {

            throw new ObjectNotFoundException(identifier);
        }

        DisableUserAccountCommand command = new DisableUserAccountCommand();

        return command;
    }

    @RequestMapping(value = "/ac/root/disableUserAccount.html", method = RequestMethod.POST)
    public ModelAndView handle(@RequestParam("identifier") UUID identifier, @ModelAttribute("command") DisableUserAccountCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        accountService.disableAccount(identifier, command.isDisable());

        ModelAndView modelAndView = new ModelAndView("redirect:/ac/root/lookupUser.html");
        modelAndView.addObject("identifier", identifier.toString());

        return modelAndView;
    }
}
