package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.security.command.ChangeEmailCommand;

import javax.validation.Valid;

@Controller
public class ChangeEmailController {

    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;
    private final String formView;
    private final String successView;

    public ChangeEmailController(AccountService accountService, RuntimeUserService runtimeUserService, String formView, String successView) {

        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
        this.formView = formView;
        this.successView = successView;
    }

    @ModelAttribute("command")
    public Object setupCommand() {

        User user = runtimeUserService.getUser();

        ChangeEmailCommand command = new ChangeEmailCommand(user);

        return command;
    }

    @RequestMapping(value = "/ac/user/changeEmail", method = RequestMethod.GET)
    public ModelAndView setupForm() throws Exception {

        // Create ModelAndView

        ModelAndView modelAndView = new ModelAndView(this.formView);

        return modelAndView;
    }

    @RequestMapping(value = "/ac/user/changeEmail", method = RequestMethod.POST)
    protected ModelAndView onSubmit(@ModelAttribute("command") @Valid ChangeEmailCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        String resultView;

        if (bindingResult.hasErrors()) {

            resultView = formView;

        } else {

            accountService.changeEmailAddress(command);

            redirectAttributes.addFlashAttribute("successMessage", "Your email address has been updated.");

            resultView = this.successView;
        }

        ModelAndView modelAndView = new ModelAndView(resultView);

        return modelAndView;
    }
}
