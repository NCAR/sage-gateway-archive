package sgf.gateway.web.controllers.dataset.responsibleParty;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.service.metadata.ResponsiblePartyDetails;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, ResponsiblePartyCommand.class})
public class ResponsiblePartyCommand implements ResponsiblePartyDetails {

    private final Dataset dataset;

    private UUID responsiblePartyIdentifier;

    @NotNull(groups = Required.class, message = "Role is required.")
    private ResponsiblePartyRole role;

    @NotBlank(groups = Required.class, message = "Individual Name is required.")
    private String individualName;

    @Email(groups = Data.class, message = "Email Address is not a valid email address")
    private String email;

    public ResponsiblePartyCommand(Dataset dataset) {

        this.dataset = dataset;
    }

    public ResponsiblePartyCommand(Dataset dataset, ResponsibleParty responsibleParty) {

        this.dataset = dataset;
        this.responsiblePartyIdentifier = responsibleParty.getIdentifier();
        this.role = responsibleParty.getRole();
        this.individualName = responsibleParty.getIndividualName();
        this.email = responsibleParty.getEmail();
    }

    public UUID getDatasetIdentifier() {

        return this.dataset.getIdentifier();
    }

    public UUID getResponsiblePartyIdentifier() {

        return this.responsiblePartyIdentifier;
    }

    public void setRole(ResponsiblePartyRole role) {

        this.role = role;
    }

    public ResponsiblePartyRole getRole() {

        return this.role;
    }

    public void setIndividualName(String individualName) {

        this.individualName = individualName;
    }

    public String getIndividualName() {

        return this.individualName;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {

        return this.email;
    }
}
