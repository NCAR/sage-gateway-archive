package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

import java.util.Collection;

public interface Topic {

    UUID getIdentifier();

    String getName();

    void setName(String name);

    Taxonomy getType();

    void setType(Taxonomy type);

    Collection<Dataset> getDatasets();

    void addBackLink(Dataset theDataset);

    void removeBackLink(Dataset theDataset);

}
