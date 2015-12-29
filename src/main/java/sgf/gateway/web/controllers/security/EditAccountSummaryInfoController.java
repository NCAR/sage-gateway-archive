package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.AccountService;

@Controller
public class EditAccountSummaryInfoController {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final Validator validator;

    public EditAccountSummaryInfoController(AccountService accountService, UserRepository userRepository, Validator validator) {

        this.accountService = accountService;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @ModelAttribute("command")
    public AccountSummaryCommand setupCommand(@RequestParam("identifier") UUID identifier) {

        User user = this.userRepository.getUser(identifier);

        if (user == null) {

            throw new ObjectNotFoundException(identifier);
        }

        AccountSummaryCommand command = new AccountSummaryCommand();
        command.setUserName(user.getUserName());
        command.setIdentifier(user.getIdentifier().toString());
        command.setFirstName(user.getFirstName());
        command.setMiddleName(user.getMiddleName());
        command.setLastName(user.getLastName());
        command.setEmail(user.getEmail());
        command.setOrganization(user.getOrganization());
        command.setStatus(user.getStatus().getName());
        command.setOpenId(user.getOpenid());
        command.setCity(user.getCity());
        command.setState(user.getState());
        command.setCountry(user.getCountry());

        return command;
    }

    @RequestMapping(value = "/ac/root/editAccountSummaryInfo.html", method = RequestMethod.GET)
    public ModelAndView getForm(@RequestParam("identifier") UUID identifier, @ModelAttribute("command") AccountSummaryCommand command, BindingResult bindingResult) throws Exception {

        return new ModelAndView("/ac/root/editAccountSummary");
    }

    @RequestMapping(value = "/ac/root/editAccountSummaryInfo.html", method = RequestMethod.POST)
    public ModelAndView postForm(@RequestParam("identifier") UUID identifier, @ModelAttribute("command") AccountSummaryCommand command, BindingResult bindingResult) throws Exception {

        ModelAndView modelAndView;

        this.validator.validate(command, bindingResult);

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView("/ac/root/editAccountSummary");

        } else {

            User user = this.userRepository.getUser(identifier);

            user.setFirstName(command.getFirstName());
            user.setMiddleName(command.getMiddleName());
            user.setLastName(command.getLastName());
            user.setEmail(command.getEmail());
            user.setOrganization(command.getOrganization());
            user.setCity(command.getCity());
            user.setState(command.getState());
            user.setCountry(command.getCountry());

            this.accountService.storeUser(user);

            modelAndView = new ModelAndView("redirect:/ac/root/lookupUser.html");
            modelAndView.addObject("identifier", user.getIdentifier().toString());
        }

        return modelAndView;
    }

}

