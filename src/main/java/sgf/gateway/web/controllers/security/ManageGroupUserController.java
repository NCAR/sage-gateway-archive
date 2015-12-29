package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.*;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Controller to assign group memberships to a given user (who might or might not be in the group already).
 */
@Controller
public class ManageGroupUserController {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final RuntimeUserService runtimeUserService;
    private final MailService mailService;
    private final Gateway gateway;

    private String formView;
    private String successView;
    private boolean sendUserGroupUpdateEmail;

    public ManageGroupUserController(AccountService accountService, UserRepository userRepository, RuntimeUserService runtimeUserService, MailService mailService, Gateway gateway) {

        this.accountService = accountService;
        this.userRepository = userRepository;
        this.runtimeUserService = runtimeUserService;
        this.mailService = mailService;
        this.gateway = gateway;
    }

    @ModelAttribute("command")
    public ManageGroupUserCommand setupCommand(@RequestParam("identifier") UUID uuid, @RequestParam("groupName") String groupName) {

        ManageGroupUserCommand command = new ManageGroupUserCommand();

        User user = this.userRepository.getUser(uuid);
        command.setUser(user);
        command.setIdentifier(user.getIdentifier().toString());
        command.setUserName(user.getUserName());

        Group group = SecurityControllersUtils.getGroup(groupName, accountService);
        command.setGroup(group);

        command.setRoles(this.userRepository.findRoles());

        if (user.getStatusForGroup(group) == Status.VALID) {
            // retrieve current user roles
            command.setEnroll(false);
            command.setUserRoles(Role.getRoleNames(user.getRolesForGroup(group)));

            // user is waiting for administrator approval
        } else {
            // retrieve group default roles
            command.setEnroll(true);
            command.setUserRoles(Role.getRoleNames(group.getDefaultRoles()));
        }

        return command;
    }

    @RequestMapping(value = {"/ac/admin/manageGroupUser.htm", "/ac/admin/manageGroupUser.html"}, method = RequestMethod.GET)
    public String handleGet(@RequestParam("groupName") String groupName) {

        Group group = SecurityControllersUtils.getGroup(groupName, accountService);

        User admin = runtimeUserService.getUser();
        SecurityControllersUtils.checkAdminRole(admin, group);

        return this.formView;
    }

    @RequestMapping(value = {"/ac/admin/manageGroupUser.htm", "/ac/admin/manageGroupUser.html"}, method = RequestMethod.POST)
    public ModelAndView onSubmit(ManageGroupUserCommand command, RedirectAttributes redirectAttributes) throws Exception {

        UUID uuid = UUID.valueOf(command.getIdentifier());
        User user = this.userRepository.getUser(uuid);
        Group group = accountService.getGroup(command.getGroup().getName());

        String message = command.getMessage();

        if (command.isRemove()) {

            accountService.removeUserFromGroup(user, group);

            // notify user
            List<User> toList = new ArrayList<>();
            toList.add(user);
            mailService.sendUserGroupRejectionMailMessage(toList, user, group.getName(), message);

            // enroll/update user in group
        } else {

            List<String> userRoles = command.getUserRoles();
            Set<Role> roles = Role.getRoles(userRoles);

            accountService.enrollUserInGroup(user, group, roles, Status.VALID);

            // enroll user in group's mailing lists
            accountService.subscribeUserToGroupMailingLists(user, group);

            if (this.sendUserGroupUpdateEmail) {
                // notify user
                Set<Membership> memberships = user.getMembershipsForGroup(group);
                List<String> dataAccessUrls = new ArrayList<>();
                String portalUrl = this.gateway.getBaseSecureURL().toString();
                List<User> toList = new ArrayList<>();
                toList.add(user);
                mailService.sendUserGroupUpdateMailMessage(toList, user, group.getName(), memberships, message, portalUrl, dataAccessUrls);
            }
        }

        redirectAttributes.addFlashAttribute("successMessage", "User's account updated.");

        return new ModelAndView(this.successView);
    }

    public void setFormView(String formView) {
        this.formView = formView;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setSendUserGroupUpdateEmail(boolean sendUserGroupUpdateEmail) {
        this.sendUserGroupUpdateEmail = sendUserGroupUpdateEmail;
    }
}
