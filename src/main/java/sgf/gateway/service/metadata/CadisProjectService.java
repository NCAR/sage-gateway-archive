package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;

public interface CadisProjectService {

    Dataset save(ProjectRequest details);

    Dataset update(UUID identifier, ProjectRequest details);

    Dataset update(ProjectRequest details);

    Dataset saveOrUpdate(ProjectRequest details);

}
