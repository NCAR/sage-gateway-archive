package sgf.gateway.service.security.impl.acegi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Utility class for interoperability operations between Acegi and ESKE security model.
 */
public class AcegiUtils {

    private static final Log LOG = LogFactory.getLog(AcegiUtils.class);

    /**
     * Method to transform the User VALID Memberships into Acegi GrantedAuthorities.
     *
     * @param user                    the user
     * @param addAnonymousAuthorities the add anonymous authorities
     * @return the granted authorities
     */
    public static Collection<GrantedAuthority> getGrantedAuthorities(User user, boolean addAnonymousAuthorities) {

        // empty list
        List<String> gas = new ArrayList<>();

        // loop over user memberships
        if (user != null) {

            for (Membership membership : user.getMemberships()) {
                if (membership.getStatus() == Status.VALID) {
                    gas.add(getAuthority(membership.getGroup().getName(), membership.getRole().getName()));
                }
            }

            // add user identity
            gas.add(getAuthority(user.getOpenid()));
        }

        // add guest authorities ?
        if (addAnonymousAuthorities) {
            addAnonymousGrantedAuthorities(gas);
        }

        return getGrantedAuthorities(gas);

    }

    /**
     * Method that transforms the set of (operation-specific) permissions on a resource into a concatenated string that can be used by the Spring Security
     * authorization tag.
     *
     * @param dataset
     */
    public static String getGrantingAuthorities(Dataset dataset, Operation operation) {

        StringBuilder sb = new StringBuilder("");

        Set<Principal> principals = dataset.getPrincipalsForOperation(operation);

        for (Principal principal : principals) {

            if (principal instanceof Group) {

                Group group = (Group) principal;

                for (Role role : Operation.getRoles(operation)) {
                    // authorities are comma-separated
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(getAuthority(group.getName(), role.getName()));
                }

            }
            if (principal instanceof User) {
                // authorities are comma-separated
                if (sb.length() > 0) {
                    sb.append(",");
                }
                User user = (User) principal;
                sb.append(getAuthority(user.getOpenid()));
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Operation=" + operation.name() + " needed granted authorities for operation=" + sb.toString());
        }
        return sb.toString();

    }

    /**
     * Method to build an Acegi GrantedAuthority from a group and role names.
     *
     * @param groupName the group name
     * @param roleName  the role name
     * @return the authority
     */
    public static String getAuthority(String groupName, String roleName) {

        return "group_" + groupName + "_role_" + roleName;
    }

    /**
     * Method to build an Acegi GrantedAuthority from a user identifier.
     *
     * @param user
     * @return
     */
    public static String getAuthority(String user) {
        return "user_" + user;
    }

    /**
     * Method to parse a GrantedAuthority name and return the Group, Role names.
     *
     * @param authority the authority
     * @return the group and role
     */
    public static String[] getGroupAndRole(String authority) {

        int i = authority.indexOf("group_");
        int j = authority.indexOf("_role_");

        // return empty group and role if authority string cannot be parsed correctly
        String[] names = new String[]{"", ""};

        if ((i == 0) && (j > 0)) {
            names[0] = authority.substring(i + "group_".length(), j); // group name
            names[1] = authority.substring(j + "_role_".length()); // role name
        }
        return names;
    }

    /**
     * Method to add the standard anonymous authorities to a given list of GrantedAuthority.
     *
     * @param gas the gas
     */
    private static void addAnonymousGrantedAuthorities(List<String> gas) {

        String guestDefault = getAuthority(Group.GROUP_GUEST, Role.DEFAULT.getName());

        if (!gas.contains(guestDefault)) {
            gas.add(guestDefault);
        }

    }

    /**
     * Method to convert a list of granted authority strings into a GrantedAuthority[].
     *
     * @param gas the gas
     * @return the granted authorities
     */
    public static Collection<GrantedAuthority> getGrantedAuthorities(List<String> gas) {

        Collection<GrantedAuthority> authorities = new ArrayList<>(gas.size());

        for (String ga : gas) {
            authorities.add(new SimpleGrantedAuthority(ga));
        }

        return authorities;
    }

    /**
     * Debug method to build a string of GrantedAuthority.
     *
     * @param authorities the gas
     * @return the string
     */
    public static String printGrantedAuthorities(Collection<GrantedAuthority> authorities) {

        StringBuilder sb = new StringBuilder();

        for (GrantedAuthority ga : authorities) {
            sb.append(ga.toString()).append(" ");
        }

        return sb.toString();
    }

}
