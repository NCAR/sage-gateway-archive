package sgf.gateway.web.controllers.dataset.relatedLink;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, RelatedLinkCommand.class})
public class RelatedLinkCommand {

    private Dataset dataset;

    @NotBlank(groups = Required.class, message = "Related Link is required.")
    @URL(message = "Invalid website address")
    private String linkUri;

    @NotBlank(groups = Required.class, message = "Description is required.")
    private String linkText;

    private UUID relatedLinkId;

    public RelatedLinkCommand(Dataset dataset) {

        this.dataset = dataset;
    }

    public RelatedLinkCommand(Dataset dataset, RelatedLink link) {

        this.dataset = dataset;
        this.relatedLinkId = link.getIdentifier();
        this.linkUri = link.getUri().toString();
        this.linkText = link.getText();
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public String getLinkUri() {
        return linkUri;
    }

    public void setLinkUri(String linkUri) {

        this.linkUri = linkUri;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {

        this.linkText = linkText;
    }

    public UUID getRelatedLinkId() {
        return relatedLinkId;
    }

    public void setRelatedLinkId(UUID relatedLinkId) {
        this.relatedLinkId = relatedLinkId;
    }
}
