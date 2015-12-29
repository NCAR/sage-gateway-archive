package sgf.gateway.service.metadata;

import sgf.gateway.model.metadata.citation.ResponsibleParty;

import java.net.URI;
import java.util.Date;
import java.util.List;


public interface DatasetDetails {

    String getShortName();

    String getParentDatasetShortName();

    String getTitle();

    String getDescription();

    Date getStartDate();

    Date getEndDate();

    Double getNorthernLatitude();

    Double getSouthernLatitude();

    Double getWesternLongitude();

    Double getEasternLongitude();

    Boolean isBrokered();

    String getDataAccessText();

    URI getDataAccessURI();

    String getDistributionText();

    URI getDistributionURI();

    String getDataCenterName();

    String getAuthoritativeIdentifier();

    Date getAuthoritativeSourceDateCreated();

    Date getAuthoritativeSourceDateModified();

    URI getAuthoritativeSourceURI();

    String getDoi();

    List<ResponsibleParty> getContacts();
}
