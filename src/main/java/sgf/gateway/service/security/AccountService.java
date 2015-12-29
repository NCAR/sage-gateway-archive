package sgf.gateway.service.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Role;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;

import java.util.List;
import java.util.Set;

/**
 * Service class to manage user registration and group membership.
 */
public interface AccountService {

    /**
     * Method to store a new user, or persist changes to an existing user.
     *
     * @param user the user
     * @deprecated a user only needs to be added (to the UserRepository) if they are a new User (instance). Otherwise
     * the hibernate transaction should handle the saving of a User's new/changed state.
     */
    @Deprecated
    void storeUser(User user);

    /**
     * Method to retrieve a group by name.
     *
     * @param groupName the group name
     * @return the group
     * @Deprecated Please use the GroupRepository
     */
    @Deprecated
    Group getGroup(String groupName);

    void addGroupDataToGroup(UUID groupIdentifier, GroupDataRequest groupDataRequest);

    void removeGroupDataFromGroup(UUID groupIdentifier, UUID groupDataIdentifier);

    void addExistingGroupDataToGroup(UUID groupIdentifier, ExistingGroupDataRequest existingGroupDataRequest);

    User registerUser(RegisterUserRequest request);

    User registerRemoteUser(RegisterRemoteUserRequest request);

    User registerRemoteUser(String openId);

    void changeEmailAddress(ChangeEmailRequest request);

    /**
     * Method to confirm a user registration, triggered by user clicking on email link: 1) changes default group
     * membership status from requested to valid 2) changes other group memberships to either pending (for moderated
     * groups) or valid (for automatic groups) NOTE: for security reasons, the default group memberships will NOT be
     * changed from INVALID to VALID.
     *
     * @param identifier the user's UUID identifier
     */
    @Deprecated
    void confirmRegistration(UUID identifier);

    /**
     * Method to subscribe a user to the group's mailing lists (optional and mandatory).
     *
     * @param user  the user
     * @param group the group
     */
    void subscribeUserToGroupMailingLists(User user, Group group);

    void registerUserInGroup(GroupRegistrationRequest groupRegistrationRequest);

    /**
     * Method to enroll a user in a group with a given set of roles, in a given status, and to override any pre-existing
     * user memberships for that group.
     *
     * @param user   the user
     * @param group  the group
     * @param roles  the roles
     * @param status the status
     */
    void enrollUserInGroup(User user, Group group, Set<Role> roles, Status status);

    void changeUserPassword(User user, String newPassword);

    /**
     * Method to reset the user password to a randomly generated string, for the case when the user has lost it.
     *
     * @param user the user
     * @return the string
     */
    String setRandomPassword(User user);

    /**
     * Method to remove all memberships of a given user to a given group.
     *
     * @param user  the user
     * @param group the group
     */
    void removeUserFromGroup(User user, Group group);

    /**
     * Method to create a new group, and assign a user as administrator. NOTE: the group should be wired with a Gateway
     * BEFORE invoking this method.
     *
     * @param group         the group
     * @param administrator the administrator
     */
    void createGroup(AddGroupRequest group, User administrator);

    /**
     * Method to list all groups that the user has NOT EVER subscribed to, for a specific gateway. The same
     * consideration as for the other same-named method apply.
     *
     * @param user the user
     * @return the list< group>
     */
    List<Group> listUnsubscribedGroups(User user);

    // FIXME change to be a Request (or Details) like object. See ProjectService for example.
    void disableAccount(UUID userIdentifier, boolean disable);

    boolean emailExists(String email);
}
