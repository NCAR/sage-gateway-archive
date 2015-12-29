package sgf.gateway.service.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.security.GroupDataType;

public interface GroupDataRequest {

    UUID getIdentifier();

    String getName();

    String getDescription();

    String getValue();

    GroupDataType getGroupDataType();
}
