package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

public interface DataFormat {

    UUID getIdentifier();

    String getName();

    void setName(String dataFormatName);

    String getDescription();

    void setDescription(String dataFormatDescription);
}
