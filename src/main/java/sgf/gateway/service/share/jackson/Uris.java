package sgf.gateway.service.share.jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        "canonicalUri",
        "providerUris",
        "descriptorUris",
        "objectUris"
})
public class Uris {

    @JsonProperty("canonicalUri")
    private String canonicalUri;
    @JsonProperty("providerUris")
    private List<String> providerUris = new ArrayList<String>();
    @JsonProperty("descriptorUris")
    private List<String> descriptorUris = new ArrayList<String>();
    @JsonProperty("objectUris")
    private List<String> objectUris = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The canonicalUri
     */
    @JsonProperty("canonicalUri")
    public String getCanonicalUri() {
        return canonicalUri;
    }

    /**
     *
     * @param canonicalUri
     * The canonicalUri
     */
    @JsonProperty("canonicalUri")
    public void setCanonicalUri(String canonicalUri) {
        this.canonicalUri = canonicalUri;
    }

    /**
     *
     * @return
     * The providerUris
     */
    @JsonProperty("providerUris")
    public List<String> getProviderUris() {
        return providerUris;
    }

    /**
     *
     * @param providerUris
     * The providerUris
     */
    @JsonProperty("providerUris")
    public void setProviderUris(List<String> providerUris) {
        this.providerUris = providerUris;
    }

    /**
     *
     * @return
     * The descriptorUris
     */
    @JsonProperty("descriptorUris")
    public List<String> getDescriptorUris() {
        return descriptorUris;
    }

    /**
     *
     * @param descriptorUris
     * The descriptorUris
     */
    @JsonProperty("descriptorUris")
    public void setDescriptorUris(List<String> descriptorUris) {
        this.descriptorUris = descriptorUris;
    }

    /**
     *
     * @return
     * The objectUris
     */
    @JsonProperty("objectUris")
    public List<String> getObjectUris() {
        return objectUris;
    }

    /**
     *
     * @param objectUris
     * The objectUris
     */
    @JsonProperty("objectUris")
    public void setObjectUris(List<String> objectUris) {
        this.objectUris = objectUris;
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