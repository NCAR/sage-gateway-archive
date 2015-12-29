package sgf.gateway.service.whois.impl.jackson;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "@number",
        "$"
})
public class Line {

    @JsonProperty("@number")
    private String Number;
    @JsonProperty("$")
    private String $;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The Number
     */
    @JsonProperty("@number")
    public String getNumber() {
        return Number;
    }

    /**
     * @param Number The @number
     */
    @JsonProperty("@number")
    public void setNumber(String Number) {
        this.Number = Number;
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
