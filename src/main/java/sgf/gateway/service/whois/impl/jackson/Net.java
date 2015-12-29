package sgf.gateway.service.whois.impl.jackson;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "@xmlns",
        "@termsOfUse",
        "@inaccuracyReportUrl",
        "registrationDate",
        "ref",
        "customerRef",
        "endAddress",
        "handle",
        "name",
        "netBlocks",
        "orgRef",
        "parentNetRef",
        "comment",
        "startAddress",
        "updateDate",
        "version"
})
public class Net {

    @JsonProperty("@xmlns")
    private sgf.gateway.service.whois.impl.jackson.Xmlns Xmlns;
    @JsonProperty("@termsOfUse")
    private String TermsOfUse;
    @JsonProperty("@inaccuracyReportUrl")
    private String InaccuracyReportUrl;
    @JsonProperty("registrationDate")
    private RegistrationDate registrationDate;
    @JsonProperty("ref")
    private Ref ref;
    @JsonProperty("customerRef")
    private CustomerRef customerRef;
    @JsonProperty("endAddress")
    private EndAddress endAddress;
    @JsonProperty("handle")
    private Handle handle;
    @JsonProperty("name")
    private Name name;
    @JsonIgnore
    @JsonProperty("netBlocks")
    private NetBlocks netBlocks;
    @JsonProperty("orgRef")
    private OrgRef orgRef;
    @JsonProperty("parentNetRef")
    private ParentNetRef parentNetRef;
    @JsonIgnore
    @JsonProperty("comment")
    private Comment comment;
    @JsonProperty("startAddress")
    private StartAddress_ startAddress;
    @JsonProperty("updateDate")
    private UpdateDate updateDate;
    @JsonProperty("version")
    private Version version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The Xmlns
     */
    @JsonProperty("@xmlns")
    public sgf.gateway.service.whois.impl.jackson.Xmlns getXmlns() {
        return Xmlns;
    }

    /**
     * @param Xmlns The @xmlns
     */
    @JsonProperty("@xmlns")
    public void setXmlns(sgf.gateway.service.whois.impl.jackson.Xmlns Xmlns) {
        this.Xmlns = Xmlns;
    }

    /**
     * @return The TermsOfUse
     */
    @JsonProperty("@termsOfUse")
    public String getTermsOfUse() {
        return TermsOfUse;
    }

    /**
     * @param TermsOfUse The @termsOfUse
     */
    @JsonProperty("@termsOfUse")
    public void setTermsOfUse(String TermsOfUse) {
        this.TermsOfUse = TermsOfUse;
    }

    /**
     * @return The InaccuracyReportUrl
     */
    @JsonProperty("@inaccuracyReportUrl")
    public String getInaccuracyReportUrl() {
        return InaccuracyReportUrl;
    }

    /**
     * @param InaccuracyReportUrl The @inaccuracyReportUrl
     */
    @JsonProperty("@inaccuracyReportUrl")
    public void setInaccuracyReportUrl(String InaccuracyReportUrl) {
        this.InaccuracyReportUrl = InaccuracyReportUrl;
    }

    /**
     * @return The registrationDate
     */
    @JsonProperty("registrationDate")
    public RegistrationDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate The registrationDate
     */
    @JsonProperty("registrationDate")
    public void setRegistrationDate(RegistrationDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return The ref
     */
    @JsonProperty("ref")
    public Ref getRef() {
        return ref;
    }

    /**
     * @param ref The ref
     */
    @JsonProperty("ref")
    public void setRef(Ref ref) {
        this.ref = ref;
    }

    /**
     * @return The customerRef
     */
    @JsonProperty("customerRef")
    public CustomerRef getCustomerRef() {
        return customerRef;
    }

    /**
     * @param customerRef The customerRef
     */
    @JsonProperty("customerRef")
    public void setCustomerRef(CustomerRef customerRef) {
        this.customerRef = customerRef;
    }

    /**
     * @return The endAddress
     */
    @JsonProperty("endAddress")
    public EndAddress getEndAddress() {
        return endAddress;
    }

    /**
     * @param endAddress The endAddress
     */
    @JsonProperty("endAddress")
    public void setEndAddress(EndAddress endAddress) {
        this.endAddress = endAddress;
    }

    /**
     * @return The handle
     */
    @JsonProperty("handle")
    public Handle getHandle() {
        return handle;
    }

    /**
     * @param handle The handle
     */
    @JsonProperty("handle")
    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public Name getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * @return The netBlocks
     */
    @JsonIgnore
    @JsonProperty("netBlocks")
    public NetBlocks getNetBlocks() {
        return netBlocks;
    }

    /**
     * @param netBlocks The netBlocks
     */
    @JsonIgnore
    @JsonProperty("netBlocks")
    public void setNetBlocks(NetBlocks netBlocks) {
        this.netBlocks = netBlocks;
    }

    /**
     * @return The orgRef
     */
    @JsonProperty("orgRef")
    public OrgRef getOrgRef() {
        return orgRef;
    }

    /**
     * @param orgRef The orgRef
     */
    @JsonProperty("orgRef")
    public void setOrgRef(OrgRef orgRef) {
        this.orgRef = orgRef;
    }

    /**
     * @return The parentNetRef
     */
    @JsonProperty("parentNetRef")
    public ParentNetRef getParentNetRef() {
        return parentNetRef;
    }

    /**
     * @param parentNetRef The parentNetRef
     */
    @JsonProperty("parentNetRef")
    public void setParentNetRef(ParentNetRef parentNetRef) {
        this.parentNetRef = parentNetRef;
    }

    /**
     * @return The comment
     */
    @JsonIgnore
    @JsonProperty("comment")
    public Comment getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    @JsonIgnore
    @JsonProperty("comment")
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * @return The startAddress
     */
    @JsonProperty("startAddress")
    public StartAddress_ getStartAddress() {
        return startAddress;
    }

    /**
     * @param startAddress The startAddress
     */
    @JsonProperty("startAddress")
    public void setStartAddress(StartAddress_ startAddress) {
        this.startAddress = startAddress;
    }

    /**
     * @return The updateDate
     */
    @JsonProperty("updateDate")
    public UpdateDate getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate The updateDate
     */
    @JsonProperty("updateDate")
    public void setUpdateDate(UpdateDate updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return The version
     */
    @JsonProperty("version")
    public Version getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    @JsonProperty("version")
    public void setVersion(Version version) {
        this.version = version;
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
