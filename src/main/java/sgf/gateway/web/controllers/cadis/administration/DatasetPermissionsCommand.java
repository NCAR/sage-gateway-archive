package sgf.gateway.web.controllers.cadis.administration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.Principal;
import sgf.gateway.model.security.User;
import sgf.gateway.utils.spring.SelectableElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Command object to support the data permission assignment page.
 *
 * @author hannah
 */
public class DatasetPermissionsCommand {

    private static final Log LOG = LogFactory.getLog(DatasetPermissionsCommand.class);

    private Dataset dataset;

    private Collection<SelectableElement<User>> individualPermissions = new ArrayList<>();

    public DatasetPermissionsCommand(Dataset dataset, Set<User> users, Set<User> admins) {
        this.dataset = dataset;
        Set<Principal> writePermissionPrincipals = dataset.getPrincipalsForOperation(Operation.WRITE);

        for (User user : users) {

            if (writePermissionPrincipals.contains(user) || admins.contains(user)) {
                this.individualPermissions.add(new SelectableElement<>(true, user));
                LOG.info("Existing WRITE permission for " + user.getName());
            } else {
                this.individualPermissions.add(new SelectableElement<>(false, user));
            }
        }
    }

    /**
     * @return the dataset
     */
    public Dataset getDataset() {
        return this.dataset;
    }

    /**
     * @param dataset the dataset to set
     */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public void updateUserPermissions() {
        Set<Principal> writePermissionPrincipals = dataset.getPrincipalsForOperation(Operation.WRITE);
        for (SelectableElement<User> userSelected : this.individualPermissions) {
            if (!(writePermissionPrincipals.contains(userSelected.getElement())) && userSelected.isSelected()) {
                this.dataset.recursivelyAddPermissionToAllDatasets(userSelected.getElement(), Operation.WRITE);
                LOG.info("Added new WRITE permission for " + userSelected.getElement().getName());
            }
        }
    }

    /**
     * @return the individualPermissions
     */
    public Collection<SelectableElement<User>> getIndividualPermissions() {
        return this.individualPermissions;
    }

    /**
     * @param individualPermissions the individualPermissions to set
     */
    public void setIndividualPermissions(Collection<SelectableElement<User>> individualPermissions) {
        this.individualPermissions = individualPermissions;
    }
}
