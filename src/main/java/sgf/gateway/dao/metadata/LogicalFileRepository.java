package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;

import java.net.URI;
import java.util.Collection;
import java.util.List;

public interface LogicalFileRepository {

    void addLogicalFile(LogicalFile file);

    LogicalFile get(UUID identifier);

    Collection<Variable> findByStandardNames(Collection<String> names);

    Collection<Variable> findByNames(Collection<String> names);

    LogicalFile findLogicalFileByAccessPointURL(URI accessURI);

    LogicalFile findLogicalFileByAccessPointId(UUID fileAccessPointId);

    LogicalFile findByLineageIdentifier(String lineageIdentifier);

    List<LogicalFile> findByDatasetShortNameAndLogicalFileName(String shortName, String filename, boolean exactMatch);
}
