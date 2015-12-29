package sgf.gateway.web.controllers.group;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

@Controller
public class RegistrationGroupController {

    private final RuntimeUserService runtimeUserService;

    public RegistrationGroupController(RuntimeUserService runtimeUserService) {

        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/ac/group/{group}/registrationField.html", method = RequestMethod.GET)
    public ModelAndView getRegistrationFields(@PathVariable(value = "group") Group group) {

        // ADD SECURITY CHECKS

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        ModelAndView modelAndView = new ModelAndView("/group/viewRegistrationFields");
        modelAndView.addObject("group", group);
        modelAndView.addObject("registrationFields", group.getRegistrationFields());

        return modelAndView;
    }
}
