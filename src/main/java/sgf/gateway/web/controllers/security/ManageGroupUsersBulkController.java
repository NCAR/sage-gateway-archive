package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.GatewayImpl;
import sgf.gateway.model.security.*;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.*;

@Controller
public class ManageGroupUsersBulkController {

    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final GatewayImpl groupGateway;

    private boolean sendUserGroupUpdateEmail;

    private String view;

    public ManageGroupUsersBulkController(AccountService accountService, RuntimeUserService runtimeUserService, MailService mailService,
                                          UserRepository userRepository, GatewayImpl groupGateway) {

        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
        this.userRepository = userRepository;
        this.groupGateway = groupGateway;
        this.mailService = mailService;
    }

    @RequestMapping(value = "/ac/admin/manageGroupUsersBulk")
    protected ModelAndView handle(@ModelAttribute("command") ManageGroupUsersBulkCommand command, BindingResult result) throws Exception {

        String groupName = command.getGroupName();
        String uuids = command.getUuids();

        // load group
        Group group = SecurityControllersUtils.getGroup(groupName, accountService);

        // security check: user must be group administrator
        User admin = runtimeUserService.getUser();
        SecurityControllersUtils.checkAdminRole(admin, group);

        // enroll all users, one at a time
        Map<User, Status> users = new HashMap<>();
        Set<Role> roles = group.getDefaultRoles();
        for (String uuid : uuids.split("\\|")) {

            User user = this.userRepository.getUser(new UUID(uuid));

            // assign user to group
            accountService.enrollUserInGroup(user, group, roles, Status.VALID);

            // enroll user in group's mailing lists
            accountService.subscribeUserToGroupMailingLists(user, group);

            // notify user
            if (this.sendUserGroupUpdateEmail) {
                Set<Membership> memberships = user.getMembershipsForGroup(group);
                List<String> dataAccessUrls = new ArrayList<>();
                //What should be in dataAccessUrls?
                String portalUrl = this.groupGateway.getBaseSecureURL().toString();

                List<User> toList = new ArrayList<>();
                toList.add(user);
                mailService.sendUserGroupUpdateMailMessage(toList, user, group.getName(), memberships, "", portalUrl, dataAccessUrls);
            }

            Status status = user.getStatusForGroup(group);
            users.put(user, status);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("group", group);
        model.put("users", users);
        return new ModelAndView(getView(), "model", model);

    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setSendUserGroupUpdateEmail(boolean sendUserGroupUpdateEmail) {
        this.sendUserGroupUpdateEmail = sendUserGroupUpdateEmail;
    }
}
