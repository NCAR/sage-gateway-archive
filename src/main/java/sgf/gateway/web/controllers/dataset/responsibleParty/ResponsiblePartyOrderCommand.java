package sgf.gateway.web.controllers.dataset.responsibleParty;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;

import java.util.List;

public class ResponsiblePartyOrderCommand {

    private Dataset dataset;

    private List<ResponsibleParty> responsibleParties;

    public ResponsiblePartyOrderCommand(Dataset dataset) {

        this.dataset = dataset;
    }

    public ResponsiblePartyOrderCommand(Dataset dataset, List<ResponsibleParty> responsibleParties) {

        this.dataset = dataset;
        this.responsibleParties = responsibleParties;
    }

    public UUID getDatasetIdentifier() {

        return this.dataset.getIdentifier();
    }

    public Dataset getDataset() {
        return dataset;
    }

    public List<ResponsibleParty> getResponsibleParties() {
        return responsibleParties;
    }

    public void setResponsibleParties(List<ResponsibleParty> responsibleParties) {
        this.responsibleParties = responsibleParties;
    }
}
