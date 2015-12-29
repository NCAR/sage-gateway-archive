
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
    "awardName",
    "awardIdentifier"
})
public class Award {

    @JsonProperty("awardName")
    private String awardName;
    @JsonProperty("awardIdentifier")
    private String awardIdentifier;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The awardName
     */
    @JsonProperty("awardName")
    public String getAwardName() {
        return awardName;
    }

    /**
     * 
     * @param awardName
     *     The awardName
     */
    @JsonProperty("awardName")
    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    /**
     * 
     * @return
     *     The awardIdentifier
     */
    @JsonProperty("awardIdentifier")
    public String getAwardIdentifier() {
        return awardIdentifier;
    }

    /**
     * 
     * @param awardIdentifier
     *     The awardIdentifier
     */
    @JsonProperty("awardIdentifier")
    public void setAwardIdentifier(String awardIdentifier) {
        this.awardIdentifier = awardIdentifier;
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
