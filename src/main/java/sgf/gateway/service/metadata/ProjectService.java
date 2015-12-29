package sgf.gateway.service.metadata;

import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.web.controllers.project.command.ProjectCommandESG;

public interface ProjectService {

    Project save(ProjectCommandESG command);
}
