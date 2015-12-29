package sgf.gateway.utils.modelfilters;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyComparator;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

import java.util.*;

public class ResponsiblePartyFilter {

    static final Comparator<ResponsibleParty> responsiblePartyComparator = new ResponsiblePartyComparator();

    public static Collection<ResponsibleParty> getMetadataContacts(Dataset dataset) {

        Collection<ResponsibleParty> contacts = dataset.getDescriptiveMetadata().getResponsiblePartiesByRole(ResponsiblePartyRole.pointOfContact);

        List<ResponsibleParty> contactList = new ArrayList<>(contacts);

        Collections.sort(contactList, responsiblePartyComparator);

        return contacts;
    }

    public static Collection<ResponsibleParty> getPIContacts(Dataset dataset) {
        return dataset.getDescriptiveMetadata().getResponsiblePartiesByRole(ResponsiblePartyRole.pointOfContact);
    }

    public static Collection<ResponsibleParty> getLeadPIs(Dataset dataset) {

        List<ResponsibleParty> leadPIs;

        leadPIs = (ArrayList<ResponsibleParty>) dataset.getDescriptiveMetadata().getResponsiblePartiesByRole(ResponsiblePartyRole.principalInvestigator);

        Collections.sort(leadPIs, responsiblePartyComparator);

        return leadPIs;
    }

    public static Collection<ResponsibleParty> getCoPIs(Dataset dataset) {

        List<ResponsibleParty> coPIs;

        coPIs = (ArrayList<ResponsibleParty>) dataset.getDescriptiveMetadata().getResponsiblePartiesByRole(ResponsiblePartyRole.coPrincipalInvestigator);

        Collections.sort(coPIs, responsiblePartyComparator);

        return coPIs;
    }

    public static Collection<ResponsibleParty> getCollaboratingPIs(Dataset dataset) {

        List<ResponsibleParty> collaboratingPIs;

        collaboratingPIs = (ArrayList<ResponsibleParty>) dataset.getDescriptiveMetadata().getResponsiblePartiesByRole(ResponsiblePartyRole.collaboratingPrincipalInvestigator);

        Collections.sort(collaboratingPIs, responsiblePartyComparator);

        return collaboratingPIs;
    }
}
