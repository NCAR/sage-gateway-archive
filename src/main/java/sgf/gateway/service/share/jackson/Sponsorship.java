
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
    "award",
    "sponsor"
})
public class Sponsorship {

    @JsonProperty("award")
    private Award award;
    @JsonProperty("sponsor")
    private Sponsor sponsor;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The award
     */
    @JsonProperty("award")
    public Award getAward() {
        return award;
    }

    /**
     * 
     * @param award
     *     The award
     */
    @JsonProperty("award")
    public void setAward(Award award) {
        this.award = award;
    }

    /**
     * 
     * @return
     *     The sponsor
     */
    @JsonProperty("sponsor")
    public Sponsor getSponsor() {
        return sponsor;
    }

    /**
     * 
     * @param sponsor
     *     The sponsor
     */
    @JsonProperty("sponsor")
    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
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
