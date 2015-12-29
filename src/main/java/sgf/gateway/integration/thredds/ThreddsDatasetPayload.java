package sgf.gateway.integration.thredds;

import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.metadata.DatasetDetails;
import sgf.gateway.service.metadata.ProjectRequest;
import thredds.catalog.InvDataset;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;

public class ThreddsDatasetPayload implements Serializable, DatasetDetails, ProjectRequest {

    private static final long serialVersionUID = -3322977428256559522L;

    private URI source;
    private InvDataset invDataset;
    private List<URI> threddsDatasetURIs;
    private String shortName;
    private String parentDatasetShortName;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Double northernLatitude;
    private Double southernLatitude;
    private Double westernLongitude;
    private Double easternLongitude;
    private URI dataAccessURI;       // This will become a list of "related links" eventually
    private String dataAccessText;
    private URI distributionURI;     // Download link constructed from Thredds service tag
    private String distributionText;
    private String doi;
    private String dataCenterName;
    private String authoritativeIdentifier;
    private String author;
    private Date authoritativeSourceDateCreated;
    private Date authoritativeSourceDateModified;
    private List<ResponsibleParty> contacts;

    public ThreddsDatasetPayload() {
        super();
    }

    public URI getSource() {
        return this.source;
    }

    public void setSource(URI source) {
        this.source = source;
    }

    public InvDataset getInvDataset() {
        return this.invDataset;
    }

    public void setInvDataset(InvDataset invDataset) {
        this.invDataset = invDataset;
    }

    public List<URI> getThreddsDatasetURIs() {
        return this.threddsDatasetURIs;
    }

    public void setThreddsDatasetURIs(List<URI> threddsDatasetURIs) {
        this.threddsDatasetURIs = threddsDatasetURIs;
    }

    @Override
    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getParentDatasetShortName() {
        return this.parentDatasetShortName;
    }

    public void setParentDatasetShortName(String shortName) {
        this.parentDatasetShortName = shortName;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Double getNorthernLatitude() {
        return this.northernLatitude;
    }

    public void setNorthernLatitude(double northernLatitude) {
        this.northernLatitude = northernLatitude;
    }

    @Override
    public Double getSouthernLatitude() {
        return this.southernLatitude;
    }

    public void setSouthernLatitude(double southernLatitude) {
        this.southernLatitude = southernLatitude;
    }

    @Override
    public Double getWesternLongitude() {
        return this.westernLongitude;
    }

    public void setWesternLongitude(double westernLongitude) {
        this.westernLongitude = westernLongitude;
    }

    @Override
    public Double getEasternLongitude() {
        return this.easternLongitude;
    }

    public void setEasternLongitude(double easternLongitude) {
        this.easternLongitude = easternLongitude;
    }

    @Override
    public String toString() {

        String str;

        if (null == source) {
            str = super.toString();
        } else {
            str = source.toString();
        }

        return str;
    }

    @Override
    public Boolean isBrokered() {
        return true; // always true for EOL datasets
    }

    @Override
    public String getDataAccessText() {
        return this.dataAccessText;
    }

    public void setDataAccessText(String dataAccessText) {
        this.dataAccessText = dataAccessText;
    }

    @Override
    public URI getDataAccessURI() {
        return this.dataAccessURI;
    }

    public void setDistributionText(String distributionText) {
        this.distributionText = distributionText;
    }

    @Override
    public String getDistributionText() {
        return this.distributionText;
    }

    public void setDataAccessURI(URI dataAccessURI) {
        this.dataAccessURI = dataAccessURI;
    }

    @Override
    public URI getDistributionURI() {
        return this.distributionURI;
    }

    public void setDistributionURI(URI distributionURI) {
        this.distributionURI = distributionURI;
    }

    @Override
    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }

    @Override
    public String getAuthoritativeIdentifier() {
        return authoritativeIdentifier;
    }

    public void setAuthoritativeIdentifier(String authoritativeIdentifier) {
        this.authoritativeIdentifier = authoritativeIdentifier;
    }

    @Override
    public Date getAuthoritativeSourceDateCreated() {
        return authoritativeSourceDateCreated;
    }

    public void setAuthoritativeSourceDateCreated(Date authoritativeSourceDateCreated) {
        this.authoritativeSourceDateCreated = authoritativeSourceDateCreated;
    }

    @Override
    public Date getAuthoritativeSourceDateModified() {
        return authoritativeSourceDateModified;
    }

    public void setAuthoritativeSourceDateModified(Date authoritativeSourceDateModified) {
        this.authoritativeSourceDateModified = authoritativeSourceDateModified;
    }

    @Override
    public URI getAuthoritativeSourceURI() {
        return this.source;
    }

    @Override
    public List<ResponsibleParty> getContacts() {
        return contacts;
    }

    public void setContacts(List<ResponsibleParty> contacts) {
        this.contacts = contacts;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Override
    public String getDoi() {
        return doi;
    }

	/*
     The methods below are to temporarily allow this ThreddsDatasetPayload to also serve to create an ACADIS project.
	 When the project record is no longer created by the CADISProjectService and it accepts a DatasetDetails type, these
	 methods can be removed.
	 */

    @Override
    public String getProjectGroup() {
        return null;
    }


}
