package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.web.controllers.cadis.publish.command.CadisMoveDatasetCommand;

import java.util.List;

public interface CadisDatasetService {

    Dataset save(DatasetDetails datasetDetails);

    Dataset update(DatasetDetails datasetDetails);

    Dataset saveOrUpdate(DatasetDetails datasetDetails);

    Dataset createDataset(Dataset parentDataset, CadisDatasetDetails createCadisDatasetRequest);

    Dataset updateDataset(String datasetShortName, CadisDatasetDetails createCadisDatasetRequest);

    Dataset moveDataset(CadisMoveDatasetCommand command);

    Dataset moveDataset(Dataset datasetToMove, Dataset newParentDataset);

    void addResponsibleParty(ResponsiblePartyDetails responsiblePartyDetails);

    void updateResponsibleParty(ResponsiblePartyDetails responsiblePartyDetails);

    void reorderResponsibleParties(Dataset dataset, List<ResponsibleParty> parties);

    void orderChildDatasets(Dataset dataset, List<Dataset> childDatasets);

    void deleteResponsibleParty(DeleteResponsiblePartyDetails deleteResponsiblePartyDetails);

    void addAwardToDataset(UUID identifier, String awardNumber);

    void deleteAwardFromDataset(UUID identifier, String awardNumber);
}
