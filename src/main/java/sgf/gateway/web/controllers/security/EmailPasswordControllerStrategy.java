package sgf.gateway.web.controllers.security;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.security.User;

public interface EmailPasswordControllerStrategy {

    ModelAndView showForm();

    ModelAndView handleSubmit(User user, BindingResult bindingResult);
}
