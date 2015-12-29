package sgf.gateway.web.controllers.publishing.thredds;

import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.ShortNameExists;
import sgf.gateway.validation.type.ValidURI;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, PublishThreddsCatalogCommand.class})
public class PublishThreddsCatalogCommand {

    @ShortNameExists(groups = Persistence.class, message = "The Enclosing Dataset does not exist.")
    private String parentDatasetIdentifier;

    @NotBlank(groups = Required.class, message = "Thredds Catalog URI is required.")
    @ValidURI(groups = Type.class, message = "Thredds Calalog URI must be a valid URI.")
    private String threddsCatalogURI;

    public void setParentDatasetIdentifier(String parentDatasetIdentifier) {

        this.parentDatasetIdentifier = parentDatasetIdentifier;
    }

    public String getParentDatasetIdentifier() {

        return this.parentDatasetIdentifier;
    }

    public void setThreddsCatalogURI(String threddsCatalogURI) {

        this.threddsCatalogURI = threddsCatalogURI;
    }

    public String getThreddsCatalogURI() {

        return this.threddsCatalogURI;
    }
}
