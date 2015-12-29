package sgf.gateway.web.controllers.oai;

import org.hibernate.validator.constraints.NotBlank;


public class GetRecordRequest extends OAIRequest {

    @NotBlank(message = "identifier is required")
    private String identifier;

    @NotBlank(message = "metadataPrefix is required")
    private String metadataPrefix;

    public GetRecordRequest() {
        super();
    }

    public String getIdentifier() {

        String[] identifierParts = this.identifier.split(":");

        return identifierParts[2];
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

}
