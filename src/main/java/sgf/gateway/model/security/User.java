package sgf.gateway.model.security;

import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.xrds.XrdsServiceEndpoint;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.discovery.yadis.YadisResult;
import org.openid4java.util.HttpCache;
import org.openid4java.util.HttpFetcherFactory;
import org.safehaus.uuid.UUID;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Object representing a (human) user of the system.
 */
public class User extends Principal implements Serializable {

    public static final String LOGIN_DEFAULT = "DEFAULT";

    private static final long serialVersionUID = 1L;

    /**
     * Not-Nullable persistent field.
     */
    private String firstName;

    /**
     * Nullable persistent field.
     */
    private String middleName;

    /**
     * Not-Nullable persistent field.
     */
    private String lastName;

    /**
     * Not-Nullable persistent field.
     */
    private String email;

    /**
     * Nullable persistent field.
     */
    private String userName;

    /**
     * Nullable persistent field.
     */
    private String password;

    /**
     * Not-Nullable persistent field. Currently fixed to LOGIN_DEFAULT.
     */
    private String loginType = User.LOGIN_DEFAULT;

    /**
     * Nullable persistent field.
     */
    private String organization;

    /**
     * Nullable persistent field.
     */
    private String organizationType;

    /**
     * Nullable persistent field.
     */
    private String country;

    /**
     * The city.
     */
    private String city;

    /**
     * The state.
     */
    private String state;

    /**
     * The user's group memberships.
     */
    private Set<Membership> memberships = new HashSet<>();

    /**
     * Additional user's personal data.
     */
    private Set<UserData> userData = new HashSet<>();

    /**
     * The user's Openid.
     */
    private String openid;

    /**
     * The user's Distinguished Name
     */
    private String dn;

