package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.security.User;

@Controller
public class EmailPasswordController {

    private EmailPasswordControllerStrategy strategy;

    public EmailPasswordController(EmailPasswordControllerStrategy strategy) {

        this.strategy = strategy;
    }

    @ModelAttribute(value = "command")
    public User setupCommand() {

        User user = new User();

        return user;
    }

    @RequestMapping(value = {"/ac/guest/secure/emailPassword.html", "/ac/guest/secure/emailPassword.htm"}, method = RequestMethod.GET)
    public ModelAndView showForm() {

        return this.strategy.showForm();
    }

    @RequestMapping(value = {"/ac/guest/secure/emailPassword.html", "/ac/guest/secure/emailPassword.htm"}, method = RequestMethod.POST)
    protected ModelAndView onSubmit(@ModelAttribute("command") User user, BindingResult bindingResult) {

        ModelAndView modelAndView = this.strategy.handleSubmit(user, bindingResult);

        return modelAndView;
    }
}
