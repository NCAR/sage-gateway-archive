package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for local authentication. This controller doesn't really do anything except for displaying the form view and instantiating the command bean, since
 * all functionality is executed by the Spring Security filters.
 */
@Controller
public class LoginController {

    private final String viewName;

    public LoginController(String viewName) {

        this.viewName = viewName;
    }

    @ModelAttribute("command")
    public LoginCommand formBackingObject() {

        return new LoginCommand();
    }

    @RequestMapping(value = {"/ac/guest/secure/login.htm", "/ac/guest/secure/login.html"}, method = RequestMethod.GET)
    public String showView() {

        return this.viewName;
    }
}
