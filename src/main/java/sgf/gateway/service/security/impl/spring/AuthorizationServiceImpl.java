package sgf.gateway.service.security.impl.spring;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.Principal;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AuthorizationService;

import java.util.Set;

/**
 * Implementation of {@link AuthorizationService} currently based on the following logic:
 * <p/>
 * 1) The authorization for a LogicalFile is either established directly, or derived from the authorization of the parent Dataset.
 * <p/>
 * 2) The authorization for a Dataset is established by iteratively looking for Permissions up the Dataset hierarchy,
 * starting with the given Dataset, and returning the first positive or negative match.
 * <p/>
 * 3) If no Permissions are found for the given Resource, negative authorization is returned.
 */

public class AuthorizationServiceImpl implements AuthorizationService {

    public boolean authorize(User user, Dataset resource, Operation operation) throws AuthorizationException {

        // pulled from FileAccessPoint, if the dataset is retracted we shouldn't authorize access.
        if (resource.isRetracted()) {
            return false;
        }

        // retrieve the set of Principals that are allowed to execute the given Operation on this Resource
        Set<Principal> principals = resource.getPrincipalsForOperation(operation);

        // check for Guest authorization
        for (Principal p : principals) {

            if (p.getName().equals(Group.GROUP_GUEST)) {
                return true;
            }
        }

        // specific user authorization
        if (principals.contains(user)) {
            return true;

            // match authorized groups versus local user memberships
        } else {

            Set<Group> ugroups = user.getGroupsForOperation(operation);
            for (Group ugroup : ugroups) {
                if (principals.contains(ugroup)) {
                    return true;
                }
            }
        }

        // deny authorization by default
        return false;
    }
}
