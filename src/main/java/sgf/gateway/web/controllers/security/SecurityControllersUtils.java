package sgf.gateway.web.controllers.security;

import org.springframework.util.StringUtils;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;

/**
 * Convenience class to hold functionality common to many security controllers.
 */
public class SecurityControllersUtils {

    /**
     * Method to load a group from the database, or throw exceptions for invalid parameters.
     *
     * @param groupName
     * @param accountService
     * @return
     * @throws Exception
     */
    public static Group getGroup(String groupName, AccountService accountService) throws IllegalArgumentException {

        if (!StringUtils.hasText(groupName)) {
            throw new IllegalArgumentException("Invalid group: " + groupName);
        }
        Group group = accountService.getGroup(groupName);
        if (group == null) {
            throw new IllegalArgumentException("Unknown group: " + groupName);
        }
        return group;
    }

    /**
     * Method to check that a given user has administrator role for the given group, and throw an exception if not
     *
     * @param group
     */
    public static void checkAdminRole(User user, Group group) throws IllegalArgumentException {
        if (!user.isAdmin(group)) {
            throw new IllegalArgumentException("You are not an administrator for group " + group.getName());
        }
    }
}
