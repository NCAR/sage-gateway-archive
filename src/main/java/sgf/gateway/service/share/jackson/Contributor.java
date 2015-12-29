
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
    "name",
    "sameAs",
    "familyName",
    "givenName",
    "additionalName",
    "email"
})
public class Contributor {

    @JsonProperty("name")
    private String name;
    @JsonProperty("sameAs")
    private List<String> sameAs = new ArrayList<String>();
    @JsonProperty("familyName")
    private String familyName;
    @JsonProperty("givenName")
    private String givenName;
    @JsonProperty("additionalName")
    private String additionalName;
    @JsonProperty("email")
    private String email;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The sameAs
     */
    @JsonProperty("sameAs")
    public List<String> getSameAs() {
        return sameAs;
    }

    /**
     * 
     * @param sameAs
     *     The sameAs
     */
    @JsonProperty("sameAs")
    public void setSameAs(List<String> sameAs) {
        this.sameAs = sameAs;
    }

    /**
     * 
     * @return
     *     The familyName
     */
    @JsonProperty("familyName")
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 
     * @param familyName
     *     The familyName
     */
    @JsonProperty("familyName")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * 
     * @return
     *     The givenName
     */
    @JsonProperty("givenName")
    public String getGivenName() {
        return givenName;
    }

    /**
     * 
     * @param givenName
     *     The givenName
     */
    @JsonProperty("givenName")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * 
     * @return
     *     The additionalName
     */
    @JsonProperty("additionalName")
    public String getAdditionalName() {
        return additionalName;
    }

    /**
     * 
     * @param additionalName
     *     The additionalName
     */
    @JsonProperty("additionalName")
    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    /**
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
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
