package sgf.gateway.service.whois.impl.jackson;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "ns3",
        "ns2",
        "$"
})
public class Xmlns {

    @JsonProperty("ns3")
    private String ns3;
    @JsonProperty("ns2")
    private String ns2;
    @JsonProperty("$")
    private String $;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The ns3
     */
    @JsonProperty("ns3")
    public String getNs3() {
        return ns3;
    }

    /**
     * @param ns3 The ns3
     */
    @JsonProperty("ns3")
    public void setNs3(String ns3) {
        this.ns3 = ns3;
    }

    /**
     * @return The ns2
     */
    @JsonProperty("ns2")
    public String getNs2() {
        return ns2;
    }

    /**
     * @param ns2 The ns2
     */
    @JsonProperty("ns2")
    public void setNs2(String ns2) {
        this.ns2 = ns2;
    }

    /**
     * @return The $
     */
    @JsonProperty("$")
    public String get$() {
        return $;
    }

    /**
     * @param $ The $
     */
    @JsonProperty("$")
    public void set$(String $) {
        this.$ = $;
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
