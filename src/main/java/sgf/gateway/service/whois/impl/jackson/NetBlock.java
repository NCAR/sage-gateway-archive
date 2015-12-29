package sgf.gateway.service.whois.impl.jackson;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "cidrLength",
        "endAddress",
        "description",
        "type",
        "startAddress"
})
public class NetBlock {

    @JsonProperty("cidrLength")
    private CidrLength cidrLength;
    @JsonProperty("endAddress")
    private EndAddress_ endAddress;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("startAddress")
    private StartAddress startAddress;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The cidrLength
     */
    @JsonProperty("cidrLength")
    public CidrLength getCidrLength() {
        return cidrLength;
    }

    /**
     * @param cidrLength The cidrLength
     */
    @JsonProperty("cidrLength")
    public void setCidrLength(CidrLength cidrLength) {
        this.cidrLength = cidrLength;
    }

    /**
     * @return The endAddress
     */
    @JsonProperty("endAddress")
    public EndAddress_ getEndAddress() {
        return endAddress;
    }

    /**
     * @param endAddress The endAddress
     */
    @JsonProperty("endAddress")
    public void setEndAddress(EndAddress_ endAddress) {
        this.endAddress = endAddress;
    }

    /**
     * @return The description
     */
    @JsonProperty("description")
    public Description getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * @return The type
     */
    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    /**
     * @param type The type
     */
    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return The startAddress
     */
    @JsonProperty("startAddress")
    public StartAddress getStartAddress() {
        return startAddress;
    }

    /**
     * @param startAddress The startAddress
     */
    @JsonProperty("startAddress")
    public void setStartAddress(StartAddress startAddress) {
        this.startAddress = startAddress;
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
