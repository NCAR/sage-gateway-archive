package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.List;
import java.util.Map;

@Controller
public class ListUserGroupsController {

    private final UserRepository userRepository;
    private final RuntimeUserService runtimeUserService;

    private String view;

    public ListUserGroupsController(UserRepository userRepository, RuntimeUserService runtimeUserService) {

        this.userRepository = userRepository;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/ac/user/listUserGroups.html", method = RequestMethod.GET)
    public ModelAndView handleRequestInternal() throws Exception {

        User user = runtimeUserService.getUser();
        user = this.userRepository.getUser(user.getIdentifier());

        Map<Group, List<Membership>> userGroups = user.getGroupToMembershipsMap();

        ModelAndView modelAndView = new ModelAndView(getView());
        modelAndView.addObject("userGroups", userGroups);

        return modelAndView;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
