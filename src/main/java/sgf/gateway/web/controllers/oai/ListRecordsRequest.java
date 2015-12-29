package sgf.gateway.web.controllers.oai;

import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.validation.data.ValidDate;
import sgf.gateway.validation.data.ValidTemporalBoundsDates;
import sgf.gateway.validation.groups.Type;

import java.util.Date;

@ValidTemporalBoundsDates(startDateField = "from", endDateField = "until", format = "yyyy-MM-dd'T'HH:mm:ss'Z'", message = "From Date must be before Until Date")
public class ListRecordsRequest extends OAIRequest implements ListRequest {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @ValidDate(groups = Type.class, format = DATE_FORMAT, message = "From Date must use the following format: yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date from;

    @ValidDate(groups = Type.class, format = DATE_FORMAT, message = "Until Date must use the following format: yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date until;
    private String set;

    @NotBlank(message = "metadataPrefix is required")
    private String metadataPrefix;

    public ListRecordsRequest() {
        super();
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

    public int getOffset() {
        return 0;
    }

}
