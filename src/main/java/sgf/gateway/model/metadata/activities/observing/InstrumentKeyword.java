package sgf.gateway.model.metadata.activities.observing;

import org.safehaus.uuid.UUID;

public interface InstrumentKeyword {

    UUID getIdentifier();

    String getCategory();

    void setCategory(String category);

    String getInstrumentClass();

    void setInstrumentClass(String instrumentClass);

    String getType();

    void setType(String type);

    String getSubtype();

    void setSubtype(String subtype);

    String getShortName();

    void setShortName(String shortName);

    String getLongName();

    void setLongName(String longName);

    String getDisplayText();
}
