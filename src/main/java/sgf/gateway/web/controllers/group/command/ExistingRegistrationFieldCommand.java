package sgf.gateway.web.controllers.group.command;

import org.safehaus.uuid.UUID;
import sgf.gateway.service.security.ExistingGroupDataRequest;

import java.util.ArrayList;
import java.util.Collection;

public class ExistingRegistrationFieldCommand implements ExistingGroupDataRequest {

    private String[] groupDataIdentifiers;

    public String[] getGroupDataIdentifiers() {

        return this.groupDataIdentifiers;
    }

    public void setGroupDataIdentifiers(String[] groupDataIdentifiers) {

        this.groupDataIdentifiers = groupDataIdentifiers;
    }

    public Collection<UUID> getExistingGroupDataIdentifiers() {

        Collection<UUID> collection = new ArrayList<UUID>();

        for (String identifier : this.groupDataIdentifiers) {

            collection.add(UUID.valueOf(identifier));
        }

        return collection;
    }
}
