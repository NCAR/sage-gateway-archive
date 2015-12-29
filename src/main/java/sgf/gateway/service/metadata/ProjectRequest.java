package sgf.gateway.service.metadata;

import java.net.URI;
import java.util.Date;

public interface ProjectRequest {

    String getShortName();

    String getTitle();

    String getProjectGroup();

    String getDescription();

    Date getStartDate();

    Date getEndDate();

    Double getNorthernLatitude();

    Double getSouthernLatitude();

    Double getWesternLongitude();

    Double getEasternLongitude();

    String getDataCenterName();

    String getAuthoritativeIdentifier();

    Date getAuthoritativeSourceDateCreated();

    Date getAuthoritativeSourceDateModified();

    URI getAuthoritativeSourceURI();

    Boolean isBrokered();

}
