package sgf.gateway.web.controllers.security;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import sgf.gateway.model.security.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationSubmittedController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = (User) request.getSession().getAttribute("REGISTERED_USER");

        ModelAndView modelAndView = new ModelAndView("/ac/guest/secure/registrationSubmitted");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

}
