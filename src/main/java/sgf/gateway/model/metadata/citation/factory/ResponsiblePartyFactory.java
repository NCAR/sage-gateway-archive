package sgf.gateway.model.metadata.citation.factory;

import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

public interface ResponsiblePartyFactory {

    ResponsibleParty createResponsibleParty(ResponsiblePartyRole role);
}
