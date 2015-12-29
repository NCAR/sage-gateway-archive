package sgf.gateway.dao.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.security.Role;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;

import java.io.Serializable;
import java.util.List;

public interface UserRepository extends Repository<User, Serializable> {

    /**
     * Method to store or update a user.
     *
     * @param user the user
     * @deprecated please use addUser(User user) instead.
     */
    @Deprecated
    void storeUser(User user);

    User getUser(UUID identifier);

    User findUserByUserName(String username);

    boolean isUsernameUniqueIgnoreCase(String username);

    // Multiple "users" can have the same email due to how this is modeled.
    List<User> findUsersByEmail(String email);

    boolean isLocalEmailUnique(String email);

    /**
     * Method to find a user by (unique) openid.
     *
     * @param openid the openid
     * @return the user
     */
    User findUserByOpenid(String openid);

    /**
     * Method to return all users in the system whose first name, last name, user name or email match a given expression, ignoring case.
     *
     * @return
     */
    List<User> findUsersThatMatch(String match, int limit);

    /**
     * Method to return a count of all users in the system whose first name, last name, user name or email match a given expression, ingoring case.
     * There is not result set limit, all users found will be returned.
     *
     * @param match the given expression in which to filter system users by.
     * @return the list of users that matched the given expression.
     */
    Long countUsersThatMatch(String match);

    /**
     * Method to find all users that belong to a named group in a given status and match a given expression.
     *
     * @param groupName
     * @param match
     * @param status
     * @param limit
     * @return
     */
    List<User> findGroupUsersInStatusThatMatch(String groupName, String match, Status status, int limit);

    /**
     * Method to get the count of all users that belong to named group in a given status and match a given expression.
     * There is not result set limit, all users found will be returned.
     *
     * @param groupName desired group by name
     * @param match     desired expression in which to filter the users by
     * @param status    desired group user status
     * @return the calculated total
     */
    Long countGroupUsersInStatusThatMatch(String groupName, String match, Status status);

    /**
     * Method to return all possible user roles in the system.
     *
     * @return the list< role>
     */
    List<Role> findRoles();
}
