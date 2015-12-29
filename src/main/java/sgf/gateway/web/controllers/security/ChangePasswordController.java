package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.validation.Valid;

@Controller
public class ChangePasswordController {

    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;
    private final String formView;
    private final String successView;

    public ChangePasswordController(AccountService accountService, RuntimeUserService runtimeUserService, String formView, String successView) {

        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
        this.formView = formView;
        this.successView = successView;
    }

    @ModelAttribute("command")
    public ChangePasswordCommand setupCommand() {

        ChangePasswordCommand command = new ChangePasswordCommand();

        return command;
    }

    @ModelAttribute("user")
    protected User referenceData() {

        User user = runtimeUserService.getUser();

        return user;
    }

    @RequestMapping(value = "/ac/user/secure/changePassword.html", method = RequestMethod.GET)
    public ModelAndView getForm() {

        ModelAndView modelAndView = new ModelAndView(formView);

        return modelAndView;
    }

    @RequestMapping(value = "/ac/user/secure/changePassword.html", method = RequestMethod.POST)
    protected ModelAndView onSubmit(@ModelAttribute("command") @Valid ChangePasswordCommand changePasswordcommand, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName(formView);

        } else {

            String newPassword = changePasswordcommand.getPassword();

            User user = runtimeUserService.getUser();

            accountService.changeUserPassword(user, newPassword);

            redirectAttributes.addFlashAttribute("successMessage", "Password changed.");

            modelAndView.setViewName(successView);
        }

        return modelAndView;
    }

}
