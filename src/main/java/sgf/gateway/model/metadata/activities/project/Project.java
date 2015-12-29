package sgf.gateway.model.metadata.activities.project;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.activities.Activity;

import java.net.URI;

public interface Project extends Activity {

    UUID getIdentifier();

    String getPersistentIdentifier();

    void setPersistentIdentifier(String identifier);

    URI getProjectURI();

    void setProjectURI(URI uri);

}
