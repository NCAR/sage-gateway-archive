package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.spring.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ListGroupUsersController {

    private static final String COMMAND = "command";

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;
    private String view;

    public ListGroupUsersController(UserRepository userRepository, AccountService accountService, RuntimeUserService runtimeUserService) {

        this.userRepository = userRepository;
        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/ac/admin/listGroupUsers")
    protected ModelAndView handle(@ModelAttribute(COMMAND) ListGroupUsersCommand data, BindingResult result) throws Exception {

        Long userTotal = null;

        // request input data
        String groupName = data.getGroupName();
        String text = data.getText();
        int limit = data.getLimit();
        int status = data.getStatus();

        // security check
        Group group = SecurityControllersUtils.getGroup(groupName, accountService);
        User admin = runtimeUserService.getUser();
        SecurityControllersUtils.checkAdminRole(admin, group);

        if (StringUtils.hasText(text) && FormValidationUtils.isInvalid(text)) {

            result.reject(Constants.ERROR_INVALID, "Matching text contains invalid characters.");

        } else {

            Map<User, Status> userMap = new LinkedHashMap<>();

            // add new user -> list all users with their current group status
            if (status == Status.UNKNOWN.getId()) {

                List<User> users = this.userRepository.findUsersThatMatch(text, limit);
                userTotal = 0L;
                for (User user : users) {
                    Status userStatus = user.getStatusForGroup(group);
                    if (userStatus == Status.UNKNOWN) {
                        userMap.put(user, userStatus);
                        userTotal++;
                    }
                }

            } else {

                // manage pending/valid users -> list all group users in that status
                Status userStatus = Status.getById(status);
                List<User> users = this.userRepository.findGroupUsersInStatusThatMatch(groupName, text, userStatus, limit);
                for (User user : users) {
                    userMap.put(user, userStatus);
                }

                // Retrieving the total number of system users that match.
                userTotal = this.userRepository.countGroupUsersInStatusThatMatch(groupName, text, userStatus);

            }
            data.setUserMap(userMap);
        }

        // Creating the data model to be used in the jsp.
        // This map contains a map of the user and user statues, and the user
        ModelAndView modelAndView = new ModelAndView(getView());
        modelAndView.addObject("command", data);
        modelAndView.addObject("status", status);
        modelAndView.addObject("groupName", groupName);
        modelAndView.addObject("userTotal", userTotal);

        return modelAndView;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
