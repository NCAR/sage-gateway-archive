package sgf.gateway.model.metadata.citation;

import java.io.Serializable;
import java.util.Comparator;

public class ResponsiblePartyComparator implements Comparator<ResponsibleParty>, Serializable {

    private static final long serialVersionUID = 1L;

    // Compare name, ignoring case
    public int compare(ResponsibleParty contact1, ResponsibleParty contact2) {

        String name1 = contact1.getIndividualName();
        String name2 = contact2.getIndividualName();

        return name1.compareToIgnoreCase(name2);
    }

}

