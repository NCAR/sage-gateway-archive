package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.RuntimeUserService;

@Controller
public class LookupUserController {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RuntimeUserService runtimeUserService;

    public LookupUserController(UserRepository userRepository, GroupRepository groupRepository, RuntimeUserService runtimeUserService) {

        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/ac/root/lookupUser")
    public ModelAndView handleRequestInternal(@RequestParam("identifier") UUID identifier) throws Exception {

        // security check
        User admin = runtimeUserService.getUser();
        Group rootGroup = this.groupRepository.findGroupByName(Group.GROUP_ROOT);
        SecurityControllersUtils.checkAdminRole(admin, rootGroup);

        User user = this.userRepository.getUser(identifier);

        if (user == null) {

            throw new ObjectNotFoundException(identifier);
        }

        return new ModelAndView("/ac/root/lookupUser", "user", user);
    }
}
