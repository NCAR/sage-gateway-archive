package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.PrincipalInvestigatorWithData;
import sgf.gateway.model.metadata.citation.ResponsibleParty;

import java.io.Serializable;
import java.util.List;

public interface ResponsiblePartyRepository extends Repository<ResponsibleParty, Serializable> {

    ResponsibleParty get(UUID projectIdentity);

    List<PrincipalInvestigatorWithData> findAllPIsWithData();

    List<PrincipalInvestigatorWithData> findAllResponsiblePartiesOrdered();

    List<Dataset> findDataForPI(String contact);
}
