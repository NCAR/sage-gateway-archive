
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
    "contributors",
    "languages",
    "description",
    "providerUpdatedDateTime",
    "freeToRead",
    "licenses",
    "publisher",
    "raw",
    "sponsorships",
    "title",
    "version",
    "uris",
        "shareProperties"
})
public class JsonData {

    @JsonProperty("contributors")
    private List<Contributor> contributors = new ArrayList<Contributor>();
    @JsonProperty("languages")
    private List<String> languages = new ArrayList<String>();
    @JsonProperty("description")
    private String description;
    @JsonProperty("providerUpdatedDateTime")
    private String providerUpdatedDateTime;
    @JsonProperty("freeToRead")
    private FreeToRead freeToRead;
    @JsonProperty("licenses")
    private List<License> licenses = new ArrayList<License>();
    @JsonProperty("publisher")
    private Publisher publisher;
    @JsonProperty("raw")
    private String raw;
    @JsonProperty("sponsorships")
    private List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
    @JsonProperty("title")
    private String title;
    @JsonProperty("version")
    private Version version;
    @JsonProperty("uris")
    private Uris uris;
    @JsonProperty("shareProperties")
    private ShareProperties shareProperties;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The contributors
     */
    @JsonProperty("contributors")
    public List<Contributor> getContributors() {
        return contributors;
    }

    /**
     *
     * @param contributors
     *     The contributors
     */
    @JsonProperty("contributors")
    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    /**
     *
     * @return
     *     The languages
     */
    @JsonProperty("languages")
    public List<String> getLanguages() {
        return languages;
    }

    /**
     *
     * @param languages
     *     The languages
     */
    @JsonProperty("languages")
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    /**
     *
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The providerUpdatedDateTime
     */
    @JsonProperty("providerUpdatedDateTime")
    public String getProviderUpdatedDateTime() {
        return providerUpdatedDateTime;
    }

    /**
     *
     * @param providerUpdatedDateTime
     *     The providerUpdatedDateTime
     */
    @JsonProperty("providerUpdatedDateTime")
    public void setProviderUpdatedDateTime(String providerUpdatedDateTime) {
        this.providerUpdatedDateTime = providerUpdatedDateTime;
    }

    /**
     *
     * @return
     *     The freeToRead
     */
    @JsonProperty("freeToRead")
    public FreeToRead getFreeToRead() {
        return freeToRead;
    }

    /**
     *
     * @param freeToRead
     *     The freeToRead
     */
    @JsonProperty("freeToRead")
    public void setFreeToRead(FreeToRead freeToRead) {
        this.freeToRead = freeToRead;
    }

    /**
     *
     * @return
     *     The licenses
     */
    @JsonProperty("licenses")
    public List<License> getLicenses() {
        return licenses;
    }

    /**
     *
     * @param licenses
     *     The licenses
     */
    @JsonProperty("licenses")
    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    /**
     *
     * @return
     *     The publisher
     */
    @JsonProperty("publisher")
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     *
     * @param publisher
     *     The publisher
     */
    @JsonProperty("publisher")
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     *
     * @return
     *     The raw
     */
    @JsonProperty("raw")
    public String getRaw() {
        return raw;
    }

    /**
     *
     * @param raw
     *     The raw
     */
    @JsonProperty("raw")
    public void setRaw(String raw) {
        this.raw = raw;
    }

    /**
     *
     * @return
     *     The sponsorships
     */
    @JsonProperty("sponsorships")
    public List<Sponsorship> getSponsorships() {
        return sponsorships;
    }

    /**
     *
     * @param sponsorships
     *     The sponsorships
     */
    @JsonProperty("sponsorships")
    public void setSponsorships(List<Sponsorship> sponsorships) {
        this.sponsorships = sponsorships;
    }

    /**
     *
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The version
     */
    @JsonProperty("version")
    public Version getVersion() {
        return version;
    }

    /**
     *
     * @param version
     *     The version
     */
    @JsonProperty("version")
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     *
     * @return
     *     The uris
     */
    @JsonProperty("uris")
    public Uris getUris() {
        return uris;
    }

    /**
     *
     * @param uris
     *     The uris
     */
    @JsonProperty("uris")
    public void setUris(Uris uris) {
        this.uris = uris;
    }

    /**
     *
     * @return
     * The shareProperties
     */
    @JsonProperty("shareProperties")
    public ShareProperties getShareProperties() {
        return shareProperties;
    }

    /**
     *
     * @param shareProperties
     * The shareProperties
     */
    @JsonProperty("shareProperties")
    public void setShareProperties(ShareProperties shareProperties) {
        this.shareProperties = shareProperties;
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
