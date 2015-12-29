package sgf.gateway.service.whois.impl.jackson;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "@name",
        "@handle",
        "$"
})
public class ParentNetRef {

    @JsonProperty("@name")
    private String Name;
    @JsonProperty("@handle")
    private String Handle;
    @JsonProperty("$")
    private String $;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The Name
     */
    @JsonProperty("@name")
    public String getName() {
        return Name;
    }

    /**
     * @param Name The @name
     */
    @JsonProperty("@name")
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return The Handle
     */
    @JsonProperty("@handle")
    public String getHandle() {
        return Handle;
    }

    /**
     * @param Handle The @handle
     */
    @JsonProperty("@handle")
    public void setHandle(String Handle) {
        this.Handle = Handle;
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
