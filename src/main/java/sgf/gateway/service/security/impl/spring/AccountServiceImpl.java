package sgf.gateway.service.security.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.*;
import sgf.gateway.model.security.factory.UserFactory;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.security.*;
import sgf.gateway.web.controllers.security.openid.OpenIdConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link AccountService} versus the ESKE relational database.
 */

public class AccountServiceImpl implements AccountService {

    private static final Log LOG = LogFactory.getLog(AccountServiceImpl.class);

    private final UserFactory userFactory;

    private final OpenIdConfiguration openIdConfiguration;

    private final Gateway gateway;

    // FIXME change the userRepository to be passed in as a constructor parameter.
    private UserRepository userRepository;

    // FIXME change the groupRepository to be passed in as a constructor parameter.
    private GroupRepository groupRepository;

    private CryptoService cryptoService;

    private MailService mailService;

    private GroupMembershipEventPublisher groupMembershipEventPublisher;

    public AccountServiceImpl(UserFactory userFactory, OpenIdConfiguration openIdConfiguration, Gateway gateway) {

        this.userFactory = userFactory;
        this.openIdConfiguration = openIdConfiguration;
        this.gateway = gateway;
    }

    public User registerUser(RegisterUserRequest registerUserRequest) {

        User user = tryRegisterUser(registerUserRequest);

        this.emailAdminUserRegistrationNotification(user);

        this.emailUserRegistrationNotification(user);

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected User tryRegisterUser(RegisterUserRequest registerUserRequest) {

        String openid = openIdConfiguration.getBaseOpenIdURI() + registerUserRequest.getUsername();

        User user = userFactory.createUser(registerUserRequest.getUsername(), openid, registerUserRequest.getFirstName(), registerUserRequest.getLastName(), registerUserRequest.getEmail());

        user.setOrganization(registerUserRequest.getOrganization());
        user.setPassword(cryptoService.encrypt(registerUserRequest.getPassword()));

        Group defaultGroup = getGroup(Group.GROUP_DEFAULT);
        user.getMemberships().add(new Membership(user, defaultGroup, Role.DEFAULT, Status.VALID));

        AccountServiceImpl.this.userRepository.add(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User registerRemoteUser(RegisterRemoteUserRequest request) {

        User user = this.userFactory.createRemoteUser(request.getOpenid(), request.getFirstName(), request.getLastName(), request.getEmail());

        Group defaultGroup = getGroup(Group.GROUP_DEFAULT);
        user.getMemberships().add(new Membership(user, defaultGroup, Role.DEFAULT, Status.VALID));

        userRepository.add(user);

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User registerRemoteUser(String openId) {

        User user = userFactory.createRemoteUser(openId);

        Group defaultGroup = getGroup(Group.GROUP_DEFAULT);
        user.getMemberships().add(new Membership(user, defaultGroup, Role.DEFAULT, Status.VALID));

        userRepository.add(user);

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void changeEmailAddress(ChangeEmailRequest request) {

        User user = this.userRepository.getUser(request.getUserId());

        user.setEmail(request.getEmail());
    }

    @Deprecated
    public void confirmRegistration(UUID identifier) {

        tryConfirmRegistration(identifier);

        User user = this.userRepository.getUser(identifier);

        this.emailAdminUserRegistrationNotification(user);

        this.emailUserRegistrationNotification(user);
    }

    @Deprecated
    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryConfirmRegistration(UUID identifier) {

        // change the user memberships to appropriate status
        User user = this.userRepository.getUser(identifier);

        Set<Membership> memberships = user.getMemberships();

        for (Membership membership : memberships) {
            // NOTE: for security reasons, change only membership that are currently REQUESTED
            if (membership.getStatus() == Status.REQUESTED) {

                // default group: status REQUESTED --> VALID
                if (membership.getGroup().getName().equals(Group.GROUP_DEFAULT)) {

                    membership.setStatus(Status.VALID);

                } else {
                    // automatic group: status REQUESTED --> VALID
                    if (membership.getGroup().isAutomaticApproval()) {

                        membership.setStatus(Status.VALID);
                        // moderated group: status REQUESTED --> PENDING
                    } else {

                        membership.setStatus(Status.PENDING);
                    }
                }
            }
        }
    }

    public void registerUserInGroup(GroupRegistrationRequest groupRegistrationRequest) {

        this.tryRegisterUserInGroup(groupRegistrationRequest);

        User user = this.userRepository.getUser(groupRegistrationRequest.getUserIdentifier());

        Group group = this.groupRepository.getGroup(groupRegistrationRequest.getGroupIdentifier());

        this.subscribeUserToGroupMailingLists(user, group);

        this.notifyGroupAdminsOfUserRegistration(user, group);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void tryRegisterUserInGroup(GroupRegistrationRequest groupRegistrationRequest) {

        User user = this.userRepository.getUser(groupRegistrationRequest.getUserIdentifier());

        Group group = this.groupRepository.getGroup(groupRegistrationRequest.getGroupIdentifier());

        Map<GroupData, Boolean> groupDataMap = group.getGroupData();

        Set<GroupData> groupDataSet = groupDataMap.keySet();

        for (GroupData groupData : groupDataSet) {

            UserData userData = user.getUserDataForGroupData(groupData);

            String userDataValue = groupRegistrationRequest.getGroupData().get(groupData.getIdentifier());

            if (userData == null) {

                userData = new UserData(userDataValue, groupData, user);

                user.getUserData().add(userData);

            } else {

                userData.setValue(userDataValue);
            }
        }

        this.enrollUserInGroup(user, group);
    }

    private void enrollUserInGroup(User user, Group group) {

        // determine group status based in user default status, group type
        Status defStatus = user.getStatus();
        Status groupStatus = Status.UNKNOWN;
        // user has confirmed email
        if (defStatus == Status.VALID) {
            if (group.isAutomaticApproval()) {
                groupStatus = Status.VALID;
            } else {
                groupStatus = Status.PENDING;
            }
            // user has NOT confirmed email
        } else if (defStatus.equals(Status.REQUESTED)) {
            if (group.isAutomaticApproval()) {
                groupStatus = Status.REQUESTED;
            } else {
                groupStatus = Status.REQUESTED;
            }
        }
        // enroll user in group with given status
        enrollUserInGroup(user, group, group.getDefaultRoles(), groupStatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void enrollUserInGroup(User user, Group group, Set<Role> roles, Status status) {

        // always add default role to the list (might be present already)
        roles.add(Role.DEFAULT);

        // current user memberships
        Set<Membership> memberships = user.getMemberships();
        // retrieve current user memberships in group, remove stale memberships
        Set<Membership> groupMemberships = user.getMembershipsForGroup(group);
        for (Membership groupMembership : groupMemberships) {
            if (!roles.contains(groupMembership.getRole())) {
                memberships.remove(groupMembership);
            }
        }

        // loop over new roles, insert or update memberships
        for (Role role : roles) {
            Membership membership = user.getMembership(group, role);
            // insert new membership
            if (membership == null) {
                membership = new Membership(user, group, role, status);
                memberships.add(membership);
                // update existing membership
            } else {
                membership.setStatus(status);
            }
        }

        // publish group membership event
        groupMembershipEventPublisher.publishEvent(new GroupMembershipEventImpl(this, user, group));

        // persist all changes
        this.userRepository.storeUser(user);
    }

    public void notifyGroupAdminsOfUserRegistration(User user, Group group) {

        List<User> adminUsers = new ArrayList<>(group.getUsersInRole(Role.ADMIN));

        if (group.isAutomaticApproval()) {

            // notify administrators
            Set<Membership> groupMemberships = user.getMembershipsForGroup(group);

            this.mailService.sendAdminGroupRoleUpdateMailMessage(adminUsers, user, group.getName(), new ArrayList<>(groupMemberships));

        } else {

            String portalUrl = gateway.getBaseSecureURL().toString();
            this.mailService.sendAdminGroupRegistrationMailMessage(adminUsers, user, group.getName(), portalUrl);
        }
    }

    public void subscribeUserToGroupMailingLists(User user, Group group) {

        // check for user status
        if (user.getStatusForGroup(group) == Status.VALID) {

            // loop over group's registration fields
            Map<GroupData, Boolean> groupDataMap = group.getGroupData();
            Map<GroupData, UserData> userDataMap = user.getUserDataMap();
            for (Object obj : groupDataMap.keySet()) {
                GroupData gd = (GroupData) obj;
                if (gd.getType() == GroupDataType.MAILING_LIST) {
                    // mandatory list OR user accepted optional mailing list
                    if (groupDataMap.get(gd) || ((userDataMap.get(gd) != null) && Boolean.valueOf(userDataMap.get(gd).getValue()))) {
                        String listAddress = gd.getValue();
                        LOG.info("AccountServiceImpl: subscribing user " + user.getUserName() + " to group mailing list " + listAddress);
                        try {
                            this.mailService.sendSimpleFromMailMessage(listAddress, user.getEmail(), "new subscription", "SUBSCRIBE");
                        } catch (final Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void changeUserPassword(User user, String newDecryptedPassword) {

        user.setPassword(cryptoService.encrypt(newDecryptedPassword));
        this.userRepository.storeUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String setRandomPassword(User user) {

        String newDecryptedPassword = RandomPasswordGenerator.getRandomPassword(12);
        String newEncryptedPassword = cryptoService.encrypt(newDecryptedPassword);
        user.setPassword(newEncryptedPassword);
        this.userRepository.storeUser(user);

        return newDecryptedPassword;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeUserFromGroup(User user, Group group) {

        user.deleteMembershipsForGroup(group);
        this.userRepository.storeUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createGroup(AddGroupRequest addGroupRequest, User user) {

        Group group = new Group(addGroupRequest.getGroupName(), addGroupRequest.getGroupDescription(), addGroupRequest.isAutomaticApproval(), addGroupRequest.isVisibleToUsers());
        // always add DEFAULT role
        group.getDefaultRoles().add(Role.DEFAULT);

        // persist group
        this.groupRepository.storeGroup(group);

        User currentUser = this.userRepository.getUser(user.getIdentifier());
        // assign memberships to administrator
        for (Role role : group.getDefaultRoles()) {
            currentUser.getMemberships().add(new Membership(currentUser, group, role, Status.VALID));
        }
        currentUser.getMemberships().add(new Membership(currentUser, group, Role.ADMIN, Status.VALID));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void disableAccount(UUID userIdentifier, boolean disable) {

        User user = this.userRepository.getUser(userIdentifier);

        // determine new status
        Status oldStatus = (disable ? Status.VALID : Status.INVALID);
        Status newStatus = (disable ? Status.INVALID : Status.VALID);

        // loop over user memberships
        for (Membership membership : user.getMemberships()) {
            if (membership.getStatus() == oldStatus) {
                membership.setStatus(newStatus);
            }
        }

        storeUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List listUnsubscribedGroups(User user) {

        // retrieve gateway groups
        List<Group> gatewayGroups = this.groupRepository.findGroups();

        // filter out unwanted groups
        return filterUnsubscribedGroups(user, gatewayGroups);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private List<Group> filterUnsubscribedGroups(User user, List<Group> groups) {

        // create list of unsubscribed groups
        List<Group> unsubscribedGroups = new ArrayList<>();

        // loop over all groups, exclude those the user is already
        // subscribed to
        for (Group group : groups) {
            if (group.isVisible()) {
                if (!group.isSystem()) {
                    if (user.getStatusForGroup(group) == Status.UNKNOWN) { // NOT EVER subscribed to
                        unsubscribedGroups.add(group);
                    }
                }
            }
        }

        return unsubscribedGroups;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Group getGroup(final String groupName) {

        return this.groupRepository.findGroupByName(groupName);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void storeUser(User user) {

        this.userRepository.storeUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGroupDataToGroup(UUID groupIdentifier, GroupDataRequest groupDataRequest) {

        Group group = this.groupRepository.getGroup(groupIdentifier);

        GroupData newGroupData = new GroupData(groupDataRequest.getName(), groupDataRequest.getDescription(), groupDataRequest.getGroupDataType());
        newGroupData.setValue(groupDataRequest.getValue());

        this.groupRepository.storeGroupData(newGroupData);

        group.getGroupData().put(newGroupData, false);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addExistingGroupDataToGroup(UUID groupIdentifier, ExistingGroupDataRequest existingGroupDataRequest) {

        Group group = this.groupRepository.getGroup(groupIdentifier);

        for (UUID groupDataIdentifier : existingGroupDataRequest.getExistingGroupDataIdentifiers()) {

            GroupData groupData = this.groupRepository.getGroupData(groupDataIdentifier);

            group.getGroupData().put(groupData, false);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeGroupDataFromGroup(UUID groupIdentifier, UUID groupDataIdentifier) {

        Group group = this.groupRepository.getGroup(groupIdentifier);

        GroupData groupData = this.groupRepository.getGroupData(groupDataIdentifier);

        group.getGroupData().remove(groupData);
    }

    private void emailAdminUserRegistrationNotification(User user) {

        // send notification of registration to Administrators
        Group rootGroup = this.getGroup(Group.GROUP_ROOT);

        Set<User> adminUsers = rootGroup.getAdministrators();

        String portalUrl = this.gateway.getBaseSecureURL().toString();

        this.mailService.sendAdminRegistrationMailMessage(adminUsers, user, portalUrl);
    }

    private void emailUserRegistrationNotification(User user) {

        // send notification of registration to user.
        this.mailService.sendAccountConfirmedMailMessage(user);
    }

    public boolean emailExists(String email) {

        boolean value = false;

        List<User> users = this.userRepository.findUsersByEmail(email);

        if (users.size() > 0) {

            value = true;
        }

        return value;
    }

    public void setUserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setGroupDao(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setGroupMembershipEventPublisher(GroupMembershipEventPublisher groupMembershipEventPublisher) {
        this.groupMembershipEventPublisher = groupMembershipEventPublisher;
    }
}
