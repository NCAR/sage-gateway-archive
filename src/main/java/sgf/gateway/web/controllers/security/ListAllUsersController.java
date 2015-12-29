package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListAllUsersController {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RuntimeUserService runtimeUserService;

    public ListAllUsersController(UserRepository userRepository, GroupRepository groupRepository, RuntimeUserService runtimeUserService) {

        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/ac/root/listAllUsers", method = {RequestMethod.GET})
    protected ModelAndView handleRequest(@ModelAttribute("command") ListAllUsersCommand command, BindingResult result) {

        // security check
        User admin = runtimeUserService.getUser();
        Group rootGroup = this.groupRepository.findGroupByName(Group.GROUP_ROOT);
        SecurityControllersUtils.checkAdminRole(admin, rootGroup);

        List<User> users;

        String searchText = command.getText();

        // TODO: Testing ignoring limit - remove later.
        if (StringUtils.hasText(searchText) ) {
            users = this.userRepository.findUsersThatMatch(searchText, 1000);
        }
        else {
            users = new ArrayList<User>();
            searchText = "";  // NPE in repos if null
        }

        // Retrieving the total number of system users in the database.
        Long userTotal = this.userRepository.countUsersThatMatch(searchText);

        // Creating the data model to be used in the jsp.
        // This map contains both users and user total.

        ModelAndView modelAndView = new ModelAndView("/ac/root/listAllUsers");
        modelAndView.addObject("users", users);
        modelAndView.addObject("userTotal", userTotal);

        return modelAndView;
    }

}
