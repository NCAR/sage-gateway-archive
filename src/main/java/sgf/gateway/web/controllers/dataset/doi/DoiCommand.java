package sgf.gateway.web.controllers.dataset.doi;

import org.hibernate.validator.constraints.NotBlank;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.doi.DataciteDoiRequest;
import sgf.gateway.validation.data.ValidDate;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;
import javax.validation.constraints.Digits;
import java.util.Calendar;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, DoiCommand.class})
public class DoiCommand implements DataciteDoiRequest {

    private final Dataset dataset;

    private String doi;
    @NotBlank(groups = Required.class, message = "Creator is required")
    private String creator;

    @NotBlank(groups = Required.class, message = "Title is required")
    private String title;

    private String publisher;

    @NotBlank(groups = Required.class, message = "Publication Year is required")
    @Digits(fraction = 0, integer = 4, message = "Publication Year must use the following format: YYYY")
    @ValidDate(groups = Type.class, format = "yyyy", message = "Publication Year must use the following format: YYYY")
    private String publicationYear;

    private String resourceType = "Dataset";

    public DoiCommand(Dataset dataset, String publisher) {

        this.dataset = dataset;
        this.doi = dataset.getDOI();
        this.title = dataset.getTitle();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataset.getDateUpdated());

        this.publisher = publisher;

        this.publicationYear = String.valueOf(calendar.get(Calendar.YEAR));
    }

    @Override
    public UUID getDatasetIdentifier() {

        return this.dataset.getIdentifier();
    }

    @Override
    public String getDoi() {

        return this.doi;
    }

    public void setCreator(String creator) {

        this.creator = creator;
    }

    @Override
    public String getCreator() {

        return this.creator;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    @Override
    public String getTitle() {

        return this.title;
    }

    @Override
    public String getPublisher() {

        return this.publisher;
    }

    public void setPublicationYear(String publicationYear) {

        this.publicationYear = publicationYear;
    }

    @Override
    public String getPublicationYear() {

        return this.publicationYear;
    }

    @Override
    public String getResourceType() {

        return this.resourceType;
    }

}
