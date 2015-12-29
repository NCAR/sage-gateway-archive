package sgf.gateway.web.controllers.dataset.responsibleParty;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.metadata.DeleteResponsiblePartyDetails;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, DeleteResponsiblePartyCommand.class})
public class DeleteResponsiblePartyCommand implements DeleteResponsiblePartyDetails {

    private final Dataset dataset;
    private final ResponsibleParty responsibleParty;

    @NotNull(groups = Required.class, message = "confirmDelete is required.")
    @AssertTrue(groups = Data.class, message = "confirmDelete must be true.")
    private Boolean confirmed = false;

    public DeleteResponsiblePartyCommand(Dataset dataset, ResponsibleParty responsibleParty) {

        this.dataset = dataset;
        this.responsibleParty = responsibleParty;
    }

    @Override
    public UUID getDatasetIdentifier() {

        return this.dataset.getIdentifier();
    }

    @Override
    public UUID getResponsiblePartyIdentifier() {

        return this.responsibleParty.getIdentifier();
    }

    public void setConfirmDelete(Boolean confirmed) {

        this.confirmed = confirmed;
    }

    public Boolean getConfirmDelete() {

        return this.confirmed;
    }
}
