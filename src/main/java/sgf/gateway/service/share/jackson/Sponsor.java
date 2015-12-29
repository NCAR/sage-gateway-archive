
package sgf.gateway.service.share.jackson;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "sponsorName",
    "sponsorIdentifier"
})
public class Sponsor {

    @JsonProperty("sponsorName")
    private String sponsorName;
    @JsonProperty("sponsorIdentifier")
    private String sponsorIdentifier;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The sponsorName
     */
    @JsonProperty("sponsorName")
    public String getSponsorName() {
        return sponsorName;
    }

    /**
     * 
     * @param sponsorName
     *     The sponsorName
     */
    @JsonProperty("sponsorName")
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    /**
     * 
     * @return
     *     The sponsorIdentifier
     */
    @JsonProperty("sponsorIdentifier")
    public String getSponsorIdentifier() {
        return sponsorIdentifier;
    }

    /**
     * 
     * @param sponsorIdentifier
     *     The sponsorIdentifier
     */
    @JsonProperty("sponsorIdentifier")
    public void setSponsorIdentifier(String sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
