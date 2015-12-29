package sgf.gateway.model.metadata;

import org.hibernate.envers.Audited;
import org.safehaus.uuid.UUID;

@Audited
public interface DataCenter {

    UUID getIdentifier();

    String getShortName();

    String getLongName();

    String getURL();

    String getFirstName();

    String getLastName();

    String getAddress1();

    String getAddress2();

    String getAddress3();

    String getCity();

    String getState();

    String getPostalCode();

    String getCountry();
}
