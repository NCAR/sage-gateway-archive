package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.model.metadata.citation.factory.ResponsiblePartyFactory;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata.Contributor;

import java.util.ArrayList;
import java.util.List;

public class NodeDatasetContacts implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    private final ResponsiblePartyFactory responsiblePartyFactory;

    public NodeDatasetContacts(ResponsiblePartyFactory responsiblePartyFactory) {

        this.responsiblePartyFactory = responsiblePartyFactory;
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        InvDataset invDataset = payload.getInvDataset();

        List<ResponsibleParty> contacts = getContacts(invDataset);

        payload.setContacts(contacts);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dataset ResponsibleParty is " + contacts + " for payload " + payload);
        }
    }

    private List<ResponsibleParty> getContacts(InvDataset invDataset) {

        List<ResponsibleParty> contacts = new ArrayList<ResponsibleParty>();

        if (hasContributors(invDataset)) {

            for (Contributor contributor : invDataset.getContributors()) {

                ResponsibleParty contact = createContactFromThreddsContributor(contributor);

                contacts.add(contact);
            }
        }

        return contacts;
    }

    private Boolean hasContributors(InvDataset invDataset) {
        Boolean hasContributors = invDataset.getContributors() != null && !invDataset.getContributors().isEmpty();

        return hasContributors;
    }

    private String getContributorRole(Contributor contributor) {
        String role = contributor.getRole();

        return role.trim();
    }

    private ResponsibleParty createContactFromThreddsContributor(Contributor contributor) {

        String role = getContributorRole(contributor);

        ResponsibleParty contact = createContact(role);

        String name = getContributorName(contributor);

        contact.setIndividualName(name);

        return contact;
    }

    private String getContributorName(Contributor contributor) {

        String dirtyName = contributor.getName();

        if (dirtyName.lastIndexOf(",") != -1) {
            dirtyName = dirtyName.substring(0, dirtyName.lastIndexOf(","));
        } else {
            dirtyName = dirtyName.substring(0);
        }

        String name = dirtyName.trim();

        return name;
    }

    private ResponsibleParty createContact(String roleString) {

        ResponsiblePartyRole role = lookupRoleFromCode(roleString);

        ResponsibleParty contact = this.responsiblePartyFactory.createResponsibleParty(role);

        return contact;
    }

    protected ResponsiblePartyRole lookupRoleFromCode(String roleCode) {

        for (ResponsiblePartyRole role : ResponsiblePartyRole.values()) {
            if (role.name().equalsIgnoreCase(roleCode)) {
                return role;
            }
        }

        return null;
    }

}
