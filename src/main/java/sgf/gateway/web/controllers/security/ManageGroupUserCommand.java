package sgf.gateway.web.controllers.security;

import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Role;
import sgf.gateway.model.security.User;

import java.util.List;


public class ManageGroupUserCommand {

    private User user = new User(); // full user object

    private String identifier; // user identifier, which cannot be set on the user object

    private String userName;

    private String gatewayName; // gateway name used to lookup user in combination with username

    private Group group = new Group(); // full group object

    private List<String> userRoles; // roles to be assigned to the user (prefilled with existing roles, or default group roles)

    private List<Role> roles; // all possible roles in the system

    private boolean remove = false; // true to remove the current user from the group

    private boolean enroll = false; // true to enroll new user, false to change memberships of existing user

    private String message; // message to be included in email notification to user

    // NOTE: bind HTTP parameter 'groupName' to Group.name property
    public String getGroupName() {
        return group.getName();
    }

    public void setGroupName(String groupName) {
        this.group.setName(groupName);
    }

    // NOTE: bind HTTP parameter 'userName' to User.userName property
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String id) {
        this.identifier = id;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isEnroll() {
        return enroll;
    }

    public void setEnroll(boolean enroll) {
        this.enroll = enroll;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