    /**
     * Minimal constructor to create a new instance with only the required properties.
     */
    public User(String firstName, String lastName, String email, String userName, String openid) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.openid = openid;
    }

    /**
     * Minimal constructor to create a new instance with only the required properties.
     */
    public User(Serializable identifier, Serializable version, String firstName, String lastName, String email, String userName, String openid) {

        super(identifier, version);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.openid = openid;
    }

    /**
     * Convenience full arguments constructor to create a new instance, with all optional and required fields.
     *
     * @param firstName        the first name
     * @param middleName       the middle name
     * @param lastName         the last name
     * @param email            the email
     * @param userName         the user name
     * @param password         the password
     * @param loginType        the login type
     * @param organization     the organization
     * @param organizationType the organization type
     * @param city             the city
     * @param state            the state
     * @param country          the country
     */
    public User(String firstName, String middleName, String lastName, String email, String userName,
                String password, String loginType, String organization, String organizationType, String city, String state,
                String country, String openid, String dn) {

        super(true);

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.loginType = loginType;
        this.organization = organization;
        this.organizationType = organizationType;
        this.city = city;
        this.state = state;
        this.country = country;
        this.openid = openid;
        this.dn = dn;
    }

    /**
     * No arguments constructor for Hibernate.
     */
    public User() {

        super();
    }

    /**
     * Constructor to match an already existing instance that is persisted in the database.
     *
     * @param identifier the identifier
     * @param version    the version
     */
    public User(UUID identifier, Integer version) {

        super(identifier, version);
    }

    /**
     * Method to return the groups where the user is enrolled with default role, and VALID status.
     *
     * @return the groups
     */
    public List<Group> getGroups() {

        return getGroupsForRole(Role.DEFAULT);
    }

    /**
     * Method to return the groups where the user is enrolled with a given role, and VALID status.
     *
     * @param role the role
     * @return the groups for role
     */
    public List<Group> getGroupsForRole(Role role) {

        return getGroupsForRoleAndStatus(role, Status.VALID);
    }

    /**
     * Method to return the groups where the user is enrolled with a given role, and given status.
     *
     * @param role   the role
     * @param status the status
     * @return the groups for role and status
     */
    public List<Group> getGroupsForRoleAndStatus(Role role, Status status) {

        List<Group> groups = new ArrayList<>();
        for (Membership membership : this.memberships) {
            if (membership.getRole().equals(role) && (membership.getStatus() == status)) {
                groups.add(membership.getGroup());
            }
        }
        return groups;
    }

    /**
     * Method to return the (valid) roles of a user in a given group.
     *
     * @param group the group
     * @return the roles for group
     */
    public List<Role> getRolesForGroup(Group group) {
        return getRolesForGroup(group, Status.VALID);
    }

    /**
     * Method to retirn the roles of a user in a given group, for a specific status.
     *
     * @param group
     * @param status
     * @return
     */
    public List<Role> getRolesForGroup(Group group, Status status) {

        List<Role> roles = new ArrayList<>();
        for (Membership membership : this.memberships) {
            if (membership.getGroup().equals(group) && (status == membership.getStatus())) {
                roles.add(membership.getRole());
            }
        }
        return roles;
    }

    /**
     * Method to return all groups administered by the user.
     *
     * @return the admin groups
     */
    public List<Group> getAdminGroups() {
        return getGroupsForRole(Role.ADMIN);
    }

    /**
     * Method to delete all memberships of the user with a given group.
     *
     * @param group the group
     */
    public void deleteMembershipsForGroup(Group group) {

        Set<Membership> memberships = getMembershipsForGroup(group);
        for (Membership membership : memberships) {
            getMemberships().remove(membership);
        }

    }

    /**
     * Method to check wether a user has a (valid) given role for a given group.
     *
     * @param role  the role
     * @param group the group
     * @return true, if checks for role in group
     */
    public boolean hasRoleInGroup(Role role, Group group) {

        Membership membership = getMembership(group, role);
        return (membership != null) && (membership.getStatus() == Status.VALID);
    }

    /**
     * Shortcut method to check wether the user is an administrator for a specific group.
     *
     * @param group the group
     * @return true, if checks if is admin
     */
    public boolean isAdmin(Group group) {

        return hasRoleInGroup(Role.ADMIN, group);
    }

    /**
     * Shortcut method to check if the user is an administrator for the Root group.
     *
     * @return true, if checks if is root
     */
    public boolean isRoot() {

        for (Membership membership : this.getMemberships()) {
            if (membership.getGroup().getName().equals(Group.GROUP_ROOT)) {
                return isAdmin(membership.getGroup());
            }
        }
        return false;
    }

    public void addMembership(Membership membership) {

        Set<Membership> membershipSet = this.getMemberships();

        membershipSet.add(membership);
    }

    /**
     * Method to retrieve a specific user membership, if existing Note that no check is performed on the status of the membership.
     *
     * @param role  the role
     * @param group the group
     * @return the membership
     */
    public Membership getMembership(Group group, Role role) {

        Membership membership = null;
        for (Membership memb : getMemberships()) {
            if (memb.getRole().equals(role) && memb.getGroup().equals(group)) {
                membership = memb;
            }
        }
        return membership;
    }

    /**
     * Method to retrieve all memberships for a given group Note that no check is performed on the status of the membership.
     *
     * @param group the group
     * @return the memberships for group
     */
    public Set<Membership> getMembershipsForGroup(Group group) {

        final Set<Membership> memberships2 = new HashSet<>();
        for (Membership membership : this.memberships) {
            if (membership.getGroup().equals(group)) {
                memberships2.add(membership);
            }
        }
        return memberships2;
    }

    /**
     * Method to retrieve a specific registration field value for the current user.
     *
     * @param groupData the group data
     * @return the user data for group data
     */
    public UserData getUserDataForGroupData(GroupData groupData) {

        for (UserData userData : getUserData()) {
            if (userData.getGroupData().equals(groupData)) {
                return userData;
            }
        }
        return null;
    }

    /**
     * Convenience method to return a map of the user registration fields.
     *
     * @return the user data map
     */
    public Map<GroupData, UserData> getUserDataMap() {

        Map<GroupData, UserData> userDataMap = new HashMap<>();
        for (UserData userData : getUserData()) {
            userDataMap.put(userData.getGroupData(), userData);
        }
        return userDataMap;
    }

    /**
     * Utility method to create a map of the form (Group,List<Membership>) containing only the VALID user memberships.
     *
     * @return the group to memberships map
     */
    public Map<Group, List<Membership>> getGroupToMembershipsMap() {

        Map<Group, List<Membership>> map = new HashMap<>();
        for (Membership membership : this.memberships) {
            Group group = membership.getGroup();
            if (map.containsKey(group)) {
                map.get(group).add(membership);
            } else {
                List<Membership> groupMemberships = new ArrayList<>();
                groupMemberships.add(membership);
                map.put(group, groupMemberships);
            }

        }
        return map;

    }

    /**
     * Method to retrieve the groups that the user belongs to, with a role enabling a given operation NOTE: this method checks for VALID membership status.
     *
     * @param operation the operation
     * @return the groups for operation
     */
    public Set<Group> getGroupsForOperation(Operation operation) {

        Set<Group> groups = new HashSet<>();
        for (Membership membership : this.memberships) {
            if (membership.getStatus() == Status.VALID) {
                Role role = membership.getRole();
                Set<Operation> operations = role.getOperations();
                if (operations.contains(operation)) {
                    groups.add(membership.getGroup());
                }
            }
        }
        return groups;
    }

    /**
     * Convenience method to return the status of the user's default role in the default group.
     *
     * @return the status
     */
    public Status getStatus() {

        for (Membership membership : this.getMemberships()) {
            if (membership.getGroup().getName().equals(Group.GROUP_DEFAULT)) {
                return membership.getStatus();
            }
        }
        return Status.UNKNOWN;
    }

    /**
     * Convenience method to return the status of the user's default role in a given group.
     *
     * @param group the group
     * @return the status for group
     */
    public Status getStatusForGroup(Group group) {

        Status status = Status.UNKNOWN;

        Membership membership = getMembership(group, Role.DEFAULT);

        if (membership != null) {

            status = membership.getStatus();
        }

        return status;
    }

    /**
     * Gets the can change password.
     *
     * @return the can change password
     */
    public boolean getCanChangePassword() {

        return this.loginType.equalsIgnoreCase(User.LOGIN_DEFAULT);
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {

        return this.firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {

        return this.middleName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    public void setMiddleName(String middleName) {

        this.middleName = middleName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {

        return this.email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {

        return this.userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {

        this.userName = userName;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {

        this.password = password;
    }

    public URI getMyProxyEndpoint() {

        YadisResolver resolver = new YadisResolver(new HttpFetcherFactory());

        Set<String> serviceTypes = new HashSet<>();

        serviceTypes.add("urn:esg:security:myproxy-service");

        URI myproxyEndpoint;

        try {

            YadisResult yadisResult = resolver.discover(this.getOpenid(), 10, new HttpCache(), serviceTypes);

            XrdsServiceEndpoint endpoint = (XrdsServiceEndpoint) yadisResult.getEndpoints().get(0);
            myproxyEndpoint = new URI(endpoint.getUri());

        } catch (DiscoveryException e) {

            throw new RuntimeException("Failed to get yadis document: " + this.getOpenid(), e);

        } catch (URISyntaxException e) {

            throw new RuntimeException("Failed to get yadis document: " + this.getOpenid(), e);

        } catch (Throwable e) {

            throw new RuntimeException("Failed to get yadis document: " + this.getOpenid(), e);
        }

        return myproxyEndpoint;
    }

    /**
     * Gets the login type.
     *
     * @return the login type
     */
    public String getLoginType() {

        return this.loginType;
    }

    /**
     * Sets the login type.
     *
     * @param loginType the new login type
     */
    public void setLoginType(String loginType) {

        this.loginType = loginType;
    }

    /**
     * Gets the organization.
     *
     * @return the organization
     */
    public String getOrganization() {

        return this.organization;
    }

    /**
     * Sets the organization.
     *
     * @param organization the new organization
     */
    public void setOrganization(String organization) {

        this.organization = organization;
    }

    /**
     * Gets the organization type.
     *
     * @return the organization type
     */
    public String getOrganizationType() {

        return this.organizationType;
    }

    /**
     * Sets the organization type.
     *
     * @param organizationType the new organization type
     */
    public void setOrganizationType(String organizationType) {

        this.organizationType = organizationType;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public String getCountry() {

        return this.country;
    }

    /**
     * Sets the country.
     *
     * @param country the new country
     */
    public void setCountry(String country) {

        this.country = country;
    }

    /**
     * Gets the memberships.
     *
     * @return the memberships
     */
    public Set<Membership> getMemberships() {

        return this.memberships;
    }

    /**
     * Sets the memberships.
     *
     * @param memberships the new memberships
     */
    public void setMemberships(Set<Membership> memberships) {

        this.memberships = memberships;
    }

    /**
     * Gets the user data.
     *
     * @return the user data
     */
    public Set<UserData> getUserData() {

        return this.userData;
    }

    /**
     * Sets the user data.
     *
     * @param userData the new user data
     */
    public void setUserData(Set<UserData> userData) {

        this.userData = userData;
    }

    /**
     * Method implementation returns the user's full name.
     *
     * @return the full name
     */
    @Override
    public String getName() {

        return this.firstName + " " + this.lastName;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {

        return this.city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {

        this.city = city;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {

        return this.state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(String state) {
        this.state = state;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public boolean isRemoteUser() {

        boolean result = false;

        if (this.userName == null) {

            result = true;
        }

        return result;
    }
}
